package dao;

import modelo.AtencionMedica;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtencionMedicaDAO {
    public void registrarAtencion(AtencionMedica atencion) {
        String sql = "INSERT INTO AtencionMedica (id_cita, id_paciente, id_medico, diagnostico, receta, fecha_atencion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, atencion.getIdCita());
            ps.setInt(2, atencion.getIdPaciente()); // Se agregó el id_paciente
            ps.setInt(3, atencion.getIdMedico());   // Se agregó el id_medico
            ps.setString(4, atencion.getDiagnostico());
            ps.setString(5, atencion.getReceta());
            ps.setString(6, atencion.getFechaAtencion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AtencionMedica> listarAtenciones() {
        List<AtencionMedica> atenciones = new ArrayList<>();
        String sql = "SELECT * FROM AtencionMedica";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AtencionMedica atencion = new AtencionMedica(
                    rs.getInt("id_atencion"),
                    rs.getInt("id_cita"),
                    rs.getInt("id_paciente"), // Se agregó la obtención de id_paciente
                    rs.getInt("id_medico"),   // Se agregó la obtención de id_medico
                    rs.getString("diagnostico"),
                    rs.getString("receta"),
                    rs.getString("fecha_atencion")
                );
                atenciones.add(atencion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atenciones;
    }
    
    public int obtenerIdPacienteDesdeCita(int idCita) {
        String sql = "SELECT id_paciente FROM Cita WHERE id_cita = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_paciente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Si no se encuentra el paciente
    }

    public int obtenerIdMedicoDesdeCita(int idCita) {
        String sql = "SELECT id_medico FROM Cita WHERE id_cita = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_medico");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Si no se encuentra el médico
    }

    
    
}
