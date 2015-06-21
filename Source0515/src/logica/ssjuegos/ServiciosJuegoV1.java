/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos;

import Persistencia.ManejadorBD;
import java.util.ArrayList;
import logica.ssjuegos.FabricadorJuegosCasino.CodigosJuego;
import logica.ssjuegos.poker.PartidaPokerV1;
import persistencia.Parametro;
import persistencia.persistentes.ParametrosPersistente;
import persistencia.persistentes.PartidaJuegoPersistente;

/**
 *
 * @author Romi
 */
public class ServiciosJuegoV1 implements ServiciosJuego {

    private final String stringConexion = "jdbc:mysql://localhost/casino?user=root&password=root";
    //TODO conectarme a la BD y eso
    ArrayList<PartidaJuegoCasinoV1> partidas = new ArrayList<>();

    private ManejadorBD manejador = ManejadorBD.getInstancia();

    @Override
    public ArrayList<JuegoCasinoV1> getJuegos() {
        ArrayList<JuegoCasinoV1> juegosCasino = FabricadorJuegosCasino.getJuegosCasino();

        ParametrosPersistente persistente = new ParametrosPersistente(new Parametro("ultima_partida", 0));

        manejador.conectar(stringConexion);
        manejador.leerPersistente(persistente);
        double ultimoNumeroPartida = ((Parametro) persistente.getObjeto()).getValor();

        //consulta a la bd para obtener el ultimo numero de partida
        for (JuegoCasinoV1 juegoCasino : juegosCasino) {
            juegoCasino.setUltimoNumeroPartida((int) ultimoNumeroPartida);
        }

        return juegosCasino;
    }

    @Override
    public double getGanancias() {
        ParametrosPersistente persistenteGanancias = new ParametrosPersistente(new Parametro("ganancias", 0));

        manejador.conectar(stringConexion);
        manejador.leerPersistente(persistenteGanancias);

        return ((Parametro) persistenteGanancias.getObjeto()).getValor();
    }

    @Override
    public void setGanancias(double ganancias) {
        ParametrosPersistente persistenteGanancias = new ParametrosPersistente(new Parametro("ganancias", ganancias));

        manejador.conectar(stringConexion);
        manejador.modificar(persistenteGanancias);
    }

    @Override
    public ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(CodigosJuego codigoJuego) {
        PartidaJuegoPersistente persistente;

        persistente = new PartidaJuegoPersistente(new DatosPartidaJuegoCasino(-1, codigoJuego));

        manejador.conectar(stringConexion);
        return manejador.obtenerTodos(persistente);
    }

    @Override
    public void guardar(DatosPartidaJuegoCasino p) {
        PartidaJuegoPersistente persistentePartida = new PartidaJuegoPersistente(p);

        manejador.conectar(stringConexion);
        manejador.agregar(persistentePartida);

        ParametrosPersistente persistente = new ParametrosPersistente(new Parametro("ultima_partida", p.getNumeroPartida()));

        manejador.conectar(stringConexion);
        manejador.modificar(persistente);
    }

    @Override
    public void modificar(DatosPartidaJuegoCasino p) {
        PartidaJuegoPersistente persistente = new PartidaJuegoPersistente(p);

        manejador.conectar(stringConexion);
        manejador.modificar(persistente);
    }

}
