package controlador;

import dao.CitaDAO;
import modelo.Cita;
import java.util.List;

public class CitaController {
    private CitaDAO citaDAO;

    public CitaController() {
        this.citaDAO = new CitaDAO();
    }

    public void registrarCita(int idPaciente, int idMedico, String fecha, String hora) {
        Cita cita = new Cita(0, idPaciente, idMedico, fecha, hora, "Pendiente");
        citaDAO.registrarCita(cita);
    }

    public List<Cita> listarCitas() {
        return citaDAO.listarCitas();
    }

    public void cancelarCita(int id) {
        citaDAO.cancelarCita(id);
    }
}
