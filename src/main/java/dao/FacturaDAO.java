package dao;

import modelo.Factura;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {
    public void registrarFactura(Factura factura) {
        String sql = "INSERT INTO Factura (id_paciente, tipo, monto_total, fecha_emision, estado) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdPaciente());
            ps.setString(2, factura.getTipo());
            ps.setDouble(3, factura.getMontoTotal());
            ps.setDate(4, new java.sql.Date(factura.getFechaEmision().getTime()));
            ps.setString(5, factura.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
