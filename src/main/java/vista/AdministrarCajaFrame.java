package vista;

import controlador.CajaController;
import modelo.Caja;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdministrarCajaFrame extends JFrame {
    private CajaController cajaController;
    private JTable transaccionesTable;
    private DefaultTableModel tableModel;
    private JLabel saldoActualLabel;
    private int idCaja;

    public AdministrarCajaFrame(int idCaja) {
        this.cajaController = new CajaController();
        this.idCaja = idCaja;

        // Verificar si la caja existe antes de continuar
        Caja caja = cajaController.obtenerCajaPorId(idCaja);
        if (caja == null) {
            JOptionPane.showMessageDialog(null, "La caja con ID " + idCaja + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Administrar Caja - " + caja.getNombre());
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // 📌 Panel Principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
        add(panelPrincipal, BorderLayout.CENTER);

        // 📌 Panel Superior (Saldo y Botones)
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Gestión de Caja"));
        panelSuperior.setBackground(new Color(220, 230, 250));

        panelSuperior.add(new JLabel("Saldo Actual:", SwingConstants.RIGHT));
        saldoActualLabel = new JLabel("", SwingConstants.LEFT);
        saldoActualLabel.setFont(new Font("Arial", Font.BOLD, 14));
        saldoActualLabel.setForeground(new Color(0, 128, 0)); // Color verde
        panelSuperior.add(saldoActualLabel);

        JButton registrarIngresoButton = crearBoton("Registrar Ingreso", new Color(50, 180, 80));
        registrarIngresoButton.addActionListener(this::registrarIngreso);
        panelSuperior.add(registrarIngresoButton);

        JButton registrarEgresoButton = crearBoton("Registrar Egreso", new Color(200, 50, 50));
        registrarEgresoButton.addActionListener(this::registrarEgreso);
        panelSuperior.add(registrarEgresoButton);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // 📌 Tabla de Transacciones
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones"));
        panelTabla.setBackground(new Color(250, 250, 250));

        tableModel = new DefaultTableModel(new String[]{"Tipo", "Monto", "Fecha"}, 0);
        transaccionesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transaccionesTable);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        // 📌 Panel Inferior con Botón de Regreso
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBackground(new Color(240, 240, 240));

        JButton regresarButton = crearBoton("Regresar", new Color(100, 100, 250));
        regresarButton.addActionListener(e -> dispose());
        panelInferior.add(regresarButton);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Cargar los datos iniciales
        cargarDatosCaja(caja);
    }

    private void cargarDatosCaja(Caja caja) {
        saldoActualLabel.setText("S/ " + caja.getSaldoActual());
        cargarHistorialTransacciones();
    }

    private void cargarHistorialTransacciones() {
        tableModel.setRowCount(0);
        List<String[]> transacciones = cajaController.obtenerHistorialTransacciones(idCaja);
        for (String[] transaccion : transacciones) {
            tableModel.addRow(transaccion);
        }
    }

    private void registrarIngreso(ActionEvent e) {
        String montoStr = JOptionPane.showInputDialog(this, "Ingrese el monto de ingreso:");
        try {
            double monto = Double.parseDouble(montoStr);
            cajaController.registrarIngreso(idCaja, monto);
            cargarDatosCaja(cajaController.obtenerCajaPorId(idCaja));
            cargarHistorialTransacciones();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarEgreso(ActionEvent e) {
        String montoStr = JOptionPane.showInputDialog(this, "Ingrese el monto de egreso:");
        try {
            double monto = Double.parseDouble(montoStr);
            cajaController.registrarEgreso(idCaja, monto);
            cargarDatosCaja(cajaController.obtenerCajaPorId(idCaja));
            cargarHistorialTransacciones();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(10, 15, 10, 15));
        return boton;
    }
}
