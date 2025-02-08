package dao;

import modelo.Factura;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {
    
    public void registrarFactura(Factura factura) {
        String sql = "INSERT INTO Factura (id_paciente, monto_total, fecha_emision, estado, tipo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdPaciente());
            ps.setDouble(2, factura.getMontoTotal());
            ps.setDate(3, new java.sql.Date(factura.getFechaEmision().getTime())); // Fecha como SQL Date
            ps.setString(4, factura.getEstado());
            ps.setString(5, factura.getTipo());
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
                    rs.getString("tipo"), // Tipo es String
                    rs.getDouble("monto_total"), // Monto total es double
                    rs.getDate("fecha_emision"), // Fecha es tipo Date
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
