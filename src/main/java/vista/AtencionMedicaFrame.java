package vista;

import controlador.AtencionMedicaController;
import controlador.MedicamentoController;
import modelo.Cita;
import modelo.Medicamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

public class AtencionMedicaFrame extends JFrame {
    private AtencionMedicaController atencionController;
    private MedicamentoController medicamentoController;
    private int idMedico;
    private JTextField idCitaField, pacienteField, fechaField;
    private JTextArea diagnosticoArea;
    private JList<Medicamento> medicamentosList;
    private DefaultListModel<Medicamento> medicamentosModel;

    private JTable recetaTable, citasTable;
    private DefaultTableModel recetaTableModel, tableModel;
    private List<Medicamento> recetaLista;

    public AtencionMedicaFrame(int idMedico) {
        this.idMedico = idMedico;
        atencionController = new AtencionMedicaController();
        medicamentoController = new MedicamentoController();
        recetaLista = new ArrayList<>();
        setTitle("Atención Médica");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // 📌 Panel Principal con Bordes Redondeados
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(255, 255, 255));
        add(panelPrincipal, BorderLayout.CENTER);

        // 📌 Panel Datos con diseño mejorado
        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Atención"));
        panelDatos.setBackground(new Color(220, 230, 250));

        panelDatos.add(new JLabel("ID Cita:"));
        idCitaField = new JTextField();
        idCitaField.setEditable(false);
        panelDatos.add(idCitaField);

        panelDatos.add(new JLabel("Paciente:"));
        pacienteField = new JTextField();
        pacienteField.setEditable(false);
        panelDatos.add(pacienteField);

        panelDatos.add(new JLabel("Fecha Cita:"));
        fechaField = new JTextField();
        fechaField.setEditable(false);
        panelDatos.add(fechaField);

        panelDatos.add(new JLabel("Diagnóstico:"));
        diagnosticoArea = new JTextArea(3, 20);
        panelDatos.add(new JScrollPane(diagnosticoArea));

        panelPrincipal.add(panelDatos, BorderLayout.NORTH);

        // 📌 Panel Receta con diseño mejorado
        JPanel panelReceta = new JPanel(new GridLayout(1, 2, 10, 10));
        panelReceta.setBorder(BorderFactory.createTitledBorder("Receta Médica"));
        panelReceta.setBackground(new Color(230, 250, 220));

        medicamentosModel = new DefaultListModel<>();
        medicamentosList = new JList<>(medicamentosModel);
        medicamentosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollMedicamentos = new JScrollPane(medicamentosList);
        scrollMedicamentos.setBorder(BorderFactory.createTitledBorder("Medicamentos Disponibles"));
        panelReceta.add(scrollMedicamentos);

        JPanel panelTablaReceta = new JPanel(new BorderLayout());
        recetaTableModel = new DefaultTableModel(new String[]{"Medicamento", "Cantidad"}, 0);
        recetaTable = new JTable(recetaTableModel);
        JScrollPane scrollReceta = new JScrollPane(recetaTable);
        scrollReceta.setBorder(BorderFactory.createTitledBorder("Medicamentos Recetados"));
        panelTablaReceta.add(scrollReceta, BorderLayout.CENTER);

        JButton agregarMedicamentoButton = crearBoton("Agregar Medicamento");
        agregarMedicamentoButton.addActionListener(this::agregarMedicamento);
        panelTablaReceta.add(agregarMedicamentoButton, BorderLayout.SOUTH);

        panelReceta.add(panelTablaReceta);
        panelPrincipal.add(panelReceta, BorderLayout.CENTER);

        // 📌 Panel Botones con estilo mejorado
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(200, 230, 250));

        JButton registrarAtencionButton = crearBoton("Registrar Atención");
        registrarAtencionButton.addActionListener(this::registrarAtencion);
        panelBotones.add(registrarAtencionButton);

        JButton listarCitasButton = crearBoton("Listar Citas Asignadas");
        listarCitasButton.addActionListener(this::listarCitasAction);
        panelBotones.add(listarCitasButton);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // 📌 Panel Citas con mejor distribución
        JPanel panelCitas = new JPanel(new BorderLayout());
        panelCitas.setBorder(BorderFactory.createTitledBorder("Citas Pendientes"));
        panelCitas.setBackground(new Color(240, 220, 230));

        tableModel = new DefaultTableModel(new String[]{"ID", "Paciente", "Fecha", "Estado"}, 0);
        citasTable = new JTable(tableModel);
        citasTable.getSelectionModel().addListSelectionListener(e -> seleccionarCita());

        panelCitas.add(new JScrollPane(citasTable), BorderLayout.CENTER);
        panelPrincipal.add(panelCitas, BorderLayout.EAST);

        listarCitas();
        cargarListaMedicamentos();
    }

    private void cargarListaMedicamentos() {
        medicamentosModel.clear();

        List<Medicamento> medicamentos = medicamentoController.listarMedicamentos();

        if (medicamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay medicamentos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Medicamento medicamento : medicamentos) {
                medicamentosModel.addElement(medicamento);
            }
        }

        medicamentosList.setModel(medicamentosModel);
    }

    private void agregarMedicamento(ActionEvent e) {
        List<Medicamento> seleccionados = medicamentosList.getSelectedValuesList();

        if (!seleccionados.isEmpty()) {
            for (Medicamento medicamento : seleccionados) {
                String cantidadStr = JOptionPane.showInputDialog(this,
                        "Ingrese la cantidad para " + medicamento.getNombre() + ":",
                        "Cantidad", JOptionPane.QUESTION_MESSAGE);

                if (cantidadStr != null) {
                    try {
                        int cantidad = Integer.parseInt(cantidadStr);
                        if (cantidad > 0) {
                            recetaLista.add(medicamento);
                            recetaTableModel.addRow(new Object[]{medicamento.getNombre(), cantidad});
                        } else {
                            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarCitasAction(ActionEvent e) {
        listarCitas();
    }

    private void listarCitas() {
        tableModel.setRowCount(0);
        List<Cita> citas = atencionController.listarCitasPendientes(idMedico);
        for (Cita c : citas) {
            tableModel.addRow(new Object[]{c.getId(), c.getIdPaciente(), c.getFecha(), "Pendiente"});
        }
    }

    private void seleccionarCita() {
        int selectedRow = citasTable.getSelectedRow();
        if (selectedRow >= 0) {
            idCitaField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            pacienteField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            fechaField.setText(tableModel.getValueAt(selectedRow, 2).toString());
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
private void registrarAtencion(ActionEvent e) {
    if (idCitaField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una cita.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Medicamento> seleccionados = medicamentosList.getSelectedValuesList();
    List<String> nombresMedicamentos = new ArrayList<>();
    for (Medicamento medicamento : seleccionados) {
        nombresMedicamentos.add(medicamento.getNombre());
    }

    atencionController.registrarAtencion(
        Integer.parseInt(idCitaField.getText()),
        diagnosticoArea.getText(),
        nombresMedicamentos,
        fechaField.getText()
    );
    listarCitas();
}
    
    
}
