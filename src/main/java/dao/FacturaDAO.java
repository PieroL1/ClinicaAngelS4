package dao;

import modelo.Factura;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FacturaDAO {
    
    public int registrarFactura(Factura factura) {
        String sql = "INSERT INTO Factura (id_paciente, monto_total, fecha_emision, estado, tipo) VALUES (?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, factura.getIdPaciente());
            ps.setDouble(2, factura.getMontoTotal());
            ps.setDate(3, new java.sql.Date(factura.getFechaEmision().getTime()));
            ps.setString(4, factura.getEstado());
            ps.setString(5, factura.getTipo());
            ps.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }


    public List<Factura> listarFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM Factura";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Factura factura = new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_paciente"),
                    rs.getString("tipo"),
                    rs.getDouble("monto_total"),
                    rs.getDate("fecha_emision"),
                    rs.getString("estado")
                );
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }
    
    public void actualizarEstadoFactura(int idFactura, String estado) {
        String sql = "UPDATE Factura SET estado = ? WHERE id_factura = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idFactura);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Factura obtenerFacturaPorId(int idFactura) {
        String sql = "SELECT id_factura, id_paciente, tipo, monto_total, fecha_emision, estado FROM Factura WHERE id_factura = ?";
        Factura factura = null;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                factura = new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_paciente"),
                    rs.getString("tipo"),
                    rs.getDouble("monto_total"),
                    rs.getDate("fecha_emision"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factura;
    }

    // ✅ Nuevo método para registrar una factura de medicamentos
    public int registrarFacturaMedicamentos(double montoTotal) {
        String sql = "INSERT INTO Factura (id_paciente, monto_total, fecha_emision, estado, tipo) VALUES (?, ?, ?, ?, ?)";
        int idFactura = -1;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, 0); // No se asocia a un paciente específico
            ps.setDouble(2, montoTotal);
            ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            ps.setString(4, "Pendiente");
            ps.setString(5, "Medicamentos");
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idFactura = rs.getInt(1); // Obtener el ID de la factura creada
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idFactura;
    }

    // ✅ Nuevo método para registrar los detalles de la compra de medicamentos
    public void registrarDetalleMedicamentos(int idFactura, Map<Integer, Integer> carrito) {
        String sql = "INSERT INTO DetalleFactura (id_factura, id_medicamento, cantidad) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
                ps.setInt(1, idFactura);
                ps.setInt(2, entry.getKey()); // ID del medicamento
                ps.setInt(3, entry.getValue()); // Cantidad comprada
                ps.addBatch(); // Agregar al batch
            }
            ps.executeBatch(); // Ejecutar batch para optimizar inserciones
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
