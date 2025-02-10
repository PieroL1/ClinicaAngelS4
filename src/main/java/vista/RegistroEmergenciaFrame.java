package vista;

import controlador.PacienteController;
import controlador.FacturaController;
import controlador.DetalleEmergenciaController;
import controlador.UsuarioController;
import modelo.Paciente;
import modelo.Usuario;

import javax.swing.*;
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
    private UsuarioController usuarioController; // Nuevo para obtener médicos de la BD
    
    private JTextField dniField, nombreField, apellidoField, telefonoField, direccionField;
    private JTextArea descripcionArea;
    private JTextField costoField;
    private JComboBox<String> medicosCombo;
    private int idEnfermera;
    private Map<Integer, String> medicosMap = new HashMap<>(); // Clave: ID, Valor: Nombre

    public RegistroEmergenciaFrame(int idEnfermera) {
        this.idEnfermera = idEnfermera;
        pacienteController = new PacienteController();
        facturaController = new FacturaController();
        emergenciaController = new DetalleEmergenciaController();
        usuarioController = new UsuarioController(); // Instancia para obtener médicos
        
        setTitle("Registro de Emergencia");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        
        panel.add(new JLabel("DNI Paciente:"));
        dniField = new JTextField();
        panel.add(dniField);
        
        JButton buscarPacienteButton = new JButton("Buscar");
        buscarPacienteButton.addActionListener(this::buscarPaciente);
        panel.add(buscarPacienteButton);
        panel.add(new JLabel());
        
        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);
        
        panel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        panel.add(apellidoField);
        
        panel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        panel.add(telefonoField);
        
        panel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        panel.add(direccionField);
        
        panel.add(new JLabel("Descripción de la Emergencia:"));
        descripcionArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(descripcionArea));
        
        panel.add(new JLabel("Costo:"));
        costoField = new JTextField();
        panel.add(costoField);
        
        panel.add(new JLabel("Médico a cargo:"));
        medicosCombo = new JComboBox<>(cargarMedicos());
        panel.add(medicosCombo);
        
        JButton registrarButton = new JButton("Registrar Emergencia");
        registrarButton.addActionListener(this::registrarEmergencia);
        panel.add(registrarButton);
        
        add(panel);
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

        // Registrar la emergencia con idEnfermera incluido
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
}
