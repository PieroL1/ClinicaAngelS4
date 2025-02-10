package controlador;

import dao.CajaDAO;
import modelo.Caja;
import java.util.Date;
import java.util.List;

public class CajaController {
    private CajaDAO cajaDAO;

    public CajaController() {
        this.cajaDAO = new CajaDAO();
    }

    public void registrarCaja(String nombre, double saldoInicial, double saldoActual, Date fechaApertura, String estado) {
        Caja caja = new Caja(0, nombre, saldoInicial, saldoActual, fechaApertura, null, estado);
        cajaDAO.registrarCaja(caja);
    }

    public void actualizarSaldo(int idCaja, double monto) {
        cajaDAO.actualizarSaldoCaja(idCaja, monto);
    }

    public List<Caja> listarCajas() {
        return cajaDAO.listarCajas();
    }

    public void abrirCaja(int idCaja) {
        cajaDAO.actualizarEstadoCaja(idCaja, "Abierta");
    }

    public void cerrarCaja(int idCaja) {
        cajaDAO.actualizarEstadoCaja(idCaja, "Cerrada");
    }

    public Caja obtenerCajaPorId(int idCaja) {
        return cajaDAO.obtenerCajaPorId(idCaja);
    }

    public void registrarIngreso(int idCaja, double monto) {
        cajaDAO.registrarIngreso(idCaja, monto);
    }

    public void registrarEgreso(int idCaja, double monto) {
        cajaDAO.registrarEgreso(idCaja, monto);
    }

    public List<String[]> obtenerHistorialTransacciones(int idCaja) {
        return cajaDAO.obtenerHistorialTransacciones(idCaja);
    }

}
