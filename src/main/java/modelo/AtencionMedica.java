package modelo;

public class AtencionMedica {
    private int id;
    private int idCita;
    private int idPaciente;
    private int idMedico;
    private String diagnostico;
    private String receta;
    private String fechaAtencion;

    public AtencionMedica(int id, int idCita, int idPaciente, int idMedico, String diagnostico, String receta, String fechaAtencion) {
        this.id = id;
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.diagnostico = diagnostico;
        this.receta = receta;
        this.fechaAtencion = fechaAtencion;
    }

    public int getId() { return id; }
    public int getIdCita() { return idCita; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdMedico() { return idMedico; }
    public String getDiagnostico() { return diagnostico; }
    public String getReceta() { return receta; }
    public String getFechaAtencion() { return fechaAtencion; }
}
