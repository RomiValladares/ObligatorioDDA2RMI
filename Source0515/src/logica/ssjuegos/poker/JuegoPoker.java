package logica.ssjuegos.poker;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssjuegos.FabricadorJuegosCasino.CodigosJuego;
import logica.ssjuegos.JuegoCasinoV1;
import logica.ssjuegos.PartidaJuegoCasino;
import static logica.ssjuegos.poker.EventoPartidaPoker.EventosPartidaPoker.COMENZO_PARTIDA;
import static logica.ssjuegos.poker.EventoPartidaPoker.EventosPartidaPoker.FINALIZO_PARTIDA;
import logica.ssusuarios.Jugador;
import observableremoto.ObservableRemoto;
import observableremoto.ObservadorRemoto;

/**
 * ObservadorRemoto porque observa a la partida, que es ObservableRemoto
 *
 * @author Romi
 */
public class JuegoPoker extends JuegoCasinoV1 implements ObservadorRemoto {

    private static final CodigosJuego codigo = CodigosJuego.POKER;
    private final String etiqueta = "POKER";

    public JuegoPoker(boolean timed, int timeout) throws RemoteException {
        super(timed, timeout);
    }

    /*
     * @return POKER, partida x, z/y jugadores.
     * 
     * @see logica.JuegoCasino#getEtiqueta()
     */
    @Override
    public String getEtiqueta() {
        String partida = "";
        if (getProximaPartida() != null) {
            partida = ", partida: " + getProximaPartida().getNumeroPartida()
                    + ", " + getProximaPartida().getJugadoresPartida().size() + "/"
                    + getProximaPartida().getCantidadMaxJugadores() + " jugadores";
        }
        return etiqueta + partida;
    }

    @Override
    public PartidaJuegoCasino jugar(Jugador nuevoJugador, ObservadorRemoto obs) throws Exception {
        // TODO Auto-generated method stub

        getProximaPartida().agregar(nuevoJugador, obs);

        if (getProximaPartida() == null || getProximaPartida().isComenzada()) {
            crearPartida();
            return getPartidas().get(getPartidas().size() - 1);
        }

        return getProximaPartida();
    }

//    @Override
//    public void update(Observable observable, Object args) {
//        if (args instanceof EventosJuegoCasino) {
//            EventosJuegoCasino evento = (EventosJuegoCasino) args;
//            if (evento == EventosJuegoCasino.NUEVA_GANANCIA) {
//                actualizarGanancias();
//                notificar(evento);
//            }
//        } else if (args instanceof ManoPoker.EventoManoPoker) {
//            EventoManoPoker evento = (EventoManoPoker) args;
//            if (evento.getEvento() != null && (evento.getEvento().equals(ManoPoker.EventosManoPoker.COMENZO_MANO) && getPartidas().contains(getProximaPartida()))) {
//                crearPartida();
//            }
//        } else {
//            notificar(null);
//        }
//    }
    private void actualizarGanancias() {
        double ganancias = 0;
        for (PartidaPokerV1 partida : getPartidas()) {
            ganancias += partida.getGanancias();
        }
        setGanancias(ganancias);
    }

    @Override
    public CodigosJuego getCodigo() {
        // TODO Auto-generated method stub
        return codigo;
    }

    @Override
    public boolean puedeJugar(Jugador nuevoJugador) throws Exception {
        return getProximaPartida().puedeJugar(nuevoJugador);
    }

    @Override
    public boolean tienePartidasActivas() {
        for (PartidaPokerV1 partida : getPartidas()) {
            if (partida.isComenzada()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNombre() {
        return "Poker";
    }

    @Override
    protected void crearPartida() {
        try {
            if (getProximaPartida() != null) {
                getPartidas().add(getProximaPartida());
            }
            int ultimoNPartida = getUltimoNumeroPartida();
            setProximaPartida(new PartidaPokerV1(ultimoNPartida++, isTimed(), getTimeout()));
            setUltimoNumeroPartida(ultimoNPartida);
            getProximaPartida().agregar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(JuegoPoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(ObservableRemoto origen, Object args) throws RemoteException {
        //System.out.println("JuegoPoker update args=" + args);
        if (args instanceof EventosJuegoCasino) {
            EventosJuegoCasino evento = (EventosJuegoCasino) args;
            if (evento == EventosJuegoCasino.NUEVA_GANANCIA) {
                actualizarGanancias();
                notificar(evento);
            }
        } else if (args instanceof EventoPartidaPoker.EventosPartidaPoker) {
            EventoPartidaPoker.EventosPartidaPoker evento = (EventoPartidaPoker.EventosPartidaPoker) args;
            if (evento.equals(COMENZO_PARTIDA)) {
                notificar(args);
                crearPartida();
            } else if (evento.equals(FINALIZO_PARTIDA)) {
                notificar(args);
            }
        } else if (!(args instanceof EventoManoPoker)) {
            notificar(args);
        }
    }

}
