package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String rol;

    // Constructor para autenticación (sin nombre y apellido)
    public Usuario(int id, String usuario, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.rol = rol;
    }

    // Constructor completo con nombre y apellido
    public Usuario(int id, String nombre, String apellido, String usuario, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
