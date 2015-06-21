package logica;

import java.util.HashMap;

public class SsUsuarios {

    private ServiciosUsuario servicios;

    private static SsUsuarios instancia;
    private HashMap<String, Usuario> usuariosRegistrados = new HashMap<>();
    private HashMap<String, Usuario> usuariosLogueados = new HashMap<>();

    private SsUsuarios() {
        servicios = new ServiciosUsuarioV1();
        cargarDatosUsuarios();
    }

    public static SsUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new SsUsuarios();
        }
        return instancia;
    }

    public Usuario ingresar(String nombreUsuario, String contrasena)
            throws Exception {
        Usuario jugadorRegistrado = usuariosRegistrados.get(nombreUsuario);
        if (jugadorRegistrado == null
                || !jugadorRegistrado.getContrasena().equals(contrasena)) {
            // TODO hardcoded
            throw new Exception("El usuario " + nombreUsuario
                    + " no existe o la contrasena es incorrecta.");
        } else if (usuariosLogueados.containsKey(nombreUsuario)) {
            // TODO hardcoded
            throw new Exception("El usuario " + nombreUsuario
                    + " ya esta logueado.");
        } else {
            usuariosLogueados.put(jugadorRegistrado.getNombre(),
                    jugadorRegistrado);
            return jugadorRegistrado;
        }
    }

    public void cargarDatosUsuarios() {
        usuariosRegistrados = servicios.getUsuarios();
    }
}
