package controlador;

import dao.FacturaDAO;
import modelo.Factura;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public Factura obtenerFacturaPorId(int idFactura) {
        return facturaDAO.obtenerFacturaPorId(idFactura);
    }

    // ✅ Nuevo método para generar una factura de medicamentos
    public void generarFacturaMedicamentos(Map<Integer, Integer> carrito, double total) {
        Factura factura = new Factura(0, 0, "Medicamentos", total, new Date(), "Pendiente");
        int idFactura = facturaDAO.registrarFactura(factura); // Guardamos la factura y obtenemos su ID

        if (idFactura > 0) {
            // Guardamos cada medicamento comprado en la base de datos
            facturaDAO.registrarDetalleMedicamentos(idFactura, carrito);
        }
    }

    
    public int generarFactura(int idPaciente, double montoTotal, Date fechaEmision, String estado, String tipo) {
        Factura factura = new Factura(0, idPaciente, tipo, montoTotal, fechaEmision, estado);
        return facturaDAO.registrarFactura(factura);
    }

    
}
