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
            ps.setInt(2, atencion.getIdPaciente());
            ps.setInt(3, atencion.getIdMedico());
            ps.setString(4, atencion.getDiagnostico());

            // Convertimos la lista de recetas a un string separado por comas para almacenarlo en la BD
            String recetaTexto = String.join(", ", atencion.getReceta());
            ps.setString(5, recetaTexto);

            ps.setString(6, atencion.getFechaAtencion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void registrarReceta(int idAtencion, List<String> recetaLista, Connection conexion) {
        String sql = "INSERT INTO DetalleReceta (id_atencion, medicamento) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (String medicamento : recetaLista) {
                ps.setInt(1, idAtencion);
                ps.setString(2, medicamento);
                ps.executeUpdate();
            }
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
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getString("diagnostico"),
                    recuperarReceta(rs.getInt("id_atencion"), conexion), // Obtiene receta como lista
                    rs.getString("fecha_atencion")
                );
                atenciones.add(atencion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atenciones;
    }

    public List<String> recuperarReceta(int idAtencion, Connection conexion) {
        List<String> recetaLista = new ArrayList<>();
        String sql = "SELECT nombre FROM Receta WHERE id_atencion = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAtencion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                recetaLista.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recetaLista;
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
        return -1;
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
        return -1;
    }
    
    
    
}
