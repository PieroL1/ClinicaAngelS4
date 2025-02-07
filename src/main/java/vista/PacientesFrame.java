package vista;

import controlador.PacienteController;
import modelo.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PacientesFrame extends JFrame {
    private PacienteController pacienteController;
    private JTextField idField, nombreField, apellidoField, dniField, fechaNacimientoField, telefonoField, direccionField;
    private JTable pacientesTable;
    private DefaultTableModel tableModel;

    public PacientesFrame() {
        pacienteController = new PacienteController();
        setTitle("Gestión de Pacientes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        panel.add(idField);
        
        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);
        
        panel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        panel.add(apellidoField);
        
        panel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        panel.add(dniField);
        
        panel.add(new JLabel("Fecha de Nacimiento:"));
        fechaNacimientoField = new JTextField();
        panel.add(fechaNacimientoField);
        
        panel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        panel.add(telefonoField);
        
        panel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        panel.add(direccionField);
        
        JButton agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(e -> agregarPaciente());
        panel.add(agregarButton);
        
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> editarPaciente());
        panel.add(editarButton);
        
        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(e -> eliminarPaciente());
        panel.add(eliminarButton);
        
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> listarPacientes());
        panel.add(listarButton);
        
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "DNI", "Fecha Nac.", "Teléfono", "Dirección"}, 0);
        pacientesTable = new JTable(tableModel);
        pacientesTable.getSelectionModel().addListSelectionListener(e -> seleccionarPaciente());
        add(new JScrollPane(pacientesTable), BorderLayout.CENTER);
    }
    
    private void agregarPaciente() {
        pacienteController.agregarPaciente(
            nombreField.getText(), apellidoField.getText(), dniField.getText(), fechaNacimientoField.getText(), telefonoField.getText(), direccionField.getText()
        );
        listarPacientes();
    }
    
    private void editarPaciente() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        pacienteController.editarPaciente(
            Integer.parseInt(idField.getText()), nombreField.getText(), apellidoField.getText(), dniField.getText(), fechaNacimientoField.getText(), telefonoField.getText(), direccionField.getText()
        );
        listarPacientes();
    }
    
    private void eliminarPaciente() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        pacienteController.eliminarPaciente(Integer.parseInt(idField.getText()));
        listarPacientes();
    }
    
    private void listarPacientes() {
        tableModel.setRowCount(0);
        List<Paciente> pacientes = pacienteController.listarPacientes();
        for (Paciente p : pacientes) {
            tableModel.addRow(new Object[]{p.getId(), p.getNombre(), p.getApellido(), p.getDni(), p.getFechaNacimiento(), p.getTelefono(), p.getDireccion()});
        }
    }
    
    private void seleccionarPaciente() {
        int selectedRow = pacientesTable.getSelectedRow();
        if (selectedRow >= 0) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nombreField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            apellidoField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            dniField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            fechaNacimientoField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            telefonoField.setText(tableModel.getValueAt(selectedRow, 5).toString());
            direccionField.setText(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }
}