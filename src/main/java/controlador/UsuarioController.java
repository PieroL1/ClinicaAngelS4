package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Obtiene la lista de médicos registrados en la base de datos.
     * @return Lista de usuarios con rol "Medico".
     */
    public List<Usuario> obtenerMedicos() {
        List<Usuario> medicos = usuarioDAO.obtenerMedicos();
        return (medicos != null) ? medicos : List.of(); // Retorna lista vacía si no hay médicos
    }

    /**
     * Busca un usuario por su ID.
     * @param idUsuario ID del usuario.
     * @return Usuario encontrado o null si no existe.
     */
    public Usuario buscarUsuarioPorId(int idUsuario) {
        Usuario usuario = usuarioDAO.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.err.println("Usuario con ID " + idUsuario + " no encontrado.");
        }
        return usuario;
    }

    /**
     * Verifica las credenciales de un usuario para autenticación.
     * @param usuario Nombre de usuario.
     * @param password Contraseña del usuario.
     * @return Usuario autenticado o null si las credenciales son incorrectas.
     */
    public Usuario verificarUsuario(String usuario, String password) {
        return usuarioDAO.autenticarUsuario(usuario, password);
    }
}
