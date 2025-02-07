
package controlador;

import database.ConexionDB;
import javax.swing.SwingUtilities;
import modelo.Usuario;
import vista.LoginFrame;

public class LoginController {
    private LoginFrame loginFrame;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void verificarUsuario(String usuario, String password) {
        Usuario user = ConexionDB.verificarUsuario(usuario, password);
        if (user != null) {
            loginFrame.mostrarMensaje("Bienvenido " + usuario + " (Rol: " + user.getRol() + ")", false);
            loginFrame.abrirDashboard(user.getId(), user.getRol());  // Pasamos ID y Rol
            System.out.println("Usuario logueado: ID=" + user.getId() + ", Rol=" + user.getRol());
        } else {
            loginFrame.mostrarMensaje("Usuario o contraseña incorrectos", true);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
         System.out.print("Hola ");
    }
}
