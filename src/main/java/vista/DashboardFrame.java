package vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import vista.PacientesFrame;
import vista.CitasFrame;
import vista.AtencionMedicaFrame;

public class DashboardFrame extends JFrame {
    private int idUsuario;
    private String rol;
    private Color primaryColor = new Color(41, 128, 185);    // Azul profesional
    private Color secondaryColor = new Color(236, 240, 241); // Gris claro
    private Color accentColor = new Color(52, 152, 219);     // Azul claro
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
    private Font buttonFont = new Font("Agency FB", Font.PLAIN, 25);

    public DashboardFrame(int idUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.rol = rol;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Clínica El Ángel - Panel de Control");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }

    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior con información del usuario
        JPanel headerPanel = crearPanelSuperior();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel central con los botones
        JPanel buttonPanel = crearPanelBotones();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Panel inferior con botón de cerrar sesión
        JPanel footerPanel = crearPanelInferior();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

      private ImageIcon cargarImagen(String path, int width, int height) {
        try {
            // Intenta cargar la imagen desde diferentes ubicaciones
            ImageIcon icon = null;
            
            // Intenta desde el classpath
            java.net.URL resourceUrl = getClass().getClassLoader().getResource(path);
            if (resourceUrl != null) {
                icon = new ImageIcon(resourceUrl);
            } else {
                // Intenta desde la ruta relativa al proyecto
                icon = new ImageIcon("resources/" + path);
            }
            
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(newImg);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen " + path + ": " + e.getMessage());
        }
        // Si no se puede cargar la imagen, devolver un icono por defecto
        return createDefaultIcon(width, height);
    }

    private ImageIcon createDefaultIcon(int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(accentColor);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return new ImageIcon(bi);
    }

    private JPanel crearPanelSuperior() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        ImageIcon logoIcon = cargarImagen("images/logo_clinica.png", 60, 60);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 10, 0, 20));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JPanel welcomePanel = new JPanel(new GridLayout(2, 1, 5, 0));
        welcomePanel.setBackground(primaryColor);
        
        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel roleLabel = new JLabel("Rol: " + rol);
        roleLabel.setFont(buttonFont);
        roleLabel.setForeground(Color.WHITE);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(roleLabel);
        
        headerPanel.add(welcomePanel, BorderLayout.CENTER);
        return headerPanel;
    }

private JPanel crearPanelBotones() {
    JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 15, 15));
    buttonPanel.setBackground(Color.WHITE);
    buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Crear y añadir botones según el rol con las rutas corregidas
    if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Recepcionista") || rol.equalsIgnoreCase("Enfermera")) {
        agregarBoton(buttonPanel, "Gestión de Pacientes", "images/pacientes_icon.png", e -> abrirPacientesFrame());
    }

    if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Recepcionista")) {
        agregarBoton(buttonPanel, "Gestión de Citas", "images/citas_icon.png", e -> abrirCitasFrame());
    }

    if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Medico")) {
        agregarBoton(buttonPanel, "Atención Médica", "images/atencion_icon.png", e -> abrirAtencionMedicaFrame());
    }

    if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Cajero")) {
        agregarBoton(buttonPanel, "Administrar Caja", "images/caja_icon.png", e -> abrirModulo("Caja"));
    }

    if (rol.equalsIgnoreCase("Enfermera")) {
        agregarBoton(buttonPanel, "Atender Emergencias", "images/emergencia_icon.png", e -> abrirModulo("Emergencias"));
    }

    if (rol.equalsIgnoreCase("Farmacia")) {
        agregarBoton(buttonPanel, "Gestión de Medicamentos", "images/farmacia_icon.png", e -> abrirModulo("Farmacia"));
    }

    return buttonPanel;
}

 private void agregarBoton(JPanel panel, String texto, String iconPath, ActionListener action) {
        JButton button = new JButton(texto);
        button.setFont(buttonFont);
        button.setBackground(secondaryColor);
        button.setForeground(new Color(44, 62, 80));
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
            new LineBorder(accentColor, 1),
            new EmptyBorder(15, 20, 15, 20)
        ));

        ImageIcon icon = cargarImagen(iconPath, 60, 60);
        button.setIcon(icon);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(accentColor);
                button.setForeground(Color.WHITE);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(secondaryColor);
                button.setForeground(new Color(44, 62, 80));
            }
        });

        button.addActionListener(action);
        panel.add(button);
    }

    private JPanel crearPanelInferior() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);

        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(192, 57, 43));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });

        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });

        footerPanel.add(logoutButton);
        return footerPanel;
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
        JOptionPane.showMessageDialog(
            this,
            "Abriendo módulo: " + modulo,
            "Información",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}