package controlador;

import dao.DetalleEmergenciaDAO;
import modelo.DetalleEmergencia;

public class DetalleEmergenciaController {
    private DetalleEmergenciaDAO emergenciaDAO;

    public DetalleEmergenciaController() {
        this.emergenciaDAO = new DetalleEmergenciaDAO();
    }

    public void registrarEmergencia(int idPaciente, int idEnfermera, int idMedico, String descripcion, double costo) {
        DetalleEmergencia emergencia = new DetalleEmergencia(0, idPaciente, idEnfermera, idMedico, descripcion, costo);
        emergenciaDAO.registrarEmergencia(emergencia);
    }

}
