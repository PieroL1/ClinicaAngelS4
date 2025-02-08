package dao;

import modelo.Pago;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {
    public void registrarPago(Pago pago) {
        String sql = "INSERT INTO Pago (id_factura, id_caja, monto, fecha_pago, metodo_pago) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pago.getIdFactura());
            ps.setInt(2, pago.getIdCaja());
            ps.setDouble(3, pago.getMonto());
            ps.setDate(4, new java.sql.Date(pago.getFechaPago().getTime())); // Convertimos Date a SQL Date
            ps.setString(5, pago.getMetodoPago());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pago> listarPagos() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM Pago";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pago pago = new Pago(
                    rs.getInt("id_pago"),
                    rs.getInt("id_factura"),
                    rs.getInt("id_caja"),
                    rs.getDouble("monto"),
                    rs.getDate("fecha_pago"),
                    rs.getString("metodo_pago")
                );
                pagos.add(pago);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }
}
