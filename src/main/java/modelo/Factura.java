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

    public int getId() {
        return id;
    }

    public void setId(int id) {  // ← ✅ Nuevo método agregado
        this.id = id;
    }
    public int getIdPaciente() { return idPaciente; }
    public String getTipo() { return tipo; }
    public double getMontoTotal() { return montoTotal; }
    public Date getFechaEmision() { return fechaEmision; }
    public String getEstado() { return estado; }
    
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getIdCaja() {
        // Suponiendo que el ID de la caja está vinculado con el tipo de factura.
        // Podrías necesitar hacer una consulta para recuperar el id_caja basado en el tipo.
        if (this.tipo.equals("Emergencia")) return 1;
        if (this.tipo.equals("Consulta")) return 2;
        if (this.tipo.equals("Farmacia")) return 3;
        return 0; // Valor por defecto si no se encuentra una coincidencia
    }

    
}
