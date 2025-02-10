package vista;

import controlador.CajaController;
import modelo.Caja;
import javax.swing.*;
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
            return; // No crear la ventana si la caja no existe
        }

        setTitle("Administrar Caja - " + caja.getNombre()); // Mostrar el nombre de la caja en el título
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Saldo Actual:"));
        saldoActualLabel = new JLabel();
        panel.add(saldoActualLabel);

        JButton registrarIngresoButton = new JButton("Registrar Ingreso");
        registrarIngresoButton.addActionListener(this::registrarIngreso);
        panel.add(registrarIngresoButton);

        JButton registrarEgresoButton = new JButton("Registrar Egreso");
        registrarEgresoButton.addActionListener(this::registrarEgreso);
        panel.add(registrarEgresoButton);

        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(e -> dispose());
        panel.add(regresarButton);

        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Tipo", "Monto", "Fecha"}, 0);
        transaccionesTable = new JTable(tableModel);
        add(new JScrollPane(transaccionesTable), BorderLayout.CENTER);

        cargarDatosCaja(caja); // Se pasa la caja obtenida
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

}
