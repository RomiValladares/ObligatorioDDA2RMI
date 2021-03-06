/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssjuegos.poker.EventoManoPoker.EventosManoPoker;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import logica.ssusuarios.Jugador;
import observableremoto.ObservableLocal;

/**
 *
 * @author Romi
 */
public class ManoPokerV1 extends UnicastRemoteObject implements ManoPoker {

    private ObservableLocal observable = new ObservableLocal();

    private ArrayList<Jugador> jugadores;
    private final int cantCartasJugador = 5;
    private double pozo = 0;

    @Override
    public double getPozo() {
        return pozo;
    }
    private final double apuestaBase;
    private final MazoPoker mazo;
    private HashMap<Jugador, List<CartaPoker>> cartasJugadores = new HashMap<>();

    private Apuesta apuesta;

    private boolean finalizada = false, comenzada = false;
    private int cantJugadoresQuePasaron = 0;

    private SimpleEntry<Jugador, FiguraPoker> ganadorYFigura;

    //lo consulta la partida al finalizar esta mano.
    //puede ser diferente del pozoActual, cuando el mismo se inicio acumulado
    private double montoApostado;

    protected ManoPokerV1(ArrayList<Jugador> jugadores, double apuestaBase) throws RemoteException {
        //se hace un new para que cuando se elimine un jugador de la mano, no se elimine de la partida
        this.jugadores = new ArrayList<>(jugadores);
        this.apuestaBase = apuestaBase;
        this.mazo = new MazoPoker();
    }

    /*
     * se usa cuando se acumulo de la mano anterior
     */
    protected ManoPokerV1(ArrayList<Jugador> jugadores, double apuestaBase, double pozoInicial) throws RemoteException {
        //se hace un new para que cuando se elimine un jugador de la mano, no se elimine de la partida
        this.jugadores = new ArrayList<>(jugadores);
        this.apuestaBase = apuestaBase;
        this.mazo = new MazoPoker();
        this.pozo = pozoInicial;
    }

    public void addObserver(Observer o) {
        observable.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        observable.deleteObserver(o);
    }

    @Override
    public boolean isFinalizada() {
        return finalizada;
    }

    @Override
    public boolean isComenzada() {
        return comenzada;
    }

    protected void comenzar() {
        comenzada = true;

        acumularPozo();

        mazo.resetear();
        mazo.barajar();

        asignarCartas();

        notificar(new EventoManoPoker(EventosManoPoker.COMENZO_MANO));
    }

    protected void retirarse(Jugador jugador) throws Exception {
        /*if (!finalizada && apuesta != null && (apuesta.getJugador().equals(jugador) || apuesta.getJugadores().contains(jugador))) {
         throw new Exception("El jugador realizo una apuesta, no puede salir del juego.");
         } else*/ if (jugadores.remove(jugador)) {
            /*    throw new Exception("El jugador no esta en esta partida.");
             }*/
            if (apuesta != null) {
                apuesta.quitar(jugador);
            }
            System.out.println("ManoPoker retirarse jugadores.size()=" + jugadores.size());
            checkTerminarMano();
        }
    }

    public void pasar(Jugador jugador) {
        cantJugadoresQuePasaron++;
        String j = "";
        try {
            j = jugador.getEtiqueta();
        } catch (RemoteException ex) {
            Logger.getLogger(ManoPokerV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        notificar(new EventoManoPoker(null, j + " pasa."));
        checkTodosPasaron();

    }

    private void finalizar() {
        finalizada = true;
        notificar(new EventoManoPoker(EventosManoPoker.FINALIZO_MANO));
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS APUESTA">  
    public void apostar(Jugador jugador, double montoApostado) throws Exception {
        if (!jugadores.contains(jugador)) {
            throw new Exception(jugador + " no esta en esta mano.");
        }
        double apuestaMax = getApuestaMaxima();
        if (montoApostado > apuestaMax) {
            throw new Exception("La apuesta no puede ser mayor a " + apuestaMax);
        } else if (apuesta != null) {
            throw new Exception("Ya hay una apuesta para esta mano.");
        } else {
            //la apuesta le resta el saldo a los jugadores
            apuesta = new Apuesta(jugador, montoApostado);
            pozo += montoApostado;
            this.montoApostado += montoApostado;
            notificar(new EventoManoPoker(EventosManoPoker.NUEVA_APUESTA));
        }
    }

    public void aceptarApuesta(Jugador jugador) throws Exception {
        apuesta.agregar(jugador);
        pozo += apuesta.getMontoApostado();
        this.montoApostado += apuesta.getMontoApostado();
        notificar(new EventoManoPoker(null, jugador.getEtiqueta() + " acepta la apuesta."));

        checkTerminarMano();
    }

    public void pasarApuesta(Jugador jugador) {
        if (jugadores.remove(jugador)) {
            cartasJugadores.remove(jugador);

            String nJugador = null;
            try {
                nJugador = jugador.getEtiqueta();
            } catch (RemoteException ex) {
                Logger.getLogger(ManoPokerV1.class.getName()).log(Level.SEVERE, null, ex);
            }
            notificar(new EventoManoPoker(null, nJugador + " queda fuera."));

            checkTerminarMano();
        }
    }

    @Override
    public Apuesta getApuesta() {
        return apuesta;
    }

    /*
     *@return valor de la apuesta maxima, que es igual al saldo minimo
     */
    @Override
    public double getApuestaMaxima() {
        double saldoMinimo = Double.MAX_VALUE;
        for (Jugador j : jugadores) {
            /**
             * TODO este try catch is re what spanglish
             */
            try {
                if (j.getSaldo() < saldoMinimo) {
                    saldoMinimo = j.getSaldo();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ManoPokerV1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return saldoMinimo;
    }

    private void acumularPozo() {
        pozo += jugadores.size() * apuestaBase;
    }

    private void checkTerminarMano() {
        System.out.println("DEBUG MANO ---checkTerminarMano---:");
        System.out.println("DEBUG MANO checkTerminarMano jugadores.size()=" + jugadores.size());
        if (apuesta != null && (jugadores.size() == 1 || apuesta.todosDescartaron())) {
            System.out.println("DEBUG MANO checkTerminarMano apuesta.todosDescartaron()=" + (apuesta != null ? apuesta.todosDescartaron() : "apuesta null"));
            System.out.println("DEBUG MANO checkTerminarMano llama a checkTerminarApuesta");
            checkTerminarApuesta();
        } else if (apuesta != null) {
            System.out.println("DEBUG MANO checkTerminarMano apuesta.todosDescartaron()=" + apuesta.todosDescartaron());
            System.out.println("DEBUG MANO checkTerminarMano llama a checkDescartarCartas");
            checkDescartarCartas();
        } else {
            checkTodosPasaron();
        }
    }

    private void checkDescartarCartas() {
        if (apuesta != null) {
            //lo agrego porque puede ser que el que aposto se haya retirado
            int jugadorQueAposto = apuesta.getJugador() != null ? 1 : 0;
            System.out.println("DEBUG MANO checkTerminarMano apuesta.getJugadores().size()+jugadorQueAposto=" + (apuesta.getJugadores().size() + jugadorQueAposto));
            if (apuesta.getJugadores().size() + jugadorQueAposto == jugadores.size() && !apuesta.isModoDescartar()) {
                apuesta.setModoDescartar(true);
                notificar(new EventoManoPoker(EventosManoPoker.DESCARTAR_CARTAS, (apuesta.getJugadores().size() + 1) + " jugadores en la apuesta."));
            } else if (apuesta.getJugadores().isEmpty()) {
                System.out.println("DEBUG MANO checkDescartarCartas llama a checkTerminarApuesta");
                checkTerminarApuesta();
            }
        }
    }

    private void checkTerminarApuesta() {
        //lo agrego porque puede ser que el que aposto se haya retirado
        int jugadorQueAposto = apuesta.getJugador() != null ? 1 : 0;
        if (jugadores.size() == 1 || (apuesta.getJugadores().size() + jugadorQueAposto == jugadores.size() && apuesta.todosDescartaron())) {
            if (apuesta.getJugadores().isEmpty() && apuesta.getJugador() != null) {
                //significa que solo uno aposto, por lo tanto gana
                ganadorYFigura = new SimpleEntry<>(apuesta.getJugador(), null);
                acreditarPozoGanador(apuesta.getJugador());

                String jugador = "";
                try {
                    jugador = apuesta.getJugador().getEtiqueta();
                } catch (RemoteException ex) {
                    Logger.getLogger(ManoPokerV1.class.getName()).log(Level.SEVERE, null, ex);
                }
                notificar(new EventoManoPoker(EventosManoPoker.GANADOR, jugador + " gana porque nadie mas aposto."));
            } else if (apuesta.getJugadores().isEmpty() && apuesta.getJugador() == null) {
                //un jugador aposto, despues salio de la apuesta, y los demas pasaron
                ganadorYFigura = null;
                notificar(new EventoManoPoker(EventosManoPoker.GANADOR, "El apostador se retiro y nadie aposto. Se acumula el pozo."));
            } else {
                ganadorYFigura = obtenerGanador();
                acreditarPozoGanador(ganadorYFigura.getKey());
                String jugador = "";
                try {
                    jugador = ganadorYFigura.getKey().getEtiqueta();
                } catch (RemoteException ex) {
                    Logger.getLogger(ManoPokerV1.class.getName()).log(Level.SEVERE, null, ex);
                }
                notificar(new EventoManoPoker(EventosManoPoker.GANADOR, jugador + " gano con figura " + ganadorYFigura.getValue()));
            }

            finalizar();
        }
    }
    // </editor-fold> 

    private void notificar() {
        notificar(null);
    }

    private void notificar(Object msg) {
        observable.avisar(msg);
    }

    private void asignarCartas() {
        for (Jugador j : jugadores) {
            List<CartaPoker> cartasJugador = mazo.getCartas(cantCartasJugador);
            cartasJugadores.put(j, cartasJugador);
        }
    }

    @Override
    public List<CartaPoker> getCartasJugador(Jugador j) throws Exception {
        List<CartaPoker> cartasJugador = cartasJugadores.get(j);
        if (cartasJugador == null) {
            throw new Exception("El jugador " + j.getNombre() + " no tiene cartas asignadas.");
        }
        return cartasJugador;
    }

    /**
     * descarta las cartas que recibe y devuelve cartas nuevas
     */
    public List<CartaPoker> descartarse(Jugador j, List<CartaPoker> cartasDescartadas) throws Exception {
        if (apuesta.getJugadores().contains(j) || apuesta.getJugador().equals(j)) {
            apuesta.descartarse(j);
            List<CartaPoker> nuevasCartas = mazo.descartar(cartasDescartadas);
            reasignarCartasAJugador(j, cartasDescartadas, nuevasCartas);

            checkTerminarMano();

            return nuevasCartas;
        } else {
            throw new Exception("El jugador " + j + " no esta en la apuesta actual.");
        }
    }

    @Override
    public FiguraPoker getFiguraRealizada(Jugador j) {
        return mazo.getFigura(cartasJugadores.get(j));
    }

    protected void acreditarPozoGanador(Jugador j) {
        try {
            j.agregarSaldo(pozo);
            /**
             * TODO verificar que comentar esto no cree bugs
             */
//notificar();
        } catch (RemoteException ex) {
            Logger.getLogger(ManoPokerV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Entry<Jugador, FiguraPoker> getGanadorYFigura() {
        return ganadorYFigura;
    }

    private SimpleEntry<Jugador, FiguraPoker> obtenerGanador() {
        List<JugadorYFigura> jugadoresYfiguras = new ArrayList<>();

        for (Entry<Jugador, List<CartaPoker>> entrySet : cartasJugadores.entrySet()) {
            Jugador jugador = entrySet.getKey();

            List<CartaPoker> cartas = entrySet.getValue();

            FiguraPoker figuraRealizada = mazo.getFigura(cartas);

            jugadoresYfiguras.add(new JugadorYFigura(jugador, figuraRealizada, cartas));
        }
        Collections.sort(jugadoresYfiguras);

        return new SimpleEntry<>(jugadoresYfiguras.get(0).getJugador(), jugadoresYfiguras.get(0).getFigura());
    }

    private void reasignarCartasAJugador(Jugador j, List<CartaPoker> cartasDescartadas, List<CartaPoker> nuevasCartas) {
        cartasJugadores.get(j).removeAll(cartasDescartadas);
        cartasJugadores.get(j).addAll(nuevasCartas);
    }

    protected double getMontoApostado() {
        return montoApostado;
    }

    boolean jugadorAceptoApuesta(Jugador j) {
        if (apuesta != null) {
            if (apuesta.getJugadores().contains(j) || (apuesta.getJugador() != null && apuesta.getJugador().equals(j))) {
                return true;
            }
        }
        return false;
    }

    private void checkTodosPasaron() {
        if (cantJugadoresQuePasaron == jugadores.size()) {
            //todos pasaron, finaliza la mano
            finalizar();
        }
    }

    //clase wrapper para ordenar el array de jugadores-figuras
//basado en las figuras, para evitar usar un hashmap que no se puede ordenar
//si hay empate entre las figuras ordena por carta en la mano del jugador
    private class JugadorYFigura implements Comparable {

        private Jugador jugador;
        private FiguraPoker figura;
        private final List<CartaPoker> cartas;

        protected JugadorYFigura(Jugador jugador, FiguraPoker figura, List<CartaPoker> cartas) {
            this.jugador = jugador;
            this.figura = figura;
            this.cartas = new ArrayList<>(cartas);
            Collections.sort(cartas, new CartaPoker.ComparadorPorNumeroCartaDesc());
        }

        public Jugador getJugador() {
            return jugador;
        }

        public FiguraPoker getFigura() {
            return figura;
        }

        //-1 if a < b
        //+1 if a > b
        @Override
        public int compareTo(Object o) {
            if (o instanceof JugadorYFigura) {
                JugadorYFigura otro = (JugadorYFigura) o;

                // gana el otro porque tiene figura
                if (figura == null && otro.getFigura() != null) {
                    return 1;
                } else if (figura != null && otro.getFigura() == null) {
                    // gana this porque tiene figura y el otro no
                    return -1;
                } else if (figura == null && otro.getFigura() == null) {
                    // desempate por quien tenga la carta mas alta en su mano

                    List<CartaPoker> cartasOtro = new ArrayList<>(otro.getCartas());
                    Collections.sort(cartasOtro, new CartaPoker.ComparadorPorNumeroCartaDesc());

                    return cartas.get(0).getValorUnico() - cartasOtro.get(0).getValorUnico();
                }

                // gana la figura mas alta (poker>escalera>pierna>par)
                if (figura.getValorUnico() != otro.getFigura().getValorUnico()) {
                    return otro.getFigura().getValorUnico() - figura.getValorUnico();
                }

                // gana la figura con cartas mas altas
                if (figura.getValorCartas() != otro.getFigura()
                        .getValorCartas()) {
                    return otro.getFigura().getValorCartas() - figura.getValorCartas();
                }

                // tienen la misma figura, compuesta de las mismas cartas.
                // desempate por quien tenga la carta mas alta en su mano
                List<CartaPoker> cartasOtro = new ArrayList<>(otro.getCartas());
                Collections.sort(cartasOtro, new CartaPoker.ComparadorPorNumeroCartaDesc());

                return cartas.get(0).getValorUnico()
                        - cartasOtro.get(0).getValorUnico();
            }
            return 0;
        }

        private List<CartaPoker> getCartas() {
            return cartas;
        }

    }
}
