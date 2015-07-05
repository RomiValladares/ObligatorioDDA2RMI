package logica.ssjuegos.poker;

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
import logica.ssjuegos.JuegoCasinoV1.EventosJuegoCasino;
import logica.ssjuegos.PartidaJuegoCasinoV1;
import logica.ssjuegos.poker.EventoManoPoker.EventosManoPoker;
import logica.ssjuegos.poker.EventoPartidaPoker.EventosPartidaPoker;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import logica.ssusuarios.Jugador;
import observableremoto.ObservadorRemoto;

/*
 * Observable porque FramePoker lo observa
 * Observer porque observa a cada ManoPoker
 */
public class PartidaPokerV1 extends PartidaJuegoCasinoV1 implements Observer, PartidaPoker {

    private ManoPokerV1 manoActual;
    //TODO DEVOLVER A =4
    private int cantidadMaxJugadores = 3;
    private double apuestaBase = 50;
    private boolean primeraMano;
    private int ready = 0;

    @Override
    public void retirarse(Jugador jugador) throws Exception {
        quitarJugador(jugador);

        //aca llega solo si no hubo excepcion
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
    }

    protected void quitarJugador(Jugador jugador) throws Exception {
        //if (getJugadores().containsKey(jugador)) {
        if (manoActual != null && !manoActual.isFinalizada()) {
            if (!isFinalizada()) {
                //agrego ese chequeo para un caso en especial
                //la mano no termino pero si la partida
                //cuando los jugadores se retiran y queda uno que aposto
                manoActual.retirarse(jugador);
            }
        }
        //resta el 10% de lo ganado
        restarGanancias(jugador);
        getJugadores().remove(jugador);
//        } else {
//            throw new Exception("El jugador no esta en esta partida.");
//        }
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
            agregarSaldo(getGanador(), manoActual.getPozo());

            notificar(new EventoPartidaPoker(EventosPartidaPoker.FINALIZO_PARTIDA, "Finalizo la partida. Ganador: " + getGanador(), getGanador()));
        } else {
            notificar(new EventoPartidaPoker(EventosPartidaPoker.FINALIZO_PARTIDA, "Finalizo la partida sin ganador.", null));
        }
    }

    private int jugadoresQueSiguen = 0;

    @Override
    public void update(Observable o, Object arg) {
        //TODO verificar que comentar esto no cree bugs
        //if (o.getClass().equals(ManoPoker.class)) {
        //indica si hay que empezar el timer
        //despues de hacer los chequeos de saldo insuficiente y eso
        boolean comenzarTimer = false;
        debug("update " + arg);
        if (arg instanceof EventoManoPoker && ((EventoManoPoker) arg).getEvento() != null) {
            EventosManoPoker ev = ((EventoManoPoker) arg).getEvento();
            if (ev.equals(EventosManoPoker.COMENZO_MANO) && primeraMano) {
                notificar(EventosPartidaPoker.COMENZO_PARTIDA);
            } else if (ev.equals(EventosManoPoker.DESCARTAR_CARTAS) || ev.equals(EventosManoPoker.FINALIZO_MANO)) {
                comenzarTimer = true;
            }
        }

        if (manoActual.isFinalizada()) {
            cancelarTimer();
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

        if (comenzarTimer) {
            empezarTimer();
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
        try {
            return j.getSaldo() > apuestaBase;
        } catch (RemoteException ex) {
            Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void restarGanancias(Jugador jugador) {
        double diezPorcientoGanancias = (10 * getJugadores().get(jugador)) / 100;
        if (diezPorcientoGanancias > 0) {
            descontarSaldo(jugador, diezPorcientoGanancias);
            setGanancias(getGanancias() + diezPorcientoGanancias);
            notificar(EventosJuegoCasino.NUEVA_GANANCIA);
        }
    }

    public PartidaPokerV1(int numeroPartida, boolean timed, int timeout) throws RemoteException {
        super(timed, timeout);
        setNumeroPartida(numeroPartida);
    }

    public void agregar(Jugador nuevoJugador, ObservadorRemoto obs) throws Exception {
        if (puedeJugar(nuevoJugador)) {
            agregar(obs);
            getJugadores().put(nuevoJugador, 0d);
            notificar();
        }
    }

    @Override
    public void ready(Jugador j) throws RemoteException {
        if (getJugadores().containsKey(j)) {
            ready++;

            if (getJugadores().size() == ready && getJugadores().size() == cantidadMaxJugadores) {
                comenzar();
            }
        }
    }

    @Override
    protected void comenzar() {
        super.comenzar();

        //se asume que de afuera ya se controlo que todos los jugadores tuvieran saldo disponible
        descontarSaldo();

        primeraMano = false;
        if (manoActual == null) {
            primeraMano = true;
        }

        double pozoAcumulado = 0;

        if (manoActual != null) {
            //se acumula el pozo cuando no hubo apuesta o ganador
            if (manoActual.getApuesta() == null || (manoActual.getApuesta() != null && manoActual.getGanadorYFigura() == null)) {
                nuevaMano(manoActual.getPozo());
            }
        }

        nuevaMano(pozoAcumulado);

        //si no ya avisa la nueva mano
    }

    private void descontarSaldo() {
        for (Map.Entry<Jugador, Double> entrySet : getJugadores().entrySet()) {
            descontarSaldo(entrySet.getKey(), apuestaBase);
        }
    }

    private void nuevaMano(double pozoAcumulado) {
        try {
            manoActual = new ManoPokerV1(new ArrayList<>(getJugadores().keySet()), apuestaBase, pozoAcumulado);
            manoActual.addObserver(this);
            manoActual.comenzar();

            empezarTimer();
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
                    String nJugador = null;
                    try {
                        nJugador = j.getEtiqueta();
                    } catch (RemoteException ex) {
                        Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    notificar(new EventoPartidaPoker(EventosPartidaPoker.JUGADOR_SALDO_INSUFICIENTE, nJugador + " ya no tiene saldo suficiente, queda fuera del juego", j));
                }
            }
        }
    }

    @Override
    public int getCantidadMaxJugadores() {
        return cantidadMaxJugadores;
    }

    @Override
    public ManoPoker getManoActual() {
        return manoActual;
    }

    //se invoca cada vez que termina una mano y se les pregunta a los jugadores si seguir o no
    @Override
    public void continuarEnJuego(Jugador jugador, boolean seguir) throws Exception {
        if (!seguir) {
            retirarse(jugador);
        } else {
            setJugadorActivo(jugador);
            Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.INFO, null, "continuarEnJuego " + jugador);
            jugadoresQueSiguen++;
            Logger.getLogger("jugadoresQueSiguen " + jugadoresQueSiguen + " jugadores.size()=" + getJugadores().size());
            if (jugadoresQueSiguen == getJugadores().size()) {
                jugadoresQueSiguen = 0;
                comenzar();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS DELEGADOS MANO ACTUAL">  
    @Override
    public List<CartaPoker> getCartasJugador(Jugador j) throws Exception {
        return manoActual.getCartasJugador(j);
    }

    @Override
    public void pasar(Jugador jugador) {
        setJugadorActivo(jugador);
        manoActual.pasar(jugador);
        try {
            debug(jugador.getEtiqueta() + " pasa");
        } catch (RemoteException ex) {
            Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apostar(Jugador jugador, double montoApostado) throws Exception {
        manoActual.apostar(jugador, montoApostado);
        debug(jugador.getEtiqueta() + " apuesta");
        setJugadorActivo(jugador);
        //lama al modificar de la BD, el restar saldo lo hace la mano
        modificar(jugador);
        empezarTimer();
        setJugadorActivo(jugador);
    }

    @Override
    public void aceptarApuesta(Jugador jugador) throws Exception {
        debug(jugador.getEtiqueta() + " acepta apuesta");
        setJugadorActivo(jugador);
        manoActual.aceptarApuesta(jugador);

        //lama al modificar de la BD, el restar saldo lo hace la mano
        modificar(jugador);
    }

    @Override
    public List<CartaPoker> descartarse(Jugador j, List<CartaPoker> cartasDescartadas) throws Exception {
        List<CartaPoker> descartarse = manoActual.descartarse(j, cartasDescartadas);
        debug(j.getEtiqueta() + " se descarta");
        setJugadorActivo(j);
        return descartarse;
    }

    @Override
    public Map.Entry<Jugador, FiguraPoker> getGanadorYFiguraManoActual() throws RemoteException {
        return manoActual.getGanadorYFigura();
    }

    @Override
    public void pasarApuesta(Jugador jugador) throws RemoteException {
        manoActual.pasarApuesta(jugador);
        debug(jugador.getEtiqueta() + " pasa apuesta");
        setJugadorActivo(jugador);
    }

    // </editor-fold> 
    @Override
    protected void timeout(ArrayList<Jugador> jugadoresTimeout) {
        if (isCronometrada()) {
            debug("termino timer con " + jugadoresTimeout.size() + " jugadores");
            ArrayList<Jugador> jugadoresTimeout1 = new ArrayList<>(jugadoresTimeout);
            for (Jugador j : jugadoresTimeout1) {
                if (this.getJugadores().containsKey(j)) {
                    try {
                        debug(j.getEtiqueta() + " queda fuera por timeout.");
                        quitarJugador(j);
                        notificar(new EventoPartidaPoker(EventosPartidaPoker.TIMEOUT_JUGADOR, j.getEtiqueta() + " queda fuera por timeout.", j));
                    } catch (Exception ex) {
                        //no deberia nunca dar una excepcion
                        Logger.getLogger(PartidaPokerV1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            //aca llega solo si no hubo excepcion
            //esto es para cuando un jugador elija retirarse sin que haya empezado la partida
            if (isComenzada() && getJugadores().size() <= 1 && !isFinalizada()) {
                finalizarPartida();
            }
        }
    }

    @Override
    public boolean jugadorAceptoApuesta(Jugador j) throws RemoteException {
        return manoActual.jugadorAceptoApuesta(j);
    }

    @Override
    protected void empezarTimer() {
        super.empezarTimer(); //To change body of generated methods, choose Tools | Templates.
        debug("comenzo timer");
        notificar(EventosPartidaPoker.COMENZO_TIMER);
    }

    //TODO borrar
    private void debug(String msj) {
        System.out.println("DEBUG PARTIDA " + msj);
    }
}
