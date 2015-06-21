/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu;

import iu.poker.ControladorPanelDatosJugador;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import logica.Fachada;
import logica.ssjuegos.JuegoCasino;
import logica.ssjuegos.PartidaJuegoCasino;
import logica.ssusuarios.DatosUsuario;
import logica.ssusuarios.Jugador;
import observableremoto.ControladorObservador;

/**
 *
 * @author Romi
 */
public class ControladorFrameJuegos extends ControladorObservador implements ControladorPanelDatosJugador {

    private Jugador jugador;
    private DatosUsuario datos;
    private Fachada modelo;
    private final String stringConexion = "casino";

    public ControladorFrameJuegos(Jugador jugador) throws RemoteException {
        registrar(stringConexion);
        modelo = (Fachada) getObservable();

        setJugador(jugador);
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

    @Override
    public DatosUsuario getDatosJugador() {
        return datos;
    }

    private void setJugador(Jugador jugador) {
        this.jugador = jugador;

        /**
         * pide los datos solo una vez porque son serializables y no deberian
         * cambiar
         */
        try {
            this.datos = jugador.getDatos();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFrameJuegos.class.getName()).log(Level.SEVERE, null, ex);
            this.datos = null;
        }
    }

    @Override
    public double getSaldoJugador() {
        try {
            return jugador.getSaldo();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFrameJuegos.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
