package controlador;

import dao.FacturaDAO;
import modelo.Factura;
import java.util.Date;
import java.util.List;

public class FacturacionController {
    private FacturaDAO facturaDAO;

    public FacturacionController() {
        this.facturaDAO = new FacturaDAO();
    }

    public void generarFactura(int idPaciente, double montoTotal, Date fechaEmision, String estado, String tipo) {
        Factura factura = new Factura(0, idPaciente, tipo, montoTotal, fechaEmision, estado);
        facturaDAO.registrarFactura(factura);
    }

    public List<Factura> obtenerFacturas() {
        return facturaDAO.listarFacturas();
    }
}
