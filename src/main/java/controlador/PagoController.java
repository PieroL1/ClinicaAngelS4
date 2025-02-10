package controlador;

import dao.PagoDAO;
import dao.CajaDAO;
import dao.FacturaDAO;
import modelo.Pago;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class PagoController {
    private PagoDAO pagoDAO;
    private CajaDAO cajaDAO;
    private FacturaDAO facturaDAO;

    public PagoController() {
        this.pagoDAO = new PagoDAO();
        this.cajaDAO = new CajaDAO();
        this.facturaDAO = new FacturaDAO();
    }

    public void registrarPago(int idFactura, int idCaja, double monto, Date fechaPago, String metodoPago) {

        // Conversión de java.util.Date a java.sql.Date
        java.sql.Date sqlFechaPago = new java.sql.Date(fechaPago.getTime());

        pagoDAO.registrarPago(idFactura, idCaja, monto, sqlFechaPago, metodoPago);
       
        
        // Actualizar saldo en la caja
        cajaDAO.actualizarSaldoCaja(idCaja, monto);
        
        // Marcar la factura como pagada
        facturaDAO.actualizarEstadoFactura(idFactura, "Pagado");
    }

    public List<Pago> listarPagos() {
        return pagoDAO.listarPagos();
    }
}
