package controlador;

import dao.DetalleEmergenciaDAO;
import modelo.DetalleEmergencia;
import java.util.List;

public class EmergenciaController {
    private DetalleEmergenciaDAO detalleEmergenciaDAO;

    public EmergenciaController() {
        this.detalleEmergenciaDAO = new DetalleEmergenciaDAO();
    }

    public void registrarDetalleEmergencia(int idPaciente, int idEnfermera, int idMedico, String descripcion, double costo) {
        DetalleEmergencia detalle = new DetalleEmergencia(0, idPaciente, idEnfermera, idMedico, descripcion, costo);
        detalleEmergenciaDAO.registrarDetalleEmergencia(detalle);
    }

    public List<DetalleEmergencia> listarDetallesEmergencia() {
        return detalleEmergenciaDAO.listarDetallesEmergencia();
    }
}
