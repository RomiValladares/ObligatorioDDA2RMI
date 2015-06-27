/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos;

import Persistencia.ManejadorBD;
import java.util.ArrayList;
import java.util.Observable;
import logica.ssjuegos.FabricadorJuegosCasino.CodigosJuego;
import persistencia.Parametro;
import persistencia.persistentes.ParametrosPersistente;
import persistencia.persistentes.PartidaJuegoPersistente;

/**
 * Observable porque el SsJuegos lo observa para enterarse cuando el ultimo
 * numero partida cambia
 */
public class ServiciosJuegoV1 extends Observable {

    private final String stringConexion = "jdbc:mysql://localhost/casino?user=root&password=root";
    //TODO conectarme a la BD y eso
    ArrayList<PartidaJuegoCasinoV1> partidas = new ArrayList<>();

    private ManejadorBD manejador = ManejadorBD.getInstancia();

    public ArrayList<JuegoCasinoV1> getJuegos() {
        ArrayList<JuegoCasinoV1> juegosCasino = FabricadorJuegosCasino.getJuegosCasino();

        int ultimoNumeroPartida = getUltimoNumeroPartida();

        //consulta a la bd para obtener el ultimo numero de partida
        for (JuegoCasinoV1 juegoCasino : juegosCasino) {
            juegoCasino.setUltimoNumeroPartida((int) ++ultimoNumeroPartida);
            //crea la partida DESPUES de haber obtenido el ultimo numero partida
            juegoCasino.crearPartida();
        }

        return juegosCasino;
    }

    public int getUltimoNumeroPartida() {
        ParametrosPersistente persistente = new ParametrosPersistente(new Parametro("ultima_partida", 0));

        manejador.conectar(stringConexion);
        manejador.leerPersistente(persistente);
        return (int) ((Parametro) persistente.getObjeto()).getValor();
    }

    public double getGanancias() {
        ParametrosPersistente persistenteGanancias = new ParametrosPersistente(new Parametro("ganancias", 0));

        manejador.conectar(stringConexion);
        manejador.leerPersistente(persistenteGanancias);

        return ((Parametro) persistenteGanancias.getObjeto()).getValor();
    }

    public void setGanancias(double ganancias) {
        ParametrosPersistente persistenteGanancias = new ParametrosPersistente(new Parametro("ganancias", ganancias));

        manejador.conectar(stringConexion);
        manejador.modificar(persistenteGanancias);
    }

    public ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(CodigosJuego codigoJuego) {
        PartidaJuegoPersistente persistente;

        persistente = new PartidaJuegoPersistente(new DatosPartidaJuegoCasino(-1, codigoJuego));

        manejador.conectar(stringConexion);
        return manejador.obtenerTodos(persistente);
    }

    public void guardar(DatosPartidaJuegoCasino p) {
        PartidaJuegoPersistente persistentePartida = new PartidaJuegoPersistente(p);

        manejador.conectar(stringConexion);
        manejador.agregar(persistentePartida);
    }

    public void guardar(int ultimoNumeroPartida) {
        ParametrosPersistente persistente = new ParametrosPersistente(new Parametro("ultima_partida", ultimoNumeroPartida));

        manejador.conectar(stringConexion);
        manejador.modificar(persistente);
    }

    public void modificar(DatosPartidaJuegoCasino p) {
        PartidaJuegoPersistente persistente = new PartidaJuegoPersistente(p);

        manejador.conectar(stringConexion);
        manejador.modificar(persistente);
    }

}
