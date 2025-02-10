package dao;

import modelo.Caja;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CajaDAO {

    public void registrarCaja(Caja caja) {
        String sql = "INSERT INTO Caja (nombre, saldo_inicial, saldo_actual, fecha_apertura, estado) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, caja.getNombre());
            ps.setDouble(2, caja.getSaldoInicial());
            ps.setDouble(3, caja.getSaldoActual());
            ps.setDate(4, new java.sql.Date(caja.getFechaApertura().getTime()));
            ps.setString(5, caja.getEstado());
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


    public void actualizarEstadoCaja(int idCaja, String estado) {
        String sql = "UPDATE Caja SET estado = ? WHERE id_caja = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
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

    public Caja obtenerCajaPorId(int idCaja) {
        String sql = "SELECT * FROM Caja WHERE id_caja = ?";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCaja);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Caja(
                    rs.getInt("id_caja"),
                    rs.getString("nombre"),
                    rs.getDouble("saldo_inicial"),
                    rs.getDouble("saldo_actual"),
                    rs.getDate("fecha_apertura"),
                    rs.getDate("fecha_cierre"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String[]> obtenerHistorialTransacciones(int idCaja) {
        List<String[]> transacciones = new ArrayList<>();
        String sql = "SELECT tipo, monto, fecha FROM Transacciones WHERE id_caja = ? ORDER BY fecha DESC";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCaja);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] transaccion = {
                    rs.getString("tipo"),
                    "S/ " + rs.getDouble("monto"),
                    rs.getTimestamp("fecha").toString()
                };
                transacciones.add(transaccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transacciones;
    }


    public void registrarIngreso(int idCaja, double monto) {
        String sql = "INSERT INTO Transacciones (id_caja, tipo, monto) VALUES (?, 'Ingreso', ?)";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCaja);
            ps.setDouble(2, monto);
            ps.executeUpdate();
            actualizarSaldoCaja(idCaja, monto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void registrarEgreso(int idCaja, double monto) {
        String sql = "INSERT INTO Transacciones (id_caja, tipo, monto) VALUES (?, 'Egreso', ?)";
        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCaja);
            ps.setDouble(2, monto);
            ps.executeUpdate();
            actualizarSaldoCaja(idCaja, -monto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
