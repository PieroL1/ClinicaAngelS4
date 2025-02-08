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

    public void registrarCaja(String nombre, double saldoInicial, double saldoActual, Date fechaApertura, Date fechaCierre, String estado) {
        Caja caja = new Caja(0, nombre, saldoInicial, saldoActual, fechaApertura, fechaCierre, estado);
        cajaDAO.registrarCaja(caja);
    }

    public void actualizarSaldo(int idCaja, double monto) {
        cajaDAO.actualizarSaldoCaja(idCaja, monto);
    }

    public List<Caja> listarCajas() {
        return cajaDAO.listarCajas();
    }
}
