package com.ambulance.main;

import com.ambulance.model.Ambulance;
import com.ambulance.service.AmbulanceService;
import com.ambulance.exception.AmbulanceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AmbulanceManagementUI extends JFrame {
    private JTextField licensePlateField;
    private JTextField statusField;
    private JTable ambulanceTable;
    private DefaultTableModel tableModel;
    private AmbulanceService ambulanceService;

    public AmbulanceManagementUI() {
        ambulanceService = new AmbulanceService();
        setTitle("Ambulance Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("License Plate:"));
        licensePlateField = new JTextField();
        inputPanel.add(licensePlateField);

        inputPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        inputPanel.add(statusField);

        JButton addButton = new JButton("Add Ambulance");
        inputPanel.add(addButton);

        JButton updateButton = new JButton("Update Status");
        inputPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Ambulance");
        inputPanel.add(deleteButton);

        // Table for displaying ambulances
        String[] columnNames = {"ID", "License Plate", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ambulanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ambulanceTable);

        // Button Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licensePlate = licensePlateField.getText();
                String status = statusField.getText();
                try {
                    Ambulance ambulance = new Ambulance(0, licensePlate, status);
                    ambulanceService.addAmbulance(ambulance);
                    displayAmbulances();
                    clearFields();
                } catch (AmbulanceException ex) {
                    showError(ex.getMessage());
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ambulanceTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String newStatus = statusField.getText();
                    try {
                        ambulanceService.updateAmbulanceStatus(id, newStatus);
                        displayAmbulances();
                        clearFields();
                    } catch (AmbulanceException ex) {
                        showError(ex.getMessage());
                    }
                } else {
                    showError("Please select an ambulance to update.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ambulanceTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    try {
                        ambulanceService.deleteAmbulance(id);
                        displayAmbulances();
                        clearFields();
                    } catch (AmbulanceException ex) {
                        showError(ex.getMessage());
                    }
                } else {
                    showError("Please select an ambulance to delete.");
                }
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        displayAmbulances();
    }

    private void displayAmbulances() {
        try {
            List<Ambulance> ambulances = ambulanceService.getAllAmbulances();
            tableModel.setRowCount(0); // Clear previous entries
            for (Ambulance ambulance : ambulances) {
                tableModel.addRow(new Object[]{ambulance.getId(), ambulance.getLicensePlate(), ambulance.getStatus()});
            }
        } catch (AmbulanceException e) {
            showError(e.getMessage());
        }
    }

    private void clearFields() {
        licensePlateField.setText("");
        statusField.setText("");
        ambulanceTable.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AmbulanceManagementUI ui = new AmbulanceManagementUI();
            ui.setVisible(true);
        });
    }
}