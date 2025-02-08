package controlador;

import dao.FacturaDAO;
import modelo.Factura;
import java.util.Date;
import java.util.List;

public class FacturaController {
    private FacturaDAO facturaDAO;

    public FacturaController() {
        this.facturaDAO = new FacturaDAO();
    }

    public void registrarFactura(int idPaciente, String tipo, double montoTotal, Date fechaEmision, String estado) {
        Factura factura = new Factura(0, idPaciente, tipo, montoTotal, fechaEmision, estado);
        facturaDAO.registrarFactura(factura);
    }

    public List<Factura> listarFacturas() {
        return facturaDAO.listarFacturas();
    }
}
