
package controlador;

import database.ConexionDB;
import javax.swing.SwingUtilities;
import modelo.Usuario;
import vista.LoginFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private static final String MSG_BIENVENIDA = "¡Bienvenido!";
    private static final String MSG_ERROR_LOGIN = "Usuario o contraseña incorrectos";

    private final LoginFrame loginFrame;
    private final Color accentColor = new Color(52, 152, 219);

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void verificarUsuario(String usuario, String password) {
        Usuario user = ConexionDB.verificarUsuario(usuario, password);

        if (user != null) {
            String mensaje = String.format("Bienvenido %s (Rol: %s)", usuario, user.getRol());
            mostrarMensajeModal(MSG_BIENVENIDA, "images/success.png", 60, 60);
            loginFrame.abrirDashboard(user.getId(), user.getRol());

            LOGGER.info(String.format("Usuario logueado: ID=%d, Rol=%s", user.getId(), user.getRol()));
        } else {
            mostrarMensajeModal(MSG_ERROR_LOGIN, "images/error.png", 60, 60);
            LOGGER.warning("Intento de inicio de sesión fallido para usuario: " + usuario);
        }
    }

    private void mostrarMensajeModal(String mensaje, String iconoPath, int width, int height) {
        ImageIcon icon = cargarImagen(iconoPath, width, height);
        JLabel label = new JLabel(mensaje, icon, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JOptionPane.showMessageDialog(
            null, label, "Mensaje", JOptionPane.PLAIN_MESSAGE
        );
    }

    private ImageIcon cargarImagen(String path, int width, int height) {
        try {
            ImageIcon icon = null;

            // Intenta desde el classpath
            java.net.URL resourceUrl = getClass().getClassLoader().getResource(path);
            if (resourceUrl != null) {
                icon = new ImageIcon(resourceUrl);
            } else {
                // Intenta desde la ruta relativa
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
         System.out.print("Hola ");
    }
}
