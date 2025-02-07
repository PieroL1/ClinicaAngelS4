package controlador;

import dao.PacienteDAO;
import modelo.Paciente;
import java.util.List;

public class PacienteController {
    private PacienteDAO pacienteDAO;

    public PacienteController() {
        this.pacienteDAO = new PacienteDAO();
    }

    public void agregarPaciente(String nombre, String apellido, String dni, String fechaNacimiento, String telefono, String direccion) {
        Paciente paciente = new Paciente(0, nombre, apellido, dni, fechaNacimiento, telefono, direccion);
        pacienteDAO.agregarPaciente(paciente);
    }

    public void editarPaciente(int id, String nombre, String apellido, String dni, String fechaNacimiento, String telefono, String direccion) {
        Paciente paciente = new Paciente(id, nombre, apellido, dni, fechaNacimiento, telefono, direccion);
        pacienteDAO.editarPaciente(paciente);
    }

    public void eliminarPaciente(int id) {
        pacienteDAO.eliminarPaciente(id);
    }

    public List<Paciente> listarPacientes() {
        return pacienteDAO.listarPacientes();
    }
}
