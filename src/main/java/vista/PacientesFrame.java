package vista;

import controlador.PacienteController;
import modelo.Paciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PacientesFrame extends JFrame {
    private PacienteController pacienteController;
    private JTextField idField, nombreField, apellidoField, dniField, fechaNacimientoField, telefonoField, direccionField;
    private JTable pacientesTable;
    private DefaultTableModel tableModel;

    public PacientesFrame() {
        pacienteController = new PacienteController();
        setTitle("Gestión de Pacientes");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // 📌 Panel Datos con Diseño Mejorado
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));
        panel.setBackground(new Color(220, 230, 250));

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

        panel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"));
        fechaNacimientoField = new JTextField();
        panel.add(fechaNacimientoField);

        panel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        panel.add(telefonoField);

        panel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        panel.add(direccionField);

        add(panel, BorderLayout.NORTH);

        // 📌 Panel Botones con Estilo Mejorado
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(200, 230, 250));

        JButton agregarButton = crearBoton("Agregar");
        agregarButton.addActionListener(this::agregarPaciente);
        panelBotones.add(agregarButton);

        JButton editarButton = crearBoton("Editar");
        editarButton.addActionListener(this::editarPaciente);
        panelBotones.add(editarButton);

        JButton eliminarButton = crearBoton("Eliminar");
        eliminarButton.addActionListener(this::eliminarPaciente);
        panelBotones.add(eliminarButton);

        JButton listarButton = crearBoton("Listar");
        listarButton.addActionListener(e -> listarPacientes());
        panelBotones.add(listarButton);

        add(panelBotones, BorderLayout.SOUTH);

        // 📌 Tabla Pacientes con Bordes y Colores
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "DNI", "Fecha Nac.", "Teléfono", "Dirección"}, 0);
        pacientesTable = new JTable(tableModel);
        pacientesTable.getSelectionModel().addListSelectionListener(e -> seleccionarPaciente());

        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pacientes Registrados"));
        add(scrollPane, BorderLayout.CENTER);

        listarPacientes();
    }

    // 📌 Métodos de Acción para Pacientes
    private void agregarPaciente(ActionEvent e) {
        pacienteController.agregarPaciente(
            nombreField.getText(), apellidoField.getText(), dniField.getText(), fechaNacimientoField.getText(), telefonoField.getText(), direccionField.getText()
        );
        listarPacientes();
    }

    private void editarPaciente(ActionEvent e) {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pacienteController.editarPaciente(
            Integer.parseInt(idField.getText()), nombreField.getText(), apellidoField.getText(), dniField.getText(), fechaNacimientoField.getText(), telefonoField.getText(), direccionField.getText()
        );
        listarPacientes();
    }

    private void eliminarPaciente(ActionEvent e) {
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

    // 📌 Método para mejorar la apariencia de los botones
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(100, 150, 250));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(10, 15, 10, 15));
        return boton;
    }
}
