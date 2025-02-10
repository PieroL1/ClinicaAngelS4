package vista;

import controlador.PagoController;
import controlador.FacturaController;
import modelo.Pago;
import modelo.Factura;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(800, 550);
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
        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Pago"));
        panelDatos.setBackground(new Color(220, 230, 250));

        panelDatos.add(new JLabel("ID Factura:"));
        idFacturaField = new JTextField();
        panelDatos.add(idFacturaField);

        // 🔥 Agregar listener para autocompletar caja y monto
        idFacturaField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { completarDatosFactura(); }
            public void removeUpdate(DocumentEvent e) { completarDatosFactura(); }
            public void changedUpdate(DocumentEvent e) { completarDatosFactura(); }
        });

        panelDatos.add(new JLabel("ID Caja:"));
        idCajaField = new JTextField();
        idCajaField.setEditable(false);
        panelDatos.add(idCajaField);

        panelDatos.add(new JLabel("Monto:"));
        montoField = new JTextField();
        montoField.setEditable(false);
        panelDatos.add(montoField);

        panelDatos.add(new JLabel("Método de Pago:"));
        String[] metodosPago = {"Efectivo", "Tarjeta", "Transferencia"};
        metodoPagoCombo = new JComboBox<>(metodosPago);
        panelDatos.add(metodoPagoCombo);

        panelPrincipal.add(panelDatos, BorderLayout.NORTH);

        // 📌 Panel Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(200, 230, 250));

        JButton registrarPagoButton = crearBoton("Registrar Pago");
        registrarPagoButton.addActionListener(this::registrarPago);
        panelBotones.add(registrarPagoButton);

        JButton listarPagosButton = crearBoton("Listar Pagos");
        listarPagosButton.addActionListener(e -> listarPagos());
        panelBotones.add(listarPagosButton);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // 📌 Panel Tabla Pagos
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Historial de Pagos"));
        panelTabla.setBackground(new Color(240, 220, 230));

        tableModel = new DefaultTableModel(new String[]{"ID", "Factura", "Caja", "Monto", "Método"}, 0);
        pagoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pagoTable);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

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

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(100, 150, 250));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(10, 15, 10, 15));
        return boton;
    }
}
