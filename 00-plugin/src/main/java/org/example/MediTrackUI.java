package org.example;

import org.example.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MediTrackUI extends JFrame {

    // UI Komponenten
    private JTextField txtPZN;
    private JTextField txtSeriennummer;
    private JTextField txtChargennummer;
    private JTextField txtMedikamentenname;
    private JTextField txtWirkstoff;
    private JTextField txtAblaufdatum;
    private JTextArea txtAreaBaldAblaufendeMedikamente;
    private JTextArea txtAreaAbgelaufeneMedikamente;
    private DefaultListModel<String> listModel;
    DefaultListModel<Medikament> listModelBaldAblaufend;
    DefaultListModel<Medikament> listModelAbgelaufen;

    // Use Cases
    private final ErstelleMedikamentUseCase erstelleMedikamentUseCase;
    private final AlleMedikamenteAusgebenUseCase alleMedikamenteAusgebenUseCase;
    private final EntferneMedikamentUseCase entferneMedikamentUseCase;
    private final ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase;
    private final AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase;


    public MediTrackUI(ErstelleMedikamentUseCase erstelleMedikamentUseCase,
                       AlleMedikamenteAusgebenUseCase alleMedikamenteAusgebenUseCase,
                       EntferneMedikamentUseCase entferneMedikamentUseCase,
                       ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase,
                       AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase) {
        this.erstelleMedikamentUseCase = erstelleMedikamentUseCase;
        this.alleMedikamenteAusgebenUseCase = alleMedikamenteAusgebenUseCase;
        this.entferneMedikamentUseCase = entferneMedikamentUseCase;
        this.listeInZeitraumAblaufenderMedikamenteUseCase = listeInZeitraumAblaufenderMedikamenteUseCase;
        this.abgelaufeneMedikamenteUseCase = abgelaufeneMedikamenteUseCase;

        // Initialisierung der UI Komponenten
        setTitle("MediTrack UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab: Medikament erstellen
        JPanel medikamentErstellenPanel = new JPanel(new GridLayout(7, 2));
        medikamentErstellenPanel.add(new JLabel("PZN:"));
        txtPZN = new JTextField();
        medikamentErstellenPanel.add(txtPZN);
        medikamentErstellenPanel.add(new JLabel("Seriennummer:"));
        txtSeriennummer = new JTextField();
        medikamentErstellenPanel.add(txtSeriennummer);
        medikamentErstellenPanel.add(new JLabel("Chargennummer:"));
        txtChargennummer = new JTextField();
        medikamentErstellenPanel.add(txtChargennummer);
        medikamentErstellenPanel.add(new JLabel("Medikamentenname:"));
        txtMedikamentenname = new JTextField();
        medikamentErstellenPanel.add(txtMedikamentenname);
        medikamentErstellenPanel.add(new JLabel("Wirkstoff:"));
        txtWirkstoff = new JTextField();
        medikamentErstellenPanel.add(txtWirkstoff);
        medikamentErstellenPanel.add(new JLabel("Ablaufdatum (YYYY-MM):"));
        txtAblaufdatum = new JTextField();
        medikamentErstellenPanel.add(txtAblaufdatum);

        JButton btnHinzufuegen = new JButton("Hinzufügen");
        btnHinzufuegen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                medikamentHinzufuegen();
            }
        });
        medikamentErstellenPanel.add(btnHinzufuegen);

        // Tab: Medikamentenliste
        JPanel medikamentenlistePanel = new JPanel(new BorderLayout());

        // JList statt JTextArea verwenden
        listModel = new DefaultListModel<>();
        JList<String> medikamentenListe = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(medikamentenListe);
        medikamentenlistePanel.add(scrollPane, BorderLayout.CENTER);

        JButton btnLoeschen = new JButton("Löschen");
        btnLoeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                medikamentLoeschen(medikamentenListe);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnLoeschen);
        buttonPanel.add(erstelleAktualisierenButton());
        medikamentenlistePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tab: Bald ablaufende Medikamente
        JPanel baldAblaufendePanel = new JPanel(new BorderLayout());

        // JList für bald ablaufende Medikamente
        listModelBaldAblaufend = new DefaultListModel<>();  // Model für Medikament-Objekte
        JList<Medikament> listeBaldAblaufend = new JList<>(listModelBaldAblaufend);
        listeBaldAblaufend.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Medikament) {
                    ((JLabel) renderer).setText(value.toString());
                }
                return renderer;
            }
        });
        JScrollPane scrollPaneBaldAblaufend = new JScrollPane(listeBaldAblaufend);
        baldAblaufendePanel.add(scrollPaneBaldAblaufend, BorderLayout.CENTER);

        // Button für Export
        JButton btnExportBaldAblaufend = new JButton("Exportieren");
        btnExportBaldAblaufend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportiereMedikamente(listModelBaldAblaufend);
            }
        });
        JPanel buttonPanelBaldAblaufend = new JPanel(new FlowLayout());
        buttonPanelBaldAblaufend.add(btnExportBaldAblaufend);
        buttonPanelBaldAblaufend.add(erstelleAktualisierenButton());
        baldAblaufendePanel.add(buttonPanelBaldAblaufend, BorderLayout.SOUTH);

        // Tab: Abgelaufene Medikamente
        JPanel abgelaufenePanel = new JPanel(new BorderLayout());

        // JList für abgelaufene Medikamente
        listModelAbgelaufen = new DefaultListModel<>();
        JList<Medikament> listeAbgelaufen = new JList<>(listModelAbgelaufen);
        listeAbgelaufen.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Medikament) {
                    ((JLabel) renderer).setText(value.toString());
                }
                return renderer;
            }
        });
        JScrollPane scrollPaneAbgelaufen = new JScrollPane(listeAbgelaufen);
        abgelaufenePanel.add(scrollPaneAbgelaufen, BorderLayout.CENTER);

        // Button für Export
        JButton btnExportAbgelaufen = new JButton("Exportieren");
        btnExportAbgelaufen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportiereMedikamente(listModelAbgelaufen);
            }
        });
        JPanel buttonPanelAbgelaufen = new JPanel(new FlowLayout());
        buttonPanelAbgelaufen.add(btnExportAbgelaufen);
        buttonPanelAbgelaufen.add(erstelleAktualisierenButton());
        abgelaufenePanel.add(buttonPanelAbgelaufen, BorderLayout.SOUTH);


        // UI zusammenfügen
        tabbedPane.addTab("Liste bald ablaufender Medikamente", baldAblaufendePanel);
        tabbedPane.addTab("Liste abgelaufener Medikamente", abgelaufenePanel);
        tabbedPane.addTab("Medikament erstellen", medikamentErstellenPanel);
        tabbedPane.addTab("Liste aller Medikamente", medikamentenlistePanel);
        add(tabbedPane, BorderLayout.CENTER);

        // Medikamentenlisten initialisieren
        medikamentenlistenAktualisieren();

        setVisible(true);
    }

    // Medikament hinzufügen
    private void medikamentHinzufuegen() {
        try {
            int pzn = Integer.parseInt(txtPZN.getText());
            String seriennummer = txtSeriennummer.getText();
            String chargennummer = txtChargennummer.getText();
            String medikamentenname = txtMedikamentenname.getText();
            String wirkstoff = txtWirkstoff.getText();
            YearMonth ablaufdatum = LocalDateParser.parseDate(txtAblaufdatum.getText(), Konstanten.ABLAUF_DATUM_FORMAT);

            erstelleMedikamentUseCase.erstelleMedikament(pzn, seriennummer, chargennummer, medikamentenname, wirkstoff, txtAblaufdatum.getText());

            // Eingabefelder leeren
            txtPZN.setText("");
            txtSeriennummer.setText("");
            txtChargennummer.setText("");
            txtMedikamentenname.setText("");
            txtWirkstoff.setText("");
            txtAblaufdatum.setText("");

            // Medikamentenlisten aktualisieren
            medikamentenlistenAktualisieren();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ungültige PZN!");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Ungültiges Datum!");
        }

    }

    // Medikament löschen
    private void medikamentLoeschen(JList<String> medikamentenListe) {
        try {
            int option = JOptionPane.showConfirmDialog(
                    MediTrackUI.this,
                    "Sind Sie sicher, dass Sie die ausgewählten Medikamente löschen möchten?",
                    "Löschen bestätigen",
                    JOptionPane.YES_NO_OPTION);
            // Ausgewählte Medikamente löschen
            if (option == JOptionPane.YES_OPTION) {
                int[] selectedIndices = medikamentenListe.getSelectedIndices();
                for (int i = selectedIndices.length - 1; i >= 0; i--) {
                    String selectedMedikament = listModel.get(selectedIndices[i]);
                    // Hier die Logik zum Löschen des Medikaments einfügen
                    //... (z.B. mit der UI aus selectedMedikament und entferneMedikamentUseCase)
                    listModel.remove(selectedIndices[i]);
                }
                JOptionPane.showMessageDialog(MediTrackUI.this, "Ausgewählte Medikamente wurden gelöscht.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Löschen!");
        }
    }

    // Medikamentenlisten aktualisieren
    private void medikamentenlistenAktualisieren() {
        // Alle Medikamente
        List<Medikament> medikamente = alleMedikamenteAusgebenUseCase.listeAllerMedikamente();
        for (Medikament medikament : medikamente) {
            listModel.addElement(medikament.toString()); // Medikamente zur JList hinzufügen
        }
        // Bald ablaufende Medikamente
        List<Medikament> baldAblaufendeMedikamente = listeInZeitraumAblaufenderMedikamenteUseCase.findeMedikamenteDieInXWochenAblaufen(2);
        listModelBaldAblaufend.clear(); // JList leeren
        for (Medikament medikament: baldAblaufendeMedikamente) {
            listModelBaldAblaufend.addElement(medikament); // Medikamente zur JList hinzufügen
        }

        // Abgelaufene Medikamente
        List<Medikament> abgelaufeneMedikamente = abgelaufeneMedikamenteUseCase.abgelaufeneMedikamente();
        listModelAbgelaufen.clear();
        for (Medikament medikament: abgelaufeneMedikamente) {
            listModelAbgelaufen.addElement(medikament);
        }
    }

    private JButton erstelleAktualisierenButton(){
        // Aktualisieren-Button
        JButton btnAktualisieren = new JButton("Aktualisieren");
        btnAktualisieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                medikamentenlistenAktualisieren();
            }
        });
        return btnAktualisieren;
    }


    private void exportiereMedikamente(DefaultListModel<Medikament> listModel) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(selectedFile)) {
                for (int i = 0; i < listModel.getSize(); i++) {
                    writer.println(listModel.get(i));
                }
                JOptionPane.showMessageDialog(this, "Medikamentenliste erfolgreich exportiert nach: " + selectedFile.getAbsolutePath(), "Export erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Fehler beim Exportieren der Medikamentenliste.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}