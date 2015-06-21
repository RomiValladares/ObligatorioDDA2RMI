package logica.poker;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.JuegoCasinoV1.EventosJuegoCasino;
import logica.Jugador;
import logica.PartidaJuegoCasinoV1;
import logica.poker.EventoPartidaPoker.EventosPartidaPoker;

/*
 * Observable porque FramePoker lo observa
 * Observer porque observa a cada ManoPoker
 */
public class PartidaPokerV1 extends PartidaJuegoCasinoV1 implements Observer, PartidaPoker {

    private ManoPokerV1 manoActual;
    private int cantidadMaxJugadores = 4;
    private double apuestaBase = 50;

    @Override
    public void retirarse(Jugador jugador) throws Exception {
        if (getJugadores().containsKey(jugador)) {
            if (manoActual != null && !manoActual.isFinalizada()) {
                manoActual.retirarse(jugador);
            }
            //resta el 10% de lo ganado
            restarGanancias(jugador);
            getJugadores().remove(jugador);

            //esto es para cuando un jugador elija retirarse sin que haya empezado la partida
            if (isComenzada() && getJugadores().size() == 1 && !isFinalizada()) {
                finalizarPartida();
            } else {
                notificar(new EventoPartidaPoker(EventosPartidaPoker.SALIDA_JUGADOR, jugador + " sale del juego.", jugador));
            }
            if (jugadoresQueSiguen == getJugadores().size() && isComenzada()) {
                jugadoresQueSiguen = 0;
                comenzar();
            }
        } else {
            throw new Exception("El jugador no esta en esta partida.");
        }
    }

    @Override
    protected void obtenerGanador() {
        if (getJugadores().size() >= 1) {
            //puede pasar que haya terminado la mano, y el ganador salga
            if (manoActual.getGanadorYFigura() == null || !getJugadores().containsKey(manoActual.getGanadorYFigura().getKey())) {
                setGanador((Jugador) getJugadores().keySet().toArray()[0]);
            } else {
                setGanador(manoActual.getGanadorYFigura().getKey());
            }
            getGanador().agregarSaldo(manoActual.getPozo());
            notificar(new EventoPartidaPoker(EventosPartidaPoker.FINALIZO_PARTIDA, "Finalizo la partida. Ganador: " + getGanador(), getGanador()));
        } else {
            notificar(new EventoPartidaPoker(EventosPartidaPoker.FINALIZO_PARTIDA, "Finalizo la partida sin ganador.", null));
        }
    }

    private int jugadoresQueSiguen = 0;

    //se invoca cada vez que termina una mano y se les pregunta a los jugadores si seguir o no
    @Override
    public void continuarEnJuego(Jugador jugador, boolean seguir) throws Exception {
        if (!seguir) {
            retirarse(jugador);
        } else {
            Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.INFO, null, "continuarEnJuego " + jugador);
            jugadoresQueSiguen++;
            Logger.getLogger("jugadoresQueSiguen " + jugadoresQueSiguen + " jugadores.size()=" + getJugadores().size());
            if (jugadoresQueSiguen == getJugadores().size()) {
                jugadoresQueSiguen = 0;
                comenzar();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO verificar que comentar esto no cree bugs
        //if (o.getClass().equals(ManoPoker.class)) {
        if (manoActual.isFinalizada()) {
            setTotalApostado(getTotalApostado() + manoActual.getMontoApostado());
            if (manoActual.getGanadorYFigura() != null) {
                //actualiza el saldo del jugador
                getJugadores().put(manoActual.getGanadorYFigura().getKey(), manoActual.getPozo());
            }
            checkJugadoresSaldoInsuficiente();
        }
            //si despues de chequear el saldo de los jugadores todavia no termino la partida
        //deja que la mano actual notifique
        if (!isFinalizada()) {
            notificar(arg);
        }

        modificar();
        //}
    }

    @Override
    public boolean puedeJugar(Jugador nuevoJugador) throws Exception {
        if (!checkSaldoSuficiente(nuevoJugador)) {
            // TODO hardcoded
            throw new Exception("El saldo del jugador "
                    + nuevoJugador.getNombre()
                    + " es menor a la apuesta base (" + apuestaBase + ")");
        } else if (getJugadores().containsKey(nuevoJugador)) {
            return true;
        } else if (getJugadores().size() == cantidadMaxJugadores) {
            // TODO hardcoded
            throw new Exception("La partida " + getNumeroPartida() + " ya tiene "
                    + cantidadMaxJugadores + " jugadores.");
        } else {
            return true;
        }

    }

    private boolean checkSaldoSuficiente(Jugador j) {
        return j.getSaldo() > apuestaBase;
    }

    private void restarGanancias(Jugador jugador) {
        double diezPorcientoGanancias = (10 * getJugadores().get(jugador)) / 100;
        jugador.restarSaldo(diezPorcientoGanancias);
        setGanancias(getGanancias() + diezPorcientoGanancias);
        notificar(EventosJuegoCasino.NUEVA_GANANCIA);
    }

    public PartidaPokerV1(int numeroPartida) throws RemoteException {
        setNumeroPartida(numeroPartida);
    }

    @Override
    public void agregar(Jugador nuevoJugador) throws Exception {
        if (puedeJugar(nuevoJugador)) {
            getJugadores().put(nuevoJugador, 0d);

            if (getJugadores().size() == cantidadMaxJugadores) {
                comenzar();
            } else {
                notificar();
            }
        }
    }

    @Override
    protected void comenzar() {
        super.comenzar();

        //se asume que de afuera ya se controlo que todos los jugadores tuvieran saldo disponible
        descontarSaldo();

        boolean primeraMano = false;
        if (manoActual == null) {
            primeraMano = true;
        }

        if (manoActual != null && manoActual.getApuesta() == null) {
            nuevaMano(manoActual.getPozo());
        } else {
            nuevaMano(0);
        }

        if (primeraMano) {
            notificar(EventosPartidaPoker.COMENZO_PARTIDA);
        }
        //si no ya avisa la nueva mano
    }

    private void descontarSaldo() {
        for (Map.Entry<Jugador, Double> entrySet : getJugadores().entrySet()) {
            entrySet.getKey().restarSaldo(apuestaBase);
        }
    }

    private void nuevaMano(double pozoAcumulado) {
        try {
            manoActual = new ManoPokerV1(new ArrayList<>(getJugadores().keySet()), apuestaBase, pozoAcumulado);
            manoActual.addObserver(this);
            manoActual.comenzar();
        } catch (RemoteException ex) {
            Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkJugadoresSaldoInsuficiente() {
        ArrayList<Jugador> jugadoresSaldoInsuficiente = new ArrayList<>();
        for (Iterator<Map.Entry<Jugador, Double>> iterator = getJugadores().entrySet().iterator(); iterator.hasNext();) {
            Jugador j = iterator.next().getKey();
            if (!checkSaldoSuficiente(j)) {
                jugadoresSaldoInsuficiente.add(j);
                //iterator.remove();
                //notificar(new EventoPartidaPoker(EventosPartidaPoker.JUGADOR_SALDO_INSUFICIENTE, j + " ya no tiene saldo suficiente, queda fuera del juego", j));
            }
        }

        if (jugadoresSaldoInsuficiente.size() == getJugadores().size()) {
            //perdieron todos?
            finalizarPartida();
        } else if (jugadoresSaldoInsuficiente.size() == getJugadores().size() - 1) {
            //solo uno tiene saldo suficiente asi que gana
            finalizarPartida();
        } else {
            //quedan al menos dos personas en el juego, no gano nadie todavia
            for (Iterator<Map.Entry<Jugador, Double>> iterator = getJugadores().entrySet().iterator(); iterator.hasNext();) {
                Jugador j = iterator.next().getKey();
                if (jugadoresSaldoInsuficiente.contains(j)) {
                    iterator.remove();
                    notificar(new EventoPartidaPoker(EventosPartidaPoker.JUGADOR_SALDO_INSUFICIENTE, j + " ya no tiene saldo suficiente, queda fuera del juego", j));
                }
            }
        }
    }

    @Override
    public int getCantidadMaxJugadores() {
        return cantidadMaxJugadores;
    }

    @Override
    public List<CartaPoker> getCartasJugador(Jugador j) throws Exception {
        return manoActual.getCartasJugador(j);
    }

    @Override
    public ManoPoker getManoActual() {
        return manoActual;
    }
}
