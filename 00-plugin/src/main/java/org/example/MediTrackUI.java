package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MediTrackUI extends JFrame implements MedikamenteUI {
    private JTextField txtPZN, txtSeriennummer, txtChargennummer, txtMedikamentenname, txtWirkstoff, txtAblaufdatum;
    private DefaultListModel<String> listModel, listModelBaldAblaufend, listModelAbgelaufen;
    private final MedikamenteController controller;

    public MediTrackUI(MedikamenteController controller) {
        this.controller = controller;
        controller.addObserver(this);

        setTitle("MediTrack UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Medikament erstellen", createMedikamentErstellenPanel());
        tabbedPane.addTab("Liste aller Medikamente", createMedikamentenListePanel());
        tabbedPane.addTab("Liste bald ablaufender Medikamente", createBaldAblaufendeMedikamentePanel());
        tabbedPane.addTab("Liste abgelaufener Medikamente", createAbgelaufeneMedikamentePanel());

        add(tabbedPane, BorderLayout.CENTER);

        controller.initialisiereMedikamentenlisten();
        setVisible(true);
    }

    private JPanel createMedikamentErstellenPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        txtPZN = new JTextField();
        txtSeriennummer = new JTextField();
        txtChargennummer = new JTextField();
        txtMedikamentenname = new JTextField();
        txtWirkstoff = new JTextField();
        txtAblaufdatum = new JTextField();

        panel.add(new JLabel("PZN:")); panel.add(txtPZN);
        panel.add(new JLabel("Seriennummer:")); panel.add(txtSeriennummer);
        panel.add(new JLabel("Chargennummer:")); panel.add(txtChargennummer);
        panel.add(new JLabel("Medikamentenname:")); panel.add(txtMedikamentenname);
        panel.add(new JLabel("Wirkstoff:")); panel.add(txtWirkstoff);
        panel.add(new JLabel("Ablaufdatum (YYYY-MM):")); panel.add(txtAblaufdatum);

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
            MedikamentDTO dto = new MedikamentDTO(
                    txtPZN.getText(),
                    txtSeriennummer.getText(),
                    txtChargennummer.getText(),
                    txtMedikamentenname.getText(),
                    txtWirkstoff.getText(),
                    txtAblaufdatum.getText()
            );
            controller.erstelleMedikament(dto);
            txtPZN.setText(""); txtSeriennummer.setText(""); txtChargennummer.setText("");
            txtMedikamentenname.setText(""); txtWirkstoff.setText(""); txtAblaufdatum.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ungültige PZN! Bitte geben Sie eine gültige Zahl ein.");
        } catch (Exception ex) {
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
}
