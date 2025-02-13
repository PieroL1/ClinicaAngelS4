package modelo;

public class Cita {
    private int id;
    private int idPaciente;
    private int idMedico;
    private String fecha;
    private String hora;
    private double monto;  // Se agrega el campo monto
    private String estado;

    public Cita(int id, int idPaciente, int idMedico, String fecha, String hora, double monto, String estado) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.monto = monto;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public double getMonto() { return monto; }  // Getter para monto
    public void setMonto(double monto) { this.monto = monto; }  // Setter para monto
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
