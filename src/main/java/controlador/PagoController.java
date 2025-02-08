package controlador;

import dao.PagoDAO;
import modelo.Pago;
import java.util.Date;
import java.util.List;

public class PagoController {
    private PagoDAO pagoDAO;

    public PagoController() {
        this.pagoDAO = new PagoDAO();
    }

    public void registrarPago(int idFactura, int idCaja, double monto, Date fechaPago, String metodoPago) {
        Pago pago = new Pago(0, idFactura, idCaja, monto, fechaPago, metodoPago);
        pagoDAO.registrarPago(pago);
    }

    public List<Pago> listarPagos() {
        return pagoDAO.listarPagos();
    }
}
