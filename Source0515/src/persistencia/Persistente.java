/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author docenteFI
 */
public interface Persistente {

    public ArrayList<String> getInsertSql();

    public void setOid(int oid);

    public ArrayList<String> getUpdateSql();

    public String getDeleteSql();

    public String getSelectSql();

    public int getOid();

    public void leer(ResultSet rs) throws SQLException;

    public Persistente crearNuevo();

    public Object getObjeto();

    public void limpiar();

}
