/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.persistentes;

import Persistencia.Persistente;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.DatosPartidaJuegoCasino;
import logica.Jugador;

/**
 *
 * @author Romi
 */
public class PartidaJuegoPersistente implements Persistente {

    private final SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
    private DatosPartidaJuegoCasino u;

    public PartidaJuegoPersistente(DatosPartidaJuegoCasino u) {
        this.u = u;
    }

    @Override
    public ArrayList<String> getInsertSql() {
        ArrayList r = new ArrayList();
        r.add("INSERT INTO Partidas(oid,numero,comienzo,final,duracion,total_apostado,codigo_juego)"
                + " VALUES(" + getOid() + "," + u.getNumeroPartida() + ", '"
                + formato.format(u.getComienzo()) + "', '"
                + (u.getFinal() != null ? formato.format(u.getFinal()) : null) + "', "
                + u.getDuracion() / 60000 + ", " + u.getTotalApostado() + ", " + getCodigoJuego() + ")");
        //guarda los jugadores solo la primera vez
        if (!u.isFinalizada()) {
            for (Map.Entry<Jugador, Double> entry : u.getJugadores().entrySet()) {
                Jugador j = entry.getKey();
                Double ganancias = entry.getValue();
                try {
                    r.add("INSERT INTO Participantes(nombre,ganador,numero_partida,ganancias) "
                            + "VALUES('" + j.getNombre() + "', " + false + ", " + u.getNumeroPartida() + ", " + ganancias + ")");
                } catch (Exception ex) {
                    Logger.getLogger(PartidaJuegoPersistente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return r;
    }

    @Override
    public void setOid(int oid) {
        u.setOid(oid);
    }

    @Override
    public ArrayList<String> getUpdateSql() {
        ArrayList r = new ArrayList();
        r.add("UPDATE Partidas SET numero=" + u.getNumeroPartida()
                + ", duracion=" + u.getDuracion() / 60000
                + ", comienzo='" + formato.format(u.getComienzo())
                + "', final='" + (u.getFinal() != null ? formato.format(u.getFinal()) : null)
                + "', total_apostado=" + u.getTotalApostado() + " WHERE oid=" + getOid());
        //se actualiza para setear el ganador y las ganancias del jugador
        for (Map.Entry<Jugador, Double> entry : u.getJugadores().entrySet()) {
            Jugador j = entry.getKey();
            Double ganancias = entry.getValue();

            r.add("UPDATE Participantes "
                    + "SET ganador=" + j.equals(u.getGanador()) + ", "
                    + "ganancias=" + ganancias
                    + " WHERE nombre='" + j.getNombre() + "' "
                    + "AND numero_partida=" + u.getNumeroPartida());
        }

        return r;
    }

    @Override
    public String getDeleteSql() {
        return "DELETE FROM Partidas " + " WHERE oid=" + getOid();
    }

    @Override
    public String getSelectSql() {
        String r = "SELECT * "
                + "FROM partidas f, participantes l "
                + "WHERE f.numero=l.numero_partida ";
        if (getOid() != 0) {
            r += " AND f.oid=" + getOid();
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
        u.setOid(rs.getInt("oid"));
        u.setNumero(rs.getInt("numero"));
        u.setDuracion(rs.getLong("duracion"));
        u.setTotalApostado(rs.getDouble("total_apostado"));
        try {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("nombre"));
            u.agregarJugador(j, rs.getDouble("ganancias"));
            if (rs.getBoolean("ganador")) {
                u.setGanador(j);
            }

            u.setComienzo((Date) formato.parse(rs.getString("comienzo")));
            u.setFinal((Date) formato.parse(rs.getString("final")));
        } catch (Exception ex) {
            Logger.getLogger(PartidaJuegoPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getObjeto() {
        return u;
    }

    @Override
    public void limpiar() {

    }

    protected int getCodigoJuego() {
        return 0;
    }

    @Override
    public Persistente crearNuevo() {
        return new PartidaJuegoPersistente(new DatosPartidaJuegoCasino(0, null));
    }

}
