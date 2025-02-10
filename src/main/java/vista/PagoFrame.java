package vista;

import controlador.PagoController;
import controlador.FacturaController;
import modelo.Pago;
import modelo.Factura;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Date;

public class PagoFrame extends JFrame {
    private PagoController pagoController;
    private FacturaController facturaController;
    private JTextField idFacturaField, idCajaField, montoField;
    private JComboBox<String> metodoPagoCombo;
    private JTable pagoTable;
    private DefaultTableModel tableModel;

    public PagoFrame() {
        pagoController = new PagoController();
        facturaController = new FacturaController();
        setTitle("Registro de Pagos");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        
        panel.add(new JLabel("ID Factura:"));
        idFacturaField = new JTextField();
        panel.add(idFacturaField);

        // Agregar listener para autocompletar caja y monto
        idFacturaField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { completarDatosFactura(); }
            public void removeUpdate(DocumentEvent e) { completarDatosFactura(); }
            public void changedUpdate(DocumentEvent e) { completarDatosFactura(); }
        });
        
        panel.add(new JLabel("ID Caja:"));
        idCajaField = new JTextField();
        idCajaField.setEditable(false);
        panel.add(idCajaField);
        
        panel.add(new JLabel("Monto:"));
        montoField = new JTextField();
        montoField.setEditable(false);
        panel.add(montoField);
        
        panel.add(new JLabel("Método de Pago:"));
        String[] metodosPago = {"Efectivo", "Tarjeta", "Transferencia"};
        metodoPagoCombo = new JComboBox<>(metodosPago);
        panel.add(metodoPagoCombo);
        
        JButton registrarPagoButton = new JButton("Registrar Pago");
        registrarPagoButton.addActionListener(this::registrarPago);
        panel.add(registrarPagoButton);
        
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Factura", "Caja", "Monto", "Método"}, 0);
        pagoTable = new JTable(tableModel);
        add(new JScrollPane(pagoTable), BorderLayout.CENTER);
        listarPagos();
    }

    private void completarDatosFactura() {
        try {
            int idFactura = Integer.parseInt(idFacturaField.getText());
            Factura factura = facturaController.obtenerFacturaPorId(idFactura);
            if (factura != null) {
                idCajaField.setText(String.valueOf(factura.getIdCaja()));
                montoField.setText(String.valueOf(factura.getMontoTotal()));
            }
        } catch (NumberFormatException ex) {
            idCajaField.setText("");
            montoField.setText("");
        }
    }

    private void registrarPago(ActionEvent e) {
        if (idFacturaField.getText().isEmpty() || idCajaField.getText().isEmpty() || montoField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idFactura = Integer.parseInt(idFacturaField.getText());
            int idCaja = Integer.parseInt(idCajaField.getText());
            double monto = Double.parseDouble(montoField.getText());
            String metodoPago = (String) metodoPagoCombo.getSelectedItem();

            // Generamos la fecha actual como fecha de pago
            Date fechaPago = new Date();

            pagoController.registrarPago(idFactura, idCaja, monto, fechaPago, metodoPago);
            listarPagos();

            // Limpiar los campos después de registrar el pago
            idFacturaField.setText("");
            idCajaField.setText("");
            montoField.setText("");
            metodoPagoCombo.setSelectedIndex(0);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarPagos() {
        tableModel.setRowCount(0);
        List<Pago> pagos = pagoController.listarPagos();
        for (Pago p : pagos) {
            tableModel.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getIdCaja(), p.getMonto(), p.getMetodoPago()});
        }
    }
}
