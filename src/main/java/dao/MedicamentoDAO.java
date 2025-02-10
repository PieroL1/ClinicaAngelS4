package dao;

import modelo.Medicamento;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    // Listar todos los medicamentos disponibles
    public List<Medicamento> listarMedicamentos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM Medicamento";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medicamento medicamento = new Medicamento(
                    rs.getInt("id_medicamento"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                medicamentos.add(medicamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    // Obtener un medicamento por ID
    public Medicamento obtenerMedicamentoPorId(int id) {
        String sql = "SELECT * FROM Medicamento WHERE id_medicamento = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medicamento(
                    rs.getInt("id_medicamento"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No se encontró el medicamento
    }

    // Actualizar el stock de un medicamento
    public boolean actualizarStock(int idMedicamento, int cantidadComprada) {
        String sql = "UPDATE Medicamento SET stock = stock - ? WHERE id_medicamento = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cantidadComprada);
            ps.setInt(2, idMedicamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    public List<Medicamento> obtenerMedicamentosPorPaciente(int idPaciente) {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT receta FROM atencionmedica WHERE id_paciente = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String receta = rs.getString("receta"); // Aquí obtenemos los medicamentos recetados como texto
                if (receta != null && !receta.isEmpty()) {
                    String[] nombresMedicamentos = receta.split(","); // Asumiendo que están separados por comas

                    for (String nombreMed : nombresMedicamentos) {
                        Medicamento medicamento = obtenerMedicamentoPorNombre(nombreMed.trim()); // Obtener el medicamento por su nombre
                        if (medicamento != null) {
                            lista.add(medicamento);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    private Medicamento obtenerMedicamentoPorNombre(String nombre) {
        String sql = "SELECT * FROM medicamento WHERE nombre = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medicamento(
                    rs.getInt("id_medicamento"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    

}
