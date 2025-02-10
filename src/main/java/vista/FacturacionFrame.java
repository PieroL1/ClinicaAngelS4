package vista;

import controlador.FacturacionController;
import modelo.Factura;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout(10, 10));

        // 📌 Panel Principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
        add(panelPrincipal, BorderLayout.CENTER);

        // 📌 Panel de Datos
        JPanel panelDatos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de Facturación"));
        panelDatos.setBackground(new Color(220, 230, 250));

        panelDatos.add(new JLabel("ID del Paciente:"));
        idPacienteField = new JTextField();
        panelDatos.add(idPacienteField);

        panelDatos.add(new JLabel("Monto Total:"));
        montoField = new JTextField();
        panelDatos.add(montoField);

        panelDatos.add(new JLabel("Tipo de Factura:"));
        String[] tiposFactura = {"Consulta", "Emergencia", "Medicamentos"};
        tipoFacturaCombo = new JComboBox<>(tiposFactura);
        panelDatos.add(tipoFacturaCombo);

        panelPrincipal.add(panelDatos, BorderLayout.NORTH);

        // 📌 Panel Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(200, 230, 250));

        JButton generarFacturaButton = crearBoton("Generar Factura");
        generarFacturaButton.addActionListener(this::generarFactura);
        panelBotones.add(generarFacturaButton);

        JButton listarFacturasButton = crearBoton("Listar Facturas");
        listarFacturasButton.addActionListener(e -> listarFacturas());
        panelBotones.add(listarFacturasButton);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // 📌 Panel Tabla Facturas
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Historial de Facturas"));
        panelTabla.setBackground(new Color(240, 220, 230));

        tableModel = new DefaultTableModel(new String[]{"ID", "Paciente", "Monto", "Fecha", "Estado", "Tipo"}, 0);
        facturaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(facturaTable);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

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

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(100, 150, 250));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(10, 15, 10, 15));
        return boton;
    }
}
