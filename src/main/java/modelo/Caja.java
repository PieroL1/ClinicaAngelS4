package modelo;

import java.util.Date;

public class Caja {
    private int id;
    private String nombre;
    private double saldoInicial;
    private double saldoActual;
    private Date fechaApertura;
    private Date fechaCierre;
    private String estado;

    public Caja(int id, String nombre, double saldoInicial, double saldoActual, Date fechaApertura, Date fechaCierre, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.saldoInicial = saldoInicial;
        this.saldoActual = saldoActual;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getSaldoInicial() { return saldoInicial; }
    public double getSaldoActual() { return saldoActual; }
    public Date getFechaApertura() { return fechaApertura; }
    public Date getFechaCierre() { return fechaCierre; }
    public String getEstado() { return estado; }
    
    public void setSaldoActual(double saldoActual) { this.saldoActual = saldoActual; }
    public void setEstado(String estado) { this.estado = estado; }
}
