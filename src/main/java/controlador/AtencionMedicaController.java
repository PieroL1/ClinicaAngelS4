package controlador;

import dao.AtencionMedicaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.ConexionDB;
import modelo.AtencionMedica;
import modelo.Cita;

public class AtencionMedicaController {
    private AtencionMedicaDAO atencionMedicaDAO;

    public AtencionMedicaController() {
        this.atencionMedicaDAO = new AtencionMedicaDAO();
    }

    // Ahora acepta una lista de medicamentos en vez de un solo String
    public void registrarAtencion(int idCita, String diagnostico, List<String> recetaLista, String fechaAtencion) {
        int idPaciente = atencionMedicaDAO.obtenerIdPacienteDesdeCita(idCita);
        int idMedico = atencionMedicaDAO.obtenerIdMedicoDesdeCita(idCita);

        
        // Convertimos la lista de medicamentos a un solo String separado por comas
        String recetaTexto = String.join(", ", recetaLista);

        // Creamos la instancia usando la lista original, no el string concatenado
        AtencionMedica atencion = new AtencionMedica(0, idCita, idPaciente, idMedico, diagnostico, recetaLista, fechaAtencion);


        atencionMedicaDAO.registrarAtencion(atencion);
    }

    public List<Cita> listarCitasPendientes(int idMedico) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM Cita WHERE id_medico = ? AND estado = 'Pendiente'";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id_cita"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getString("fecha_cita"),
                    rs.getString("hora"),
                    rs.getDouble("monto"),
                    rs.getString("estado")
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<AtencionMedica> listarAtenciones() {
        List<AtencionMedica> atenciones = new ArrayList<>();
        String sql = "SELECT * FROM AtencionMedica";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idAtencion = rs.getInt("id_atencion");
                List<String> recetaLista = atencionMedicaDAO.recuperarReceta(idAtencion, conexion);

                AtencionMedica atencion = new AtencionMedica(
                    idAtencion,
                    rs.getInt("id_cita"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getString("diagnostico"),
                    recetaLista, // Ahora usa la lista de recetas
                    rs.getString("fecha_atencion")
                );
                atenciones.add(atencion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atenciones;
    }


}
