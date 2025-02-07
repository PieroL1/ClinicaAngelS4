package dao;

import modelo.Cita;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    public void registrarCita(Cita cita) {
        String sql = "INSERT INTO Cita (id_paciente, id_medico, fecha_cita, hora, estado) VALUES (?, ?, ?, ?, 'Pendiente')";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cita> listarCitas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM Cita";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id_cita"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getString("fecha_cita"),
                    rs.getString("hora"),
                    rs.getString("estado")
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public void cancelarCita(int id) {
        String sql = "UPDATE Cita SET estado = 'Cancelada' WHERE id_cita = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
