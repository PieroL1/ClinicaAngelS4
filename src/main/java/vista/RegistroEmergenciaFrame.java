package vista;

import controlador.PacienteController;
import controlador.FacturaController;
import controlador.DetalleEmergenciaController;
import controlador.UsuarioController;
import modelo.Paciente;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroEmergenciaFrame extends JFrame {
    private PacienteController pacienteController;
    private FacturaController facturaController;
    private DetalleEmergenciaController emergenciaController;
    private UsuarioController usuarioController;

    private JTextField dniField, nombreField, apellidoField, telefonoField, direccionField;
    private JTextArea descripcionArea;
    private JTextField costoField;
    private JComboBox<String> medicosCombo;
    private int idEnfermera;
    private Map<Integer, String> medicosMap = new HashMap<>();

    public RegistroEmergenciaFrame(int idEnfermera) {
        this.idEnfermera = idEnfermera;
        pacienteController = new PacienteController();
        facturaController = new FacturaController();
        emergenciaController = new DetalleEmergenciaController();
        usuarioController = new UsuarioController();

        setTitle("Registro de Emergencia");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // 📌 Panel Principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
        add(panelPrincipal, BorderLayout.CENTER);

        // 📌 Panel de Datos
        JPanel panelDatos = new JPanel(new GridLayout(7, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Información del Paciente"));
        panelDatos.setBackground(new Color(220, 230, 250));

        panelDatos.add(new JLabel("DNI Paciente:", SwingConstants.RIGHT));
        dniField = new JTextField();
        panelDatos.add(dniField);

        JButton buscarPacienteButton = crearBoton("Buscar Paciente", new Color(100, 150, 250));
        buscarPacienteButton.addActionListener(this::buscarPaciente);
        panelDatos.add(buscarPacienteButton);
        panelDatos.add(new JLabel());

        panelDatos.add(new JLabel("Nombre:", SwingConstants.RIGHT));
        nombreField = new JTextField();
        panelDatos.add(nombreField);

        panelDatos.add(new JLabel("Apellido:", SwingConstants.RIGHT));
        apellidoField = new JTextField();
        panelDatos.add(apellidoField);

        panelDatos.add(new JLabel("Teléfono:", SwingConstants.RIGHT));
        telefonoField = new JTextField();
        panelDatos.add(telefonoField);

        panelDatos.add(new JLabel("Dirección:", SwingConstants.RIGHT));
        direccionField = new JTextField();
        panelDatos.add(direccionField);

        panelPrincipal.add(panelDatos, BorderLayout.NORTH);

        // 📌 Panel de Emergencia
        JPanel panelEmergencia = new JPanel(new GridLayout(3, 2, 10, 10));
        panelEmergencia.setBorder(BorderFactory.createTitledBorder("Detalles de la Emergencia"));
        panelEmergencia.setBackground(new Color(250, 250, 220));

        panelEmergencia.add(new JLabel("Descripción:", SwingConstants.RIGHT));
        descripcionArea = new JTextArea(3, 20);
        panelEmergencia.add(new JScrollPane(descripcionArea));

        panelEmergencia.add(new JLabel("Costo:", SwingConstants.RIGHT));
        costoField = new JTextField();
        panelEmergencia.add(costoField);

        panelEmergencia.add(new JLabel("Médico a cargo:", SwingConstants.RIGHT));
        medicosCombo = new JComboBox<>(cargarMedicos());
        panelEmergencia.add(medicosCombo);

        panelPrincipal.add(panelEmergencia, BorderLayout.CENTER);

        // 📌 Panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(240, 240, 240));

        JButton registrarButton = crearBoton("Registrar Emergencia", new Color(50, 180, 80));
        registrarButton.addActionListener(this::registrarEmergencia);
        panelBotones.add(registrarButton);

        JButton cancelarButton = crearBoton("Cancelar", new Color(200, 50, 50));
        cancelarButton.addActionListener(e -> dispose());
        panelBotones.add(cancelarButton);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    }

    private void buscarPaciente(ActionEvent e) {
        String dni = dniField.getText();
        Paciente paciente = pacienteController.buscarPorDni(dni);

        if (paciente != null) {
            nombreField.setText(paciente.getNombre());
            apellidoField.setText(paciente.getApellido());
            telefonoField.setText(paciente.getTelefono());
            direccionField.setText(paciente.getDireccion());
        } else {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado. Puedes registrarlo.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void registrarEmergencia(ActionEvent e) {
        String dni = dniField.getText();
        Paciente paciente = pacienteController.buscarPorDni(dni);

        if (paciente == null) {
            paciente = new Paciente(0, nombreField.getText(), apellidoField.getText(), dni, "N/A", telefonoField.getText(), direccionField.getText());
            pacienteController.registrarPaciente(paciente);
            paciente = pacienteController.buscarPorDni(dni);
        }

        int idPaciente = paciente.getId();
        String descripcion = descripcionArea.getText();

        double costo;
        try {
            costo = Double.parseDouble(costoField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un costo válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener ID del médico seleccionado
        String medicoNombre = (String) medicosCombo.getSelectedItem();
        int idMedico = medicosMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(medicoNombre))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);

        if (idMedico == -1) {
            JOptionPane.showMessageDialog(this, "Error al seleccionar el médico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Registrar la emergencia
        emergenciaController.registrarEmergencia(idPaciente, idEnfermera, idMedico, descripcion, costo);
        facturaController.generarFactura(idPaciente, costo, new Date(), "Pendiente", "Emergencia");

        JOptionPane.showMessageDialog(this, "Emergencia registrada y factura generada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private String[] cargarMedicos() {
        List<Usuario> medicos = usuarioController.obtenerMedicos();
        medicosMap.clear();

        for (Usuario medico : medicos) {
            medicosMap.put(medico.getId(), medico.getNombre() + " " + medico.getApellido());
        }

        return medicosMap.values().toArray(new String[0]);
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
