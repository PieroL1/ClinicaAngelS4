
package vista;

import controlador.LoginController;
import database.ConexionDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginController loginController;
    
    public LoginFrame() {
        loginController = new LoginController(this);
        setTitle("Login - Clínica el Ángel");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        
        loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.verificarUsuario(usuarioField.getText(), new String(passwordField.getPassword()));
            }
        });
        
        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        
        add(panel);
    }
    
    public void mostrarMensaje(String mensaje, boolean esError) {
        int tipoMensaje = esError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(this, mensaje, esError ? "Error" : "Información", tipoMensaje);
    }
    
    public void abrirDashboard(int idUsuario, String rol) {
        new DashboardFrame(idUsuario, rol).setVisible(true);
        dispose();  // Cierra la ventana de login
    }

}