package logica.ssusuarios;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public abstract class UsuarioV1 extends UnicastRemoteObject implements Usuario {

    private DatosUsuario datos;

    public UsuarioV1() throws RemoteException {
        datos = new DatosUsuario();
    }

    public UsuarioV1(String nombre) throws RemoteException {
        datos = new DatosUsuario(nombre);
    }

    public UsuarioV1(String nombre, String contrasena) throws RemoteException {
        datos = new DatosUsuario(nombre, contrasena);
    }

    public String getNombre() {
        return datos.getNombre();
    }

    public void setNombre(String nombre) {
        datos.setNombre(nombre);
    }

    public String getContrasena() {
        return datos.getContrasena();
    }

    public void setContrasena(String contrasena) {
        datos.setContrasena(contrasena);
    }

    public int getOid() {
        return datos.getOid();
    }

    public void setOid(int oid) {
        datos.setOid(oid);
    }

    @Override
    public DatosUsuario getDatos() {
        return datos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioV1 other = (UsuarioV1) obj;
        if (!Objects.equals(getNombre(), other.getNombre())) {
            return false;
        }
        return Objects.equals(this.getContrasena(), other.getContrasena());
    }

}
