package Persistencia;

import java.sql.*;
import java.util.ArrayList;

public class ManejadorBD {

    private Connection conexion;

    private static ManejadorBD instancia;

    private ManejadorBD() {
    }

    public static ManejadorBD getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorBD();
        }
        return instancia;
    }

    public void conectar(String url) {
        try {
            conexion = DriverManager.getConnection(url);

        } catch (SQLException e1) {
            System.out.println("Error de conexión:" + e1.getMessage());
        }
    }

    public void desconectar() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión.");
        }
    }

    public void ejecutar(String sql) {
        try {
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar sql:\n" + sql + "\n" + e.getMessage());
        }
    }

    public ResultSet obtenerResultSet(String sql) {
        ResultSet rs = null;
        try {
            Statement st = conexion.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error al ejecutar sql:\n" + sql + "\n" + e.getMessage());
        }
        return rs;
    }

    public int proximoOid() {
        int oid = -1;
        try {
            String sql = "SELECT valor FROM Parametros WHERE nombre='oid'";
            ResultSet rs = this.obtenerResultSet(sql);
            if (rs.next()) {
                oid = rs.getInt("valor");
            }
            rs.close();
            oid++;
            this.ejecutar("UPDATE Parametros set valor=" + oid + " WHERE nombre='oid'");
        } catch (SQLException e) {
            System.out.println("Error al obtener el proximo oid." + e.getMessage());
        }
        return oid;
    }

    public void agregar(Persistente p) {
        int oid = this.proximoOid();
        p.setOid(oid);
        ArrayList<String> l = p.getInsertSql();
        for (String sql : l) {
            this.ejecutar(sql);
        }

    }
    /*
        	
     */

    public void modificar(Persistente p) {
        ArrayList<String> l = p.getUpdateSql();
        for (String sql : l) {
            this.ejecutar(sql);
        }
    }
//"

    public void eliminar(Persistente p) {
        String sql = p.getDeleteSql();
        p.setOid(0);
        this.ejecutar(sql);
    }

    public void leerPersistente(Persistente p) {
        try {
            String sql = p.getSelectSql();
            ResultSet rs = this.obtenerResultSet(sql);
            p.limpiar();
            while (rs.next()) {
                p.leer(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario.\n" + e.getMessage());
        }
    }

    /* VERSION ORIGINAL
     public ArrayList obtenerTodos(Persistente p) {
     ArrayList ret = new ArrayList();
     try {
     String sql = p.getSelectSql();
     System.out.println(sql);
     ResultSet rs = this.obtenerResultSet(sql);
     int oidAnt = -1;
     int oid;
     Persistente nuevo = null;
     while (rs.next()) {
     oid = rs.getInt("oid");
     if (oid != oidAnt) {
     oidAnt = oid;
     if (nuevo != null) {
     ret.add(nuevo.getObjeto());
     }
     nuevo = p.crearNuevo();
     nuevo.limpiar();
     nuevo.setOid(rs.getInt("oid"));
     }
     nuevo.leer(rs);
     }
     rs.close();
     } catch (SQLException e) {
     System.out.println("Error al obtener usuarios.\n" + e.getMessage());
     }
     return ret;
     }*/
    public ArrayList obtenerTodos(Persistente p) {
        ArrayList ret = new ArrayList();
        try {
            String sql = p.getSelectSql();
            System.out.println(sql);
            ResultSet rs = this.obtenerResultSet(sql);
            int oidAnt = -1;
            int oid;
            Persistente nuevo = null;
            while (rs.next()) {
                oid = rs.getInt("oid");
                if (oid != oidAnt) {
                    oidAnt = oid;
                    nuevo = p.crearNuevo();
                    nuevo.limpiar();
                    nuevo.setOid(rs.getInt("oid"));
                    
                    ret.add(nuevo.getObjeto());
                }
                nuevo.leer(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios.\n" + e.getMessage());
        }
        return ret;
    }
}
