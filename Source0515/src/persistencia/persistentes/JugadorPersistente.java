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
import logica.ssusuarios.JugadorV1;

/**
 *
 * @author docenteFI
 */
public class JugadorPersistente implements Persistente {

    private JugadorV1 u;

    public JugadorPersistente(JugadorV1 u) {
        this.u = u;
    }

    @Override
    public ArrayList<String> getInsertSql() {
        ArrayList r = new ArrayList();
        r.add("INSERT INTO Jugadores(oid,nombre,pass,saldo)"
                + "VALUES(" + getOid() + ",'" + u.getNombre() + "','"
                + u.getContrasena() + "', " + u.getSaldo() + ")");
        return r;
    }

    @Override
    public void setOid(int oid) {
        u.setOid(oid);
    }

    @Override
    public ArrayList<String> getUpdateSql() {
        ArrayList r = new ArrayList();
        r.add("UPDATE Jugadores SET nombre='" + u.getNombre()
                + "', pass='" + u.getContrasena()
                + "', saldo=" + u.getSaldo() + " WHERE oid=" + getOid());

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
        if (u == null) {
            return 0;
        } else {
            return u.getOid();
        }
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {

        u.setNombre(rs.getString("nombre"));
        u.setContrasena(rs.getString("pass"));
        u.setSaldo(rs.getDouble("saldo"));
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
