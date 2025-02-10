package vista;

import controlador.MedicamentoController;
import controlador.FacturaController;
import controlador.PacienteController;
import modelo.Medicamento;
import modelo.Paciente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FarmaciaFrame extends JFrame {
    private MedicamentoController medicamentoController;
    private FacturaController facturaController;
    private PacienteController pacienteController;
    
    private JTable medicamentosTable;
    private DefaultTableModel tableModel;
    private Map<Integer, Integer> carrito;
    private JTextField dniField;
    private JButton buscarButton, comprarButton;

    public FarmaciaFrame() {
        medicamentoController = new MedicamentoController();
        facturaController = new FacturaController();
        pacienteController = new PacienteController();
        carrito = new HashMap<>();

        setTitle("Farmacia - Compra de Medicamentos");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // 📌 Panel Superior: Buscar Paciente
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBorder(new EmptyBorder(10, 15, 10, 15));
        panelSuperior.setBackground(new Color(220, 230, 250));

        panelSuperior.add(new JLabel("DNI del Paciente:"));
        dniField = new JTextField(12);
        panelSuperior.add(dniField);

        buscarButton = crearBoton("Buscar Receta", new Color(100, 150, 250));
        buscarButton.addActionListener(this::buscarReceta);
        panelSuperior.add(buscarButton);

        add(panelSuperior, BorderLayout.NORTH);

        // 📌 Tabla de Medicamentos
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock", "Cantidad"}, 0);
        medicamentosTable = new JTable(tableModel);
        add(new JScrollPane(medicamentosTable), BorderLayout.CENTER);

        // 📌 Panel Inferior: Compra
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBorder(new EmptyBorder(10, 15, 10, 15));
        panelInferior.setBackground(new Color(250, 230, 230));

        comprarButton = crearBoton("Comprar Medicamentos", new Color(50, 180, 80));
        comprarButton.addActionListener(this::procesarCompra);
        panelInferior.add(comprarButton);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void buscarReceta(ActionEvent e) {
        String dni = dniField.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = pacienteController.buscarPorDni(dni);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un paciente con ese DNI.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);  // Limpiar la tabla
        List<Medicamento> medicamentosRecetados = medicamentoController.obtenerMedicamentosPorPaciente(paciente.getId());

        if (medicamentosRecetados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El paciente no tiene medicamentos recetados.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Medicamento m : medicamentosRecetados) {
                tableModel.addRow(new Object[]{m.getId(), m.getNombre(), m.getDescripcion(), m.getPrecio(), m.getStock(), 0});
            }
        }
    }

    private void procesarCompra(ActionEvent e) {
        double total = 0;
        carrito.clear();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int id = (int) tableModel.getValueAt(i, 0);
            int cantidad = Integer.parseInt(tableModel.getValueAt(i, 5).toString());
            double precio = (double) tableModel.getValueAt(i, 3);

            if (cantidad > 0) {
                carrito.put(id, cantidad);
                total += cantidad * precio;
            }
        }

        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Total a pagar: S/. " + total + "\n¿Confirmar compra?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            facturaController.generarFacturaMedicamentos(carrito, total);
            medicamentoController.actualizarStock(carrito); // 🔹 Se encarga de restar el stock
            JOptionPane.showMessageDialog(this, "Compra realizada con éxito. Diríjase a Gestión de Pagos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
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
