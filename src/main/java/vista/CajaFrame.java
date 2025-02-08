package vista;

import controlador.CajaController;
import modelo.Caja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CajaFrame extends JFrame {
    private CajaController cajaController;
    private JTable cajaTable;
    private DefaultTableModel tableModel;

    public CajaFrame() {
        cajaController = new CajaController();
        setTitle("Gestión de Cajas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10));
        
        JButton abrirCajaButton = new JButton("Abrir Caja");
        abrirCajaButton.addActionListener(this::abrirCaja);
        panel.add(abrirCajaButton);
        
        JButton cerrarCajaButton = new JButton("Cerrar Caja");
        cerrarCajaButton.addActionListener(this::cerrarCaja);
        panel.add(cerrarCajaButton);
        
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Saldo Inicial", "Saldo Actual", "Fecha Apertura", "Fecha Cierre", "Estado"}, 0);
        cajaTable = new JTable(tableModel);
        add(new JScrollPane(cajaTable), BorderLayout.CENTER);
        listarCajas();
    }

    private void listarCajas() {
        tableModel.setRowCount(0);
        List<Caja> cajas = cajaController.listarCajas();
        for (Caja c : cajas) {
            tableModel.addRow(new Object[]{c.getId(), c.getNombre(), c.getSaldoInicial(), c.getSaldoActual(), c.getFechaApertura(), c.getFechaCierre(), c.getEstado()});
        }
    }

    private void abrirCaja(ActionEvent e) {
        int selectedRow = cajaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una caja para abrir.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idCaja = (int) tableModel.getValueAt(selectedRow, 0);
        cajaController.abrirCaja(idCaja);

        // Confirmación
        JOptionPane.showMessageDialog(this, "La caja ha sido abierta exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);

        listarCajas(); // Refrescar la tabla
    }

    private void cerrarCaja(ActionEvent e) {
        int selectedRow = cajaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una caja para cerrar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idCaja = (int) tableModel.getValueAt(selectedRow, 0);
        cajaController.cerrarCaja(idCaja);

        // Confirmación
        JOptionPane.showMessageDialog(this, "La caja ha sido cerrada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);

        listarCajas(); // Refrescar la tabla
    }

}
