/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import logica.Jugador;
import logica.poker.figuras.FiguraPoker;

/**
 *
 * @author Romi
 */
public class ManoPoker extends Observable {

    private ArrayList<Jugador> jugadores;
    private final int cantCartasJugador = 5;
    private double pozo = 0;

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

    protected ManoPoker(ArrayList<Jugador> jugadores, double apuestaBase) {
        //se hace un new para que cuando se elimine un jugador de la mano, no se elimine de la partida
        this.jugadores = new ArrayList<>(jugadores);
        this.apuestaBase = apuestaBase;
        this.mazo = new MazoPoker();
    }

    /*
     * se usa cuando se acumulo de la mano anterior
     */
    protected ManoPoker(ArrayList<Jugador> jugadores, double apuestaBase, double pozoInicial) {
        //se hace un new para que cuando se elimine un jugador de la mano, no se elimine de la partida
        this.jugadores = new ArrayList<>(jugadores);
        this.apuestaBase = apuestaBase;
        this.mazo = new MazoPoker();
        this.pozo = pozoInicial;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

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
        if (!finalizada && apuesta != null && (apuesta.getJugador().equals(jugador) || apuesta.getJugadores().contains(jugador))) {
            throw new Exception("El jugador realizo una apuesta, no puede salir del juego.");
        } else if (!jugadores.remove(jugador)) {
            throw new Exception("El jugador no esta en esta partida.");
        }
    }

    public void pasar(Jugador jugador) {
        cantJugadoresQuePasaron++;
        notificar(new EventoManoPoker(null, jugador + " pasa."));
        if (cantJugadoresQuePasaron == jugadores.size()) {
            //todos pasaron, finaliza la mano
            finalizar();
        }
    }

    private void finalizar() {
        finalizada = true;
        notificar(new EventoManoPoker(EventosManoPoker.FINALIZO_MANO));
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS APUESTA">  
    public void apostar(Jugador jugador, double montoApostado) throws Exception {
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
        notificar(new EventoManoPoker(null, jugador + " acepta la apuesta."));

        if (apuesta.getJugadores().size() + 1 == jugadores.size()) {
            //notificar(new EventoManoPoker(DESCARTAR_CARTAS, (apuesta.getJugadores().size() + 1) + " jugadores en la apuesta."));
        }
    }

    public void pasarApuesta(Jugador jugador) {
        jugadores.remove(jugador);
        cartasJugadores.remove(jugador);
        notificar(new EventoManoPoker(null, jugador + " queda fuera."));
        checkTerminarApuesta();
    }

    public Apuesta getApuesta() {
        return apuesta;
    }

    /*
     *@return valor de la apuesta maxima, que es igual al saldo minimo
     */
    public double getApuestaMaxima() {
        double saldoMinimo = Double.MAX_VALUE;
        for (Jugador j : jugadores) {
            if (j.getSaldo() < saldoMinimo) {
                saldoMinimo = j.getSaldo();
            }
        }
        return saldoMinimo;
    }

    private void acumularPozo() {
        pozo += jugadores.size() * apuestaBase;
    }

    private void checkTerminarApuesta() {
        if (jugadores.size() == 1 || (apuesta.getJugadores().size() + 1 == jugadores.size() && apuesta.todosDescartaron())) {
            if (apuesta.getJugadores().isEmpty()) {
                //significa que solo uno aposto, por lo tanto gana
                ganadorYFigura = new SimpleEntry<>(apuesta.getJugador(), null);
                acreditarPozoGanador(apuesta.getJugador());
                notificar(new EventoManoPoker(EventosManoPoker.GANADOR, apuesta.getJugador() + " gana porque nadie mas aposto."));
            } else {
                ganadorYFigura = obtenerGanador();
                acreditarPozoGanador(ganadorYFigura.getKey());
                notificar(new EventoManoPoker(EventosManoPoker.GANADOR, ganadorYFigura.getKey() + " gano con figura " + ganadorYFigura.getValue()));
            }

            finalizar();
        }
    }
    // </editor-fold> 

    private void notificar() {
        notificar(null);
    }

    private void notificar(Object msg) {
        setChanged();
        notifyObservers(msg);
    }

    private void asignarCartas() {
        for (Jugador j : jugadores) {
            List<CartaPoker> cartasJugador = mazo.getCartas(cantCartasJugador);
            cartasJugadores.put(j, cartasJugador);
        }
    }

    public List<CartaPoker> getCartasJugador(Jugador j) throws Exception {
        List<CartaPoker> cartasJugador = cartasJugadores.get(j);
        if (cartasJugador == null) {
            throw new Exception("El jugador " + j.getNombre() + " no tiene cartas asignadas.");
        }
        return cartasJugador;
    }

    /*
     * descarta las cartas que recibe
     * y devuelve cartas nuevas
     */
    public List<CartaPoker> descartarse(Jugador j, List<CartaPoker> cartasDescartadas) throws Exception {
        if (apuesta.getJugadores().contains(j) || apuesta.getJugador().equals(j)) {
            apuesta.descartarse(j);
            List<CartaPoker> nuevasCartas = mazo.descartar(cartasDescartadas);
            reasignarCartasAJugador(j, cartasDescartadas, nuevasCartas);

            checkTerminarApuesta();

            return nuevasCartas;
        } else {
            throw new Exception("El jugador " + j + " no esta en la apuesta actual.");
        }
    }

    public FiguraPoker getFiguraRealizada(Jugador j) {
        return mazo.getFigura(cartasJugadores.get(j));
    }

    protected void acreditarPozoGanador(Jugador j) {
        j.agregarSaldo(pozo);
        notificar();
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
    

    public enum EventosManoPoker {

        COMENZO_MANO,
        NUEVA_APUESTA, FINALIZO_MANO, GANADOR, DESCARTAR_CARTAS;
    }

    public class EventoManoPoker {

        private EventosManoPoker evento;
        private String descripcion;

        protected EventoManoPoker(EventosManoPoker evento) {
            this.evento = evento;
        }

        protected EventoManoPoker(EventosManoPoker evento, String descripcion) {
            this.evento = evento;
            this.descripcion = descripcion;
        }

        public EventosManoPoker getEvento() {
            return evento;
        }

        public String getDescripcion() {
            return descripcion != null ? descripcion : evento.toString();
        }

        @Override
        public String toString() {
            return "EventoManoPoker{" + "evento=" + evento + ", descripcion=" + descripcion + '}';
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
