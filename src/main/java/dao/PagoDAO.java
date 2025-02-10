package dao;

import modelo.Pago;
import database.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {
public void registrarPago(int idFactura, int idCaja, double monto, Date fechaPago, String metodoPago) {
    String sql = "INSERT INTO Pago (id_factura, id_caja, monto, fecha_pago, metodo_pago) VALUES (?, ?, ?, ?, ?)";
    String updateFactura = "UPDATE Factura SET estado = 'Pagado' WHERE id_factura = ?";

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         PreparedStatement psFactura = conexion.prepareStatement(updateFactura)) {

        ps.setInt(1, idFactura);
        ps.setInt(2, idCaja);
        ps.setDouble(3, monto);
        ps.setDate(4, fechaPago); // Ahora fechaPago es java.sql.Date
        ps.setString(5, metodoPago);
        ps.executeUpdate();

        // Actualizar el estado de la factura a "Pagado"
        psFactura.setInt(1, idFactura);
        psFactura.executeUpdate();

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
