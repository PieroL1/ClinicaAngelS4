package modelo;

public class Medicamento {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int cantidad; // 🔹 Nuevo atributo para manejar la cantidad recetada

    public Medicamento(int id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.cantidad = 0; // Por defecto
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public String toString() {
        return nombre; // Muestra solo el nombre del medicamento
    }

    
    // Getter y Setter de cantidad
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
