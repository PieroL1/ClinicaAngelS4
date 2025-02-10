package vista;

import controlador.MedicamentoController;
import controlador.FacturaController;
import modelo.Medicamento;
import modelo.Factura;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FarmaciaFrame extends JFrame {
    private MedicamentoController medicamentoController;
    private FacturaController facturaController;
    private JTable medicamentosTable;
    private DefaultTableModel tableModel;
    private Map<Integer, Integer> carrito;
    
    public FarmaciaFrame() {
        medicamentoController = new MedicamentoController();
        facturaController = new FacturaController();
        carrito = new HashMap<>();
        
        setTitle("Farmacia - Compra de Medicamentos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock", "Cantidad"}, 0);
        medicamentosTable = new JTable(tableModel);
        add(new JScrollPane(medicamentosTable), BorderLayout.CENTER);
        
        JButton comprarButton = new JButton("Comprar Medicamentos");
        comprarButton.addActionListener(this::procesarCompra);
        panel.add(comprarButton, BorderLayout.SOUTH);
        add(panel);
        
        cargarMedicamentos();
    }
    
    private void cargarMedicamentos() {
        tableModel.setRowCount(0);
        List<Medicamento> medicamentos = medicamentoController.listarMedicamentos();
        for (Medicamento m : medicamentos) {
            tableModel.addRow(new Object[]{m.getId(), m.getNombre(), m.getDescripcion(), m.getPrecio(), m.getStock(), 0});
        }
    }
    
    private void procesarCompra(ActionEvent e) {
        double total = 0;
        carrito.clear();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int id = (int) tableModel.getValueAt(i, 0);
            int cantidad = (int) tableModel.getValueAt(i, 5);
            double precio = (double) tableModel.getValueAt(i, 3);
            if (cantidad > 0) {
                carrito.put(id, cantidad);
                total += cantidad * precio;
            }
        }
        
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Total a pagar: S/. " + total + "\n¿Confirmar compra?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            facturaController.generarFacturaMedicamentos(carrito, total);
            medicamentoController.actualizarStock(carrito);
            JOptionPane.showMessageDialog(this, "Compra realizada con éxito. Diríjase a Gestión de Pagos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
