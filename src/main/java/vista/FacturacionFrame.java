package vista;

import controlador.FacturacionController;
import modelo.Factura;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FacturacionFrame extends JFrame {
    private FacturacionController facturacionController;
    private JTextField idPacienteField, montoField;
    private JComboBox<String> tipoFacturaCombo;
    private JTable facturaTable;
    private DefaultTableModel tableModel;

    public FacturacionFrame() {
        facturacionController = new FacturacionController();
        setTitle("Gestión de Facturación");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        
        panel.add(new JLabel("ID del Paciente:"));
        idPacienteField = new JTextField();
        panel.add(idPacienteField);
        
        panel.add(new JLabel("Monto Total:"));
        montoField = new JTextField();
        panel.add(montoField);
        
        panel.add(new JLabel("Tipo de Factura:"));
        String[] tiposFactura = {"Consulta", "Emergencia", "Medicamentos"};
        tipoFacturaCombo = new JComboBox<>(tiposFactura);
        panel.add(tipoFacturaCombo);
        
        JButton generarFacturaButton = new JButton("Generar Factura");
        generarFacturaButton.addActionListener(this::generarFactura);
        panel.add(generarFacturaButton);
        
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Paciente", "Monto", "Fecha", "Estado", "Tipo"}, 0);
        facturaTable = new JTable(tableModel);
        add(new JScrollPane(facturaTable), BorderLayout.CENTER);
        listarFacturas();
    }

private void generarFactura(ActionEvent e) {
    if (idPacienteField.getText().isEmpty() || montoField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        int idPaciente = Integer.parseInt(idPacienteField.getText());
        double monto = Double.parseDouble(montoField.getText());
        String tipo = (String) tipoFacturaCombo.getSelectedItem();

        // Generamos la fecha actual como fecha de emisión
        java.util.Date fechaEmision = new java.util.Date();

        // Establecemos el estado por defecto como "Pendiente"
        String estado = "Pendiente";

        facturacionController.generarFactura(idPaciente, monto, fechaEmision, estado, tipo);
        listarFacturas();

        // Limpiar los campos después de generar la factura
        idPacienteField.setText("");
        montoField.setText("");
        tipoFacturaCombo.setSelectedIndex(0);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void listarFacturas() {
        tableModel.setRowCount(0);
        List<Factura> facturas = facturacionController.obtenerFacturas();
        for (Factura f : facturas) {
            tableModel.addRow(new Object[]{f.getId(), f.getIdPaciente(), f.getMontoTotal(), f.getFechaEmision(), f.getEstado(), f.getTipo()});
        }
    }
}
