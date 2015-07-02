package logica.ssjuegos;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import logica.ssjuegos.JuegoCasinoV1.EventosJuegoCasino;
import logica.ssusuarios.Jugador;
import logica.ssusuarios.JugadorV1;
import logica.ssusuarios.SsUsuarios;

//Observer porque observa los juegos para saber cuando se produce una nueva ganancia
// y a ServiciosJuego para enterarse cuando cambia el ultimo numero partida
//Observable porque la fachada lo observa
public class SsJuegos extends Observable implements Observer {

    private final ServiciosJuegoV1 servicios;

    private static SsJuegos instancia;
    private ArrayList<JuegoCasinoV1> juegos;
    private double ganancias;

    //lo guardo en memoria para que no sea necesario consultar la BD
    private int ultimoNumeroPartida;

    private SsJuegos() {
//        juegos = FabricadorJuegosCasino.getJuegosCasino();
        servicios = new ServiciosJuegoV1();
        servicios.addObserver(this);
        juegos = servicios.getJuegos();
        ganancias = servicios.getGanancias();
        for (JuegoCasinoV1 juego : juegos) {
            juego.registrar(this);
        }
        ultimoNumeroPartida = servicios.getUltimoNumeroPartida();
        System.out.println("SsJuegos ultimoNumeroPartida=" + ultimoNumeroPartida);
    }

    public static SsJuegos getInstancia() {
        if (instancia == null) {
            instancia = new SsJuegos();
        }
        return instancia;
    }

    public ArrayList<JuegoCasino> getJuegos() {
        return new ArrayList<JuegoCasino>(juegos);
    }

    public double getGanancias() {
        return ganancias;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("SsJuegos update arg=" + arg);
        if (arg instanceof EventosJuegoCasino) {
            actualizarGanancias();
        }

        setChanged();
        notifyObservers();
    }

    protected void actualizarGanancias() {
        //ganancias = 0;
        for (JuegoCasinoV1 juego : juegos) {
            ganancias += juego.getGanancias();
            //lo reseteo para que la proxima vez que actualice
            //no sume algo que ya sumo
            juego.setGanancias(0);
        }

        servicios.setGanancias(ganancias);
    }

    public boolean hayJuegoActivos() throws RemoteException {
        for (JuegoCasinoV1 juego : juegos) {
            if (juego.tienePartidasActivas()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param codigoJuego juego por el cual se van a filtrar las partidas, o -1
     * si se quieren obtener todas
     */
    public ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(JuegoCasino codigoJuego) throws RemoteException {
        return servicios.getDatosPartidas(codigoJuego.getCodigo());
    }

    protected void guardar(DatosPartidaJuegoCasino p) {
        servicios.guardar(p);
        //TODO avisar que cambio el numero partida
        if (p.getNumeroPartida() > ultimoNumeroPartida) {
            ultimoNumeroPartida = p.getNumeroPartida();
            servicios.guardar(ultimoNumeroPartida);
            for (JuegoCasinoV1 juego : juegos) {
                juego.setUltimoNumeroPartida(++ultimoNumeroPartida);
            }
        }
    }

    protected void modificar(DatosPartidaJuegoCasino p) {
        servicios.modificar(p);
    }

    public void guardar(JugadorV1 j) {
//TODO        
//SsUsuarios.getInstancia().guardar(j);
    }
}
