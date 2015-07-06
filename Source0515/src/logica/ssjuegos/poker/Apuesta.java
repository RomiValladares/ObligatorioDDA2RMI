/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssusuarios.Jugador;

/**
 *
 * @author Romi
 */
public class Apuesta implements Serializable {

    private Jugador apostador;
    private final double monto;
    private ArrayList<Jugador> jugadores;

    private int jugadoresDescartados = 0;
    //indica si ya se comenzo a descartar
    private boolean modoDescartar;

    public Apuesta(Jugador apostador, double montoApostado) {
        this.apostador = apostador;
        this.monto = montoApostado;
        this.jugadores = new ArrayList<>();
        try {
            this.apostador.restarSaldo(monto);
        } catch (RemoteException ex) {
            Logger.getLogger(Apuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        return getEtiqueta();
    }

    public String getEtiqueta() {
        try {
            return (apostador != null ? apostador.getNombre() : "Jugador que se retiro ") + (" aposto $" + monto);
        } catch (RemoteException ex) {
            Logger.getLogger(Apuesta.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public Jugador getJugador() {
        return apostador;
    }

    void descartarse(Jugador j) {
        jugadoresDescartados++;
    }

    protected boolean todosDescartaron() {
        return jugadoresDescartados == jugadores.size() + (apostador != null ? 1 : 0);
    }

    public int getTotalJugadores() {
        return jugadores.size() + 1;
    }

    public double getMontoApostado() {
        return monto;
    }

    void quitar(Jugador jugador) {
        if (apostador != null && apostador.equals(jugador)) {
            apostador = null;
        } else {
            jugadores.remove(jugador);
        }
    }

    void setModoDescartar(boolean b) {
        this.modoDescartar = b;
    }

    public boolean isModoDescartar() {
        return modoDescartar;
    }

}
