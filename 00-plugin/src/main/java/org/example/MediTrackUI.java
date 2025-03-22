package org.example;

import java.time.format.DateTimeParseException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MediTrackUI extends JFrame implements MedikamenteUI, LagerorteUI {
    private JTextField txtPZN, txtSeriennummer, txtChargennummer, txtMedikamentenname, txtWirkstoff, txtAblaufdatum, txtLagerort;
    private JTextField txtLagerortBezeichnung, txtLagerortStockwerk, txtLagerortRaumnummer;
    private DefaultListModel<String> listModel, listModelBaldAblaufend, listModelAbgelaufen, listModelLagerorte;
    private final MedikamenteController controller;
    private final LagerorteController lagerorteController;

    public MediTrackUI(MedikamenteController controller, LagerorteController lagerorteController) {
        this.controller = controller;
        this.lagerorteController = lagerorteController;
        controller.addObserver(this);
        lagerorteController.addObserver(this);

        setTitle("MediTrack UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Medikament erstellen", createMedikamentErstellenPanel());
        tabbedPane.addTab("Liste aller Medikamente", createMedikamentenListePanel());
        tabbedPane.addTab("Liste bald ablaufender Medikamente", createBaldAblaufendeMedikamentePanel());
        tabbedPane.addTab("Liste abgelaufener Medikamente", createAbgelaufeneMedikamentePanel());
        tabbedPane.addTab("Lagerorte verwalten", createLagerortePanel());

        add(tabbedPane, BorderLayout.CENTER);

        controller.initialisiereMedikamentenlisten();
        lagerorteController.initialisiereLagerortListe();
        setVisible(true);
    }

    private JPanel createLagerortePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel zum Hinzufügen neuer Lagerorte
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        txtLagerortBezeichnung = new JTextField();
        txtLagerortStockwerk = new JTextField();
        txtLagerortRaumnummer = new JTextField();
        
        formPanel.add(new JLabel("Bezeichnung:"));
        formPanel.add(txtLagerortBezeichnung);
        formPanel.add(new JLabel("Stockwerk:"));
        formPanel.add(txtLagerortStockwerk);
        formPanel.add(new JLabel("Raumnummer:"));
        formPanel.add(txtLagerortRaumnummer);
        
        JButton btnHinzufuegen = new JButton("Lagerort hinzufügen");
        btnHinzufuegen.addActionListener(e -> lagerortHinzufuegen());
        formPanel.add(btnHinzufuegen);
        
        // Panel für die Liste der Lagerorte
        JPanel listPanel = new JPanel(new BorderLayout());
        listModelLagerorte = new DefaultListModel<>();
        JList<String> lagerortListe = new JList<>(listModelLagerorte);
        listPanel.add(new JScrollPane(lagerortListe), BorderLayout.CENTER);
        
        // Panel für die Aktionen
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton btnLoeschen = new JButton("Löschen");
        btnLoeschen.addActionListener(e -> lagerortLoeschen(lagerortListe));
        actionPanel.add(btnLoeschen);
        
        // Zusammenfügen der Panel
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void lagerortHinzufuegen() {
        try {
            LagerortDTO dto = new LagerortDTO(
                txtLagerortBezeichnung.getText(),
                txtLagerortStockwerk.getText(),
                txtLagerortRaumnummer.getText()
            );
            
            lagerorteController.erstelleLagerort(dto);
            
            // Felder zurücksetzen
            txtLagerortBezeichnung.setText("");
            txtLagerortStockwerk.setText("");
            txtLagerortRaumnummer.setText("");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Lagerorts: " + ex.getMessage());
        }
    }
    
    private void lagerortLoeschen(JList<String> lagerortListe) {
        int selectedIndex = lagerortListe.getSelectedIndex();
        if (selectedIndex != -1) {
            lagerorteController.loescheLagerort(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Lagerort aus.");
        }
    }

    private JPanel createMedikamentErstellenPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2));
        txtPZN = new JTextField();
        txtSeriennummer = new JTextField();
        txtChargennummer = new JTextField();
        txtMedikamentenname = new JTextField();
        txtWirkstoff = new JTextField();
        txtAblaufdatum = new JTextField();
        txtLagerort = new JTextField();

        panel.add(new JLabel("PZN:")); panel.add(txtPZN);
        panel.add(new JLabel("Seriennummer:")); panel.add(txtSeriennummer);
        panel.add(new JLabel("Chargennummer:")); panel.add(txtChargennummer);
        panel.add(new JLabel("Medikamentenname:")); panel.add(txtMedikamentenname);
        panel.add(new JLabel("Wirkstoff:")); panel.add(txtWirkstoff);
        panel.add(new JLabel("Ablaufdatum (YYYY-MM):")); panel.add(txtAblaufdatum);
        panel.add(new JLabel("Lagerort (Stockwerk-Raumnummer):")); panel.add(txtLagerort);

        JButton btnHinzufuegen = new JButton("Hinzufügen");
        btnHinzufuegen.addActionListener(e -> medikamentHinzufuegen());
        panel.add(btnHinzufuegen);

        return panel;
    }

    private JPanel createMedikamentenListePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        JList<String> medikamentenListe = new JList<>(listModel);
        panel.add(new JScrollPane(medikamentenListe), BorderLayout.CENTER);

        JButton btnLoeschen = new JButton("Löschen");
        btnLoeschen.addActionListener(e -> medikamentLoeschen(medikamentenListe));
        panel.add(btnLoeschen, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBaldAblaufendeMedikamentePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        listModelBaldAblaufend = new DefaultListModel<>();
        JList<String> listeBaldAblaufend = new JList<>(listModelBaldAblaufend);

        panel.add(new JScrollPane(listeBaldAblaufend), BorderLayout.CENTER);

        JButton btnExport = new JButton("Exportieren");
        btnExport.addActionListener(e -> exportiereMedikamente(listModelBaldAblaufend));
        panel.add(btnExport, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAbgelaufeneMedikamentePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        listModelAbgelaufen = new DefaultListModel<>();
        JList<String> listeAbgelaufen = new JList<>(listModelAbgelaufen);

        panel.add(new JScrollPane(listeAbgelaufen), BorderLayout.CENTER);

        JButton btnExport = new JButton("Exportieren");
        btnExport.addActionListener(e -> exportiereMedikamente(listModelAbgelaufen));
        panel.add(btnExport, BorderLayout.SOUTH);

        return panel;
    }

    private void medikamentHinzufuegen() {
        try {
            System.out.println(txtLagerort.getText());
            MedikamentDTO dto = new MedikamentDTO(
                    txtPZN.getText(),
                    txtSeriennummer.getText(),
                    txtChargennummer.getText(),
                    txtMedikamentenname.getText(),
                    txtWirkstoff.getText(),
                    txtAblaufdatum.getText(),
                    txtLagerort.getText()
            );

            controller.erstelleMedikament(dto);
            txtPZN.setText(""); txtSeriennummer.setText(""); txtChargennummer.setText("");
            txtMedikamentenname.setText(""); txtWirkstoff.setText(""); txtAblaufdatum.setText(""); txtLagerort.setText("");
        }catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Medikaments: " + ex.getMessage());
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Medikaments: " + ex.getMessage());
        }
        
    }

    private void medikamentLoeschen(JList<String> medikamentenListe) {
        int selectedIndex = medikamentenListe.getSelectedIndex();
        if (selectedIndex != -1) {
            controller.loescheMedikament(selectedIndex);
        }
    }

    private void exportiereMedikamente(DefaultListModel<String> listModel) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(selectedFile)) {
                for (int i = 0; i < listModel.getSize(); i++) {
                    writer.println(listModel.get(i));
                }
                JOptionPane.showMessageDialog(this, "Medikamentenliste exportiert nach: " + selectedFile.getAbsolutePath(), "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Fehler beim Export!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void aktualisiereMedikamentenliste(List<String> medikamentenStrings) {
        listModel.clear();
        listModel.addAll(medikamentenStrings);
    }

    @Override
    public void aktualisiereBaldAblaufendeMedikamente(List<String> medikamentenStrings) {
        listModelBaldAblaufend.clear();
        listModelBaldAblaufend.addAll(medikamentenStrings);
    }

    @Override
    public void aktualisiereAbgelaufeneMedikamente(List<String> medikamentenStrings) {
        listModelAbgelaufen.clear();
        listModelAbgelaufen.addAll(medikamentenStrings);
    }

    @Override
    public void aktualisiereLagerortListe(List<String> lagerortStrings) {
        listModelLagerorte.clear();
        listModelLagerorte.addAll(lagerortStrings);
    }
}
