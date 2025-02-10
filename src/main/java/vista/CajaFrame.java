package vista;

import controlador.CajaController;
import modelo.Caja;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // 📌 Panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(220, 230, 250));

        JButton abrirCajaButton = crearBoton("Abrir Caja");
        abrirCajaButton.addActionListener(this::abrirCaja);
        panelBotones.add(abrirCajaButton);

        JButton cerrarCajaButton = crearBoton("Cerrar Caja");
        cerrarCajaButton.addActionListener(this::cerrarCaja);
        panelBotones.add(cerrarCajaButton);

        JButton administrarCajaButton = crearBoton("Administrar Caja");
        administrarCajaButton.addActionListener(this::administrarCaja);
        panelBotones.add(administrarCajaButton);

        panelPrincipal.add(panelBotones, BorderLayout.NORTH);

        // 📌 Panel Tabla Cajas
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Cajas"));
        panelTabla.setBackground(new Color(240, 220, 230));

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Saldo Inicial", "Saldo Actual", "Fecha Apertura", "Fecha Cierre", "Estado"}, 0);
        cajaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cajaTable);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

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

        JOptionPane.showMessageDialog(this, "La caja ha sido abierta exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        listarCajas();
    }

    private void cerrarCaja(ActionEvent e) {
        int selectedRow = cajaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una caja para cerrar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idCaja = (int) tableModel.getValueAt(selectedRow, 0);
        cajaController.cerrarCaja(idCaja);

        JOptionPane.showMessageDialog(this, "La caja ha sido cerrada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        listarCajas();
    }

    private void administrarCaja(ActionEvent e) {
        int selectedRow = cajaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una caja para administrar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idCaja = (int) tableModel.getValueAt(selectedRow, 0);
        new AdministrarCajaFrame(idCaja).setVisible(true);
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
