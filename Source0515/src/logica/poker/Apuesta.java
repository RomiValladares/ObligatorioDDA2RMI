/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker;

import java.util.ArrayList;
import logica.Jugador;

/**
 *
 * @author Romi
 */
public class Apuesta {

    private final Jugador apostador;
    private final double monto;
    private ArrayList<Jugador> jugadores;

    private int jugadoresDescartados = 0;

    public Apuesta(Jugador apostador, double montoApostado) {
        this.apostador = apostador;
        this.monto = montoApostado;
        this.jugadores = new ArrayList<>();
        this.apostador.restarSaldo(monto);
    }

    public boolean agregar(Jugador jugador) throws Exception {
        if (!jugadores.contains(jugador) && !apostador.equals(jugador)) {
            jugadores.add(jugador);
            jugador.restarSaldo(monto);
            return true;
        } else {
            throw new Exception("El jugador " + jugador.getNombre() + " ya esta en la apuesta.");
        }
    }

    protected ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public String toString() {
        return apostador + " aposto $" + monto;
    }

    public Jugador getJugador() {
        return apostador;
    }

    void descartarse(Jugador j) {
        jugadoresDescartados++;
    }

    protected boolean todosDescartaron() {
        return jugadoresDescartados == jugadores.size() + 1;
    }

    public int getTotalJugadores() {
        return jugadores.size() + 1;
    }

    public double getMontoApostado() {
        return monto;
    }
    
    
}
