package modelo;

public class DetalleEmergencia {
    private int id;
    private int idPaciente;
    private int idEnfermera;
    private int idMedico;
    private String descripcion;
    private double costo;

    public DetalleEmergencia(int id, int idPaciente, int idEnfermera, int idMedico, String descripcion, double costo) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idEnfermera = idEnfermera;
        this.idMedico = idMedico;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public int getId() { return id; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdEnfermera() { return idEnfermera; }
    public int getIdMedico() { return idMedico; }
    public String getDescripcion() { return descripcion; }
    public double getCosto() { return costo; }
}
