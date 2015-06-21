/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.persistentes;

import Persistencia.Persistente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import persistencia.Parametro;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Romi Permite ABM de datos en la tabla Parametros
 */
public class ParametrosPersistente implements Persistencia.Persistente {

    private Parametro param;

    public ParametrosPersistente(Parametro p) {
        param = p;
    }

    @Override
    public ArrayList<String> getInsertSql() {
        ArrayList r = new ArrayList();
        r.add("INSERT INTO Parametros(nombre,valor)"
                + "VALUES('" + param.getNombre() + ",'" + param.getValor() + "')");
        return r;
    }

    @Override
    public void setOid(int oid) {
        throw new NotImplementedException();
    }

    @Override
    public ArrayList<String> getUpdateSql() {
        ArrayList r = new ArrayList();
        r.add("UPDATE Parametros SET "
                + " valor='" + param.getValor()
                + "'"
                + " WHERE nombre='" + param.getNombre() + "'");
        return r;
    }

    @Override
    public String getDeleteSql() {
        return "DELETE FROM Parametros " + " WHERE nombre='" + param.getNombre() + "'";
    }

    @Override
    public String getSelectSql() {
        String r = "SELECT * from Parametros";
        if (param != null && param.getNombre() != null && !param.getNombre().equals("")) {
            r += " WHERE nombre='" + param.getNombre() + "'";
        }
        return r;
    }

    @Override
    public int getOid() {
        return 0;
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {
        param.setNombre(rs.getString("nombre"));
        param.setValor(rs.getInt("valor"));
    }

    @Override
    public Persistente crearNuevo() {
        return new ParametrosPersistente(new Parametro());
    }

    @Override
    public Object getObjeto() {
        return param;
    }

    @Override
    public void limpiar() {

    }

}
