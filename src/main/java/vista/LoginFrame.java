package vista;

import controlador.LoginController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginController loginController;
    private Color primaryColor = new Color(41, 128, 185); // Azul médico
    private Color accentColor = new Color(52, 152, 219);
    private Color fieldBackground = new Color(236, 240, 241);
    
    public LoginFrame() {
        loginController = new LoginController(this);
        configurarVentana();
        crearComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Login - Clínica el Ángel");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
    }
    
    private void crearComponentes() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        mainPanel.add(crearPanelTitulo());
        mainPanel.add(crearPanelLogo());
        mainPanel.add(crearPanelFormulario());
        
        add(mainPanel);
    }

    private JPanel crearPanelTitulo() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Clínica el Ángel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestión Médica");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(25));
        
        return titlePanel;
    }

    private JPanel crearPanelFormulario() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usuarioField = crearCampoTexto();
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField = crearCampoPassword();
        
        loginButton = crearBotonLogin();
        
        formPanel.add(usuarioLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usuarioField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(loginButton);
        
        return formPanel;
    }
    
    private JTextField crearCampoTexto() {
        JTextField field = new JTextField(20);
        estilizarCampo(field);
        return field;
    }
    
    private JPasswordField crearCampoPassword() {
        JPasswordField field = new JPasswordField(20);
        estilizarCampo(field);
        return field;
    }
    
    private void estilizarCampo(JTextField field) {
        field.setMaximumSize(new Dimension(280, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(fieldBackground);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 2, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });
    }
    
    private JPanel crearPanelLogo() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        
        try {
            ImageIcon logoIcon = new ImageIcon("src/main/java/images/logo.png");
            if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image img = logoIcon.getImage();
                Image newImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(newImg);
                JLabel logoLabel = new JLabel(logoIcon);
                logoPanel.add(logoLabel);
            } else {
                throw new Exception("Error al cargar la imagen");
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("[Logo no disponible]");
            errorLabel.setForeground(Color.GRAY);
            logoPanel.add(errorLabel);
        }
        return logoPanel;
    }
    
    private JButton crearBotonLogin() {
        JButton button = new JButton("Iniciar Sesión");
        button.setMaximumSize(new Dimension(280, 45));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(accentColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(primaryColor);
            }
        });
        
        button.addActionListener(e -> 
            loginController.verificarUsuario(usuarioField.getText(), new String(passwordField.getPassword()))
        );
        
        return button;
    }
    
    public void mostrarMensaje(String mensaje, boolean esError) {
        int tipoMensaje = esError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(this, mensaje, esError ? "Error" : "Información", tipoMensaje);
    }
    
    public void abrirDashboard(int idUsuario, String rol) {
        new DashboardFrame(idUsuario, rol).setVisible(true);
        dispose();
    }
}
