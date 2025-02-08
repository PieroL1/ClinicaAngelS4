package modelo;

import java.util.Date;

public class Factura {
    private int id;
    private int idPaciente;
    private String tipo;
    private double montoTotal;
    private Date fechaEmision;
    private String estado;

    public Factura(int id, int idPaciente, String tipo, double montoTotal, Date fechaEmision, String estado) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.tipo = tipo;
        this.montoTotal = montoTotal;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
    }

    public int getId() { return id; }
    public int getIdPaciente() { return idPaciente; }
    public String getTipo() { return tipo; }
    public double getMontoTotal() { return montoTotal; }
    public Date getFechaEmision() { return fechaEmision; }
    public String getEstado() { return estado; }
    
    public void setEstado(String estado) { this.estado = estado; }
}
