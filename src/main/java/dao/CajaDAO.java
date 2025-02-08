package dao;

import modelo.Caja;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CajaDAO {
    public void registrarCaja(Caja caja) {
        String sql = "INSERT INTO Caja (nombre, saldo_inicial, saldo_actual, fecha_apertura, fecha_cierre, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, caja.getNombre());
            ps.setDouble(2, caja.getSaldoInicial());
            ps.setDouble(3, caja.getSaldoActual());
            ps.setDate(4, new java.sql.Date(caja.getFechaApertura().getTime()));
            ps.setDate(5, caja.getFechaCierre() != null ? new java.sql.Date(caja.getFechaCierre().getTime()) : null);
            ps.setString(6, caja.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarSaldoCaja(int idCaja, double monto) {
        String sql = "UPDATE Caja SET saldo_actual = saldo_actual + ? WHERE id_caja = ?";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setInt(2, idCaja);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Caja> listarCajas() {
        List<Caja> cajas = new ArrayList<>();
        String sql = "SELECT * FROM Caja";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Caja caja = new Caja(
                    rs.getInt("id_caja"),
                    rs.getString("nombre"),
                    rs.getDouble("saldo_inicial"),
                    rs.getDouble("saldo_actual"),
                    rs.getDate("fecha_apertura"),
                    rs.getDate("fecha_cierre"),
                    rs.getString("estado")
                );
                cajas.add(caja);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajas;
    }
}
