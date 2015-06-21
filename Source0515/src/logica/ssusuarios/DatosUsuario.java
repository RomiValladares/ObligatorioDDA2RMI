/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssusuarios;

import java.io.Serializable;

/**
 *
 * @author Romi
 */
public class DatosUsuario implements Serializable {

    private String nombre, contrasena;
    private int oid;

    public DatosUsuario() {
    }

    public DatosUsuario(String nombre) {
        this.nombre = nombre;
    }

    public DatosUsuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

}
