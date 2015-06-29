/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos;

import java.util.ArrayList;
import logica.TareaTimer;
import logica.Timer;
import logica.ssusuarios.Jugador;

/**
 *
 * @author Romi
 */
public class PartidaTareaTimer implements TareaTimer {

    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private PartidaJuegoCasinoV1 partida;
    private Timer timer;

    public PartidaTareaTimer(int tiempo, PartidaJuegoCasinoV1 partida) {
        this.partida = partida;
        timer = new Timer(tiempo, this);
    }

    public PartidaTareaTimer(int tiempo, PartidaJuegoCasinoV1 partida, ArrayList<Jugador> jugadores) {
        this.partida = partida;
        timer = new Timer(tiempo, this);
        this.jugadores = jugadores;
    }

    public void comenzar() {
        timer.comenzar();
    }

    public PartidaTareaTimer(PartidaJuegoCasinoV1 partida) {
        this.partida = partida;
        timer = new Timer(this);
    }

    public void remove(Jugador j) {
        jugadores.remove(j);
    }

    public void add(Jugador j) {
        if (!jugadores.contains(j)) {
            jugadores.add(j);
        }
    }

    public void addAll(ArrayList<Jugador> j) {
        jugadores.addAll(j);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public void intervaloTranscurrido(Timer timer, int intervaloTranscurrido, int tiempoTranscurrido) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void timerFinalizado(Timer timer, int tiempoTranscurrido) {
        partida.timeout(jugadores);
    }

    void cancelar() {
        timer.cancelar();
    }
}
