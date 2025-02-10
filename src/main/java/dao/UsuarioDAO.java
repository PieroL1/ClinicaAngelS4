package dao;

import modelo.Usuario;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para obtener todos los médicos registrados en la base de datos
    public List<Usuario> obtenerMedicos() {
        List<Usuario> medicos = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, apellido FROM Usuario WHERE rol = 'Medico'";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario medico = new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                "", // Usuario (no necesario en este contexto)
                "Medico" // Se asigna manualmente porque ya filtramos por médicos
            );

                medicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }

    // Método para buscar un usuario por su ID
    public Usuario buscarUsuarioPorId(int idUsuario) {
        String sql = "SELECT id_usuario, nombre, apellido, usuario, rol FROM Usuario WHERE id_usuario = ?";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("usuario"),
                    rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para autenticar un usuario
    public Usuario autenticarUsuario(String usuario, String password) {
        String sql = "SELECT id_usuario, nombre, apellido, rol FROM Usuario WHERE usuario = ? AND password = ?";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    usuario,
                    rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
