package dao;

import modelo.DetalleEmergencia;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleEmergenciaDAO {
    public void registrarDetalleEmergencia(DetalleEmergencia detalle) {
        String sql = "INSERT INTO DetalleEmergencia (id_paciente, id_enfermera, id_medico, descripcion, costo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, detalle.getIdPaciente());
            ps.setInt(2, detalle.getIdEnfermera());
            ps.setInt(3, detalle.getIdMedico());
            ps.setString(4, detalle.getDescripcion());
            ps.setDouble(5, detalle.getCosto());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DetalleEmergencia> listarDetallesEmergencia() {
        List<DetalleEmergencia> detalles = new ArrayList<>();
        String sql = "SELECT * FROM DetalleEmergencia";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetalleEmergencia detalle = new DetalleEmergencia(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_enfermera"),
                    rs.getInt("id_medico"),
                    rs.getString("descripcion"),
                    rs.getDouble("costo")
                );
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
