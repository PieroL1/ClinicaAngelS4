package vista;

import controlador.CitaController;
import modelo.Cita;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CitasFrame extends JFrame {
    private CitaController citaController;
    private JTextField idCitaField, pacienteField, medicoField, fechaField, horaField, montoField;
    private JTable citasTable;
    private DefaultTableModel tableModel;

    public CitasFrame() {
        citaController = new CitaController();
        setTitle("Gestión de Citas");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        
        panel.add(new JLabel("ID Cita:"));
        idCitaField = new JTextField();
        idCitaField.setEditable(false);
        panel.add(idCitaField);
        
        panel.add(new JLabel("ID Paciente:"));
        pacienteField = new JTextField();
        panel.add(pacienteField);
        
        panel.add(new JLabel("ID Médico:"));
        medicoField = new JTextField();
        panel.add(medicoField);
        
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        fechaField = new JTextField();
        panel.add(fechaField);
        
        panel.add(new JLabel("Hora (HH:MM):"));
        horaField = new JTextField();
        panel.add(horaField);
        
        panel.add(new JLabel("Monto:"));
        montoField = new JTextField();
        panel.add(montoField);
        
        JButton agregarButton = new JButton("Registrar Cita");
        agregarButton.addActionListener(this::registrarCita);
        panel.add(agregarButton);
        
        JButton cancelarButton = new JButton("Cancelar Cita");
        cancelarButton.addActionListener(this::cancelarCita);
        panel.add(cancelarButton);
        
        JButton listarButton = new JButton("Listar Citas");
        listarButton.addActionListener(this::listarCitasAction);
        panel.add(listarButton);
        
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Paciente", "Médico", "Fecha", "Hora", "Monto", "Estado"}, 0);
        citasTable = new JTable(tableModel);
        citasTable.getSelectionModel().addListSelectionListener(e -> seleccionarCita());
        add(new JScrollPane(citasTable), BorderLayout.CENTER);
    }
    
    private void registrarCita(ActionEvent e) {
        citaController.registrarCita(
            Integer.parseInt(pacienteField.getText()), 
            Integer.parseInt(medicoField.getText()), 
            fechaField.getText(), 
            horaField.getText(),
            Double.parseDouble(montoField.getText())
        );
        listarCitas();
    }
    
    private void cancelarCita(ActionEvent e) {
        if (idCitaField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para cancelar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        citaController.cancelarCita(Integer.parseInt(idCitaField.getText()));
        listarCitas();
    }
    
    private void listarCitasAction(ActionEvent e) {
        listarCitas();
    }
    
    private void listarCitas() {
        tableModel.setRowCount(0);
        List<Cita> citas = citaController.listarCitas();
        for (Cita c : citas) {
            tableModel.addRow(new Object[]{c.getId(), c.getIdPaciente(), c.getIdMedico(), c.getFecha(), c.getHora(), c.getMonto(), c.getEstado()});
        }
    }
    
    private void seleccionarCita() {
        int selectedRow = citasTable.getSelectedRow();
        if (selectedRow >= 0) {
            idCitaField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            pacienteField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            medicoField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            fechaField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            horaField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            montoField.setText(tableModel.getValueAt(selectedRow, 5).toString());
        }
    }
}
