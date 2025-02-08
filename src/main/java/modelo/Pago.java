package modelo;

import java.util.Date;

public class Pago {
    private int id;
    private int idFactura;
    private int idCaja;
    private double monto;
    private Date fechaPago;
    private String metodoPago;

    public Pago(int id, int idFactura, int idCaja, double monto, Date fechaPago, String metodoPago) {
        this.id = id;
        this.idFactura = idFactura;
        this.idCaja = idCaja;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
    }

    public int getId() { return id; }
    public int getIdFactura() { return idFactura; }
    public int getIdCaja() { return idCaja; }
    public double getMonto() { return monto; }
    public Date getFechaPago() { return fechaPago; }
    public String getMetodoPago() { return metodoPago; }
}
