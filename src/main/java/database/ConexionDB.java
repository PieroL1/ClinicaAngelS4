
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/clinica_db";
    private static final String USER = "root";  // Cambia según tu configuración
    private static final String PASSWORD = "";  // Cambia según tu configuración

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }
    
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    public static Usuario verificarUsuario(String usuario, String password) {
        String sql = "SELECT id_usuario, rol FROM Usuario WHERE usuario = ? AND password = ?";
        Usuario user = null;

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String rol = rs.getString("rol");
                user = new Usuario(idUsuario, usuario, rol); // Retorna un objeto Usuario
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        }
        return user; // Retorna null si no encuentra el usuario
    }

}
