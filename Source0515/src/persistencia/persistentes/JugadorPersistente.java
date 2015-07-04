/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.persistentes;

import Persistencia.Persistente;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssusuarios.DatosUsuario;
import logica.ssusuarios.Jugador;
import logica.ssusuarios.JugadorV1;

/**
 *
 * @author docenteFI
 */
public class JugadorPersistente implements Persistente {

    private Jugador u;

    public JugadorPersistente(Jugador u) {
        this.u = u;
    }

    @Override
    public ArrayList<String> getInsertSql() {
        ArrayList r = new ArrayList();
        try {
            DatosUsuario datos = u.getDatos();
            r.add("INSERT INTO Jugadores(oid,nombre,pass,saldo)"
                    + "VALUES(" + getOid() + ",'" + u.getNombre() + "','"
                    + (datos != null ? datos.getContrasena() : "") + "', " + u.getSaldo() + ")"
            );
        } catch (RemoteException ex) {
            Logger.getLogger(JugadorPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public void setOid(int oid) {
        DatosUsuario datos;
        try {
            datos = u.getDatos();
            if (datos != null) {
                datos.setOid(oid);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(JugadorPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<String> getUpdateSql() {
        ArrayList r = new ArrayList();
        try {
            DatosUsuario datos = u.getDatos();

            r.add("UPDATE Jugadores SET nombre='" + u.getNombre()
                    + "', pass='" + (datos != null ? datos.getContrasena() : "")
                    + "', saldo=" + u.getSaldo() + " WHERE oid=" + getOid());

        } catch (RemoteException ex) {
            Logger.getLogger(JugadorPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public String getDeleteSql() {
        return "DELETE FROM Jugadores " + " WHERE oid=" + getOid();
    }

    @Override
    public String getSelectSql() {
        String r = "SELECT * from Jugadores";
        if (getOid() != 0) {
            r += " WHERE oid=" + getOid();
        }
        return r;
    }

    @Override
    public int getOid() {

        try {
            if (u != null && u.getDatos() != null) {
                return u.getDatos().getOid();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(JugadorPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {
        DatosUsuario datos;
        try {
            u.setSaldo(rs.getDouble("saldo"));
            datos = u.getDatos();
            if (datos != null) {
                datos.setNombre(rs.getString("nombre"));
                datos.setContrasena(rs.getString("pass"));
            }
        } catch (RemoteException ex) {
            Logger.getLogger(JugadorPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Persistente crearNuevo() {
        try {
            return new JugadorPersistente(new JugadorV1());
        } catch (RemoteException ex) {
            Logger.getLogger(JugadorPersistente.class.getName()).log(Level.SEVERE, null, ex);
            return new JugadorPersistente(null);
        }
    }

    @Override
    public Object getObjeto() {
        return u;
    }

    @Override
    public void limpiar() {

    }

}
