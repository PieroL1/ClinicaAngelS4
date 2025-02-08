package vista;

import controlador.AtencionMedicaController;
import modelo.AtencionMedica;
import modelo.Cita;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AtencionMedicaFrame extends JFrame {
    private AtencionMedicaController atencionController;
    private int idMedico;
    private JTextField idCitaField, pacienteField, fechaField;
    private JTextArea diagnosticoArea, recetaArea;
    private JTable citasTable;
    private DefaultTableModel tableModel;

    public AtencionMedicaFrame(int idMedico) {
        this.idMedico = idMedico;
        atencionController = new AtencionMedicaController();
        setTitle("Atención Médica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        
        panel.add(new JLabel("ID Cita:"));
        idCitaField = new JTextField();
        idCitaField.setEditable(false);
        panel.add(idCitaField);
        
        panel.add(new JLabel("Paciente:"));
        pacienteField = new JTextField();
        pacienteField.setEditable(false);
        panel.add(pacienteField);
        
        panel.add(new JLabel("Fecha Cita:"));
        fechaField = new JTextField();
        fechaField.setEditable(false);
        panel.add(fechaField);
        
        panel.add(new JLabel("Diagnóstico:"));
        diagnosticoArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(diagnosticoArea));
        
        panel.add(new JLabel("Receta Médica:"));
        recetaArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(recetaArea));
        
        JButton registrarAtencionButton = new JButton("Registrar Atención");
        registrarAtencionButton.addActionListener(this::registrarAtencion);
        panel.add(registrarAtencionButton);
        
        JButton listarCitasButton = new JButton("Listar Citas Asignadas");
        listarCitasButton.addActionListener(this::listarCitasAction);
        panel.add(listarCitasButton);
        
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Paciente", "Fecha", "Estado"}, 0);
        citasTable = new JTable(tableModel);
        citasTable.getSelectionModel().addListSelectionListener(e -> seleccionarCita());
        add(new JScrollPane(citasTable), BorderLayout.CENTER);

        listarCitas(); // Cargar citas al abrir la ventana
    }
    
    private void registrarAtencion(ActionEvent e) {
        if (idCitaField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cita.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        atencionController.registrarAtencion(
            Integer.parseInt(idCitaField.getText()),
            diagnosticoArea.getText(),
            recetaArea.getText(),
            fechaField.getText()
        );
        listarCitas();
    }
    
    private void listarCitasAction(ActionEvent e) {
        listarCitas();
    }
    
    private void listarCitas() {
        tableModel.setRowCount(0);
        List<Cita> citas = atencionController.listarCitasPendientes(idMedico);
        System.out.println("Citas encontradas: " + citas.size()); // Debug en consola

        for (Cita c : citas) {
            System.out.println("Cita ID: " + c.getId() + ", Paciente: " + c.getIdPaciente()); // Debug
            tableModel.addRow(new Object[]{c.getId(), c.getIdPaciente(), c.getFecha(), "Pendiente"});
        }
        
    }
    
    private void seleccionarCita() {
        int selectedRow = citasTable.getSelectedRow();
        if (selectedRow >= 0) {
            idCitaField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            pacienteField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            fechaField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        }
    }
}
