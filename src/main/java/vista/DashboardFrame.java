package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.PacientesFrame;
import vista.CitasFrame;
import vista.AtencionMedicaFrame;

public class DashboardFrame extends JFrame {
    private int idUsuario;
    private String rol;

    public DashboardFrame(int idUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.rol = rol;
        setTitle("Dashboard - Clínica el Ángel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Bienvenido, Rol: " + rol, SwingConstants.CENTER);
        panel.add(welcomeLabel);
        System.out.println("Rol recibido en DashboardFrame: " + rol + ", ID: " + idUsuario);

        if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Recepcionista") || rol.equalsIgnoreCase("Enfermera")) {
            JButton pacientesButton = new JButton("Gestión de Pacientes");
            pacientesButton.addActionListener(e -> abrirPacientesFrame());
            panel.add(pacientesButton);
        }
        
        if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Recepcionista")) {
            JButton citasButton = new JButton("Gestión de Citas");
            citasButton.addActionListener(e -> abrirCitasFrame());
            panel.add(citasButton);
        }
        
        if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Medico")) {
            JButton atencionButton = new JButton("Atención Médica");
            atencionButton.addActionListener(e -> abrirAtencionMedicaFrame());
            panel.add(atencionButton);
        }
        
        if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Cajero")) {
            JButton cajaButton = new JButton("Administrar Caja");
            cajaButton.addActionListener(e -> abrirModulo("Caja"));
            panel.add(cajaButton);
        }
        if (rol.equalsIgnoreCase("Enfermera")) {
            JButton emergenciasButton = new JButton("Atender Emergencias");
            emergenciasButton.addActionListener(e -> abrirModulo("Emergencias"));
            panel.add(emergenciasButton);
        }
        if (rol.equalsIgnoreCase("Farmacia")) {
            JButton farmaciaButton = new JButton("Gestión de Medicamentos");
            farmaciaButton.addActionListener(e -> abrirModulo("Farmacia"));
            panel.add(farmaciaButton);
        }
        
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
            }
        });
        panel.add(logoutButton);
        
        add(panel);
        setVisible(true);
    }
    
    private void abrirPacientesFrame() {
        SwingUtilities.invokeLater(() -> new PacientesFrame().setVisible(true));
    }
    
    private void abrirCitasFrame() {
        SwingUtilities.invokeLater(() -> new CitasFrame().setVisible(true));
    }
    
    private void abrirAtencionMedicaFrame() {
        SwingUtilities.invokeLater(() -> new AtencionMedicaFrame(idUsuario).setVisible(true));
    }
    
    private void abrirModulo(String modulo) {
        JOptionPane.showMessageDialog(this, "Abriendo módulo: " + modulo, "Información", JOptionPane.INFORMATION_MESSAGE);
        // Aquí se podrá implementar la apertura real de cada módulo
    }
}
