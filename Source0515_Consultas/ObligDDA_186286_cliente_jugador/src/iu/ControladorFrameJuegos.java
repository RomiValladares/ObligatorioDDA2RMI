/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import logica.Fachada;
import logica.JuegoCasino;
import logica.Jugador;
import logica.PartidaJuegoCasino;
import observableremoto.ControladorObservador;

/**
 *
 * @author Romi
 */
public class ControladorFrameJuegos extends ControladorObservador {

    private Jugador jugador;
    private Fachada modelo;
    private final String stringConexion = "casino";

    public ControladorFrameJuegos(Jugador jugador) throws RemoteException {
        registrar(stringConexion);
        modelo = (Fachada) getObservable();
        this.jugador = jugador;
    }

    ArrayList<JuegoCasino> getJuegos() {
        try {
            return modelo.getJuegos();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFrameJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    void ingresar(JuegoCasino juego) throws RemoteException, Exception {
        if (juego != null && juego.puedeJugar(jugador)) {
            PartidaJuegoCasino p = juego.jugar(jugador);

            JFrame frameJuego = FabricadorFrameJuegos.getVentanaJuego(juego, jugador, p);
            frameJuego.setVisible(true);
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    
}
