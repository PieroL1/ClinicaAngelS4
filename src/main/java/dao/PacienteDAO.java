package dao;

import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Paciente;

public class PacienteDAO {
    public void agregarPaciente(Paciente paciente) {
        String sql = "INSERT INTO Paciente (nombre, apellido, dni, fecha_nacimiento, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getTelefono());
            ps.setString(6, paciente.getDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarPaciente(Paciente paciente) {
        String sql = "UPDATE Paciente SET nombre=?, apellido=?, dni=?, fecha_nacimiento=?, telefono=?, direccion=? WHERE id_paciente=?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getTelefono());
            ps.setString(6, paciente.getDireccion());
            ps.setInt(7, paciente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPaciente(int id) {
        String sql = "DELETE FROM Paciente WHERE id_paciente=?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM Paciente";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("fecha_nacimiento"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }
    
    public Paciente buscarPorDni(String dni) {
        String sql = "SELECT * FROM Paciente WHERE dni = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("fecha_nacimiento"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el paciente
    }

    
    
    
}
