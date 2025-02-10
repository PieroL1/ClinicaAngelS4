package modelo;

import java.util.List;

public class AtencionMedica {
    private int idAtencion;
    private int idCita;
    private int idPaciente;
    private int idMedico;
    private String diagnostico;
    private List<String> receta;  // Se cambia a lista en lugar de String
    private String fechaAtencion;

    public AtencionMedica(int idAtencion, int idCita, int idPaciente, int idMedico, String diagnostico, List<String> receta, String fechaAtencion) {
        this.idAtencion = idAtencion;
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.diagnostico = diagnostico;
        this.receta = receta;
        this.fechaAtencion = fechaAtencion;
    }

    public List<String> getReceta() { return receta; }
    public void setReceta(List<String> receta) { this.receta = receta; }

    public int getIdAtencion() { return idAtencion; }
    public int getIdCita() { return idCita; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdMedico() { return idMedico; }
    public String getDiagnostico() { return diagnostico; }
    public String getFechaAtencion() { return fechaAtencion; }
    
    
    
}
