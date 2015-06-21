/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.poker;

import iu.ControladorFrameJuegos;
import iu.FrameJuegos;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssusuarios.Jugador;
import logica.ssjuegos.poker.Apuesta;
import logica.ssjuegos.poker.CartaPoker;
import logica.ssjuegos.poker.ManoPoker;
import logica.ssjuegos.poker.PartidaPoker;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import observableremoto.ControladorObservador;

/**
 * Este controlador NO se subscribe a la fachada porque no necesita enterarse de
 * sus updates solo de la partida
 *
 * @author Romi
 */
public class ControladorFramePoker extends ControladorObservador implements ControladorPanelDatosJugador {

    //boolean para controlar que este o no en la partida (porque ya no tiene saldo)
    private boolean estaEnLaPartida = true;
    private PartidaPoker partida;
    private Jugador jugador;

    ControladorFramePoker(PartidaPoker partidaPoker, Jugador jugador) throws RemoteException {
        this.jugador = jugador;
        this.partida = partidaPoker;
        this.partida.agregar(this);
    }

    boolean partidaComenzada() {
        try {
            return partida.isComenzada();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    Map.Entry<Jugador, FiguraPoker> getGanadorManoActual() {
        try {
            return partida.getManoActual().getGanadorYFigura();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    void retirarse() throws Exception {
        try {
            partida.retirarse(getJugador());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ManoPoker getManoActual() {
        try {
            return partida.getManoActual();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Jugador> getJugadoresPartida() {
        try {
            return partida.getJugadoresPartida();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getNumeroPartida() {
        try {
            return partida.getNumeroPartida();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    void salir() {
        try {
            partida.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }

        FrameJuegos frame = new FrameJuegos(getJugador());
        frame.setVisible(true);
    }

    void continuarEnJuego(boolean continuar) throws Exception {
        partida.continuarEnJuego(getJugador(), continuar);
    }

    void pasarApuesta() {
        try {
            partida.getManoActual().pasarApuesta(getJugador());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void aceptarApuesta() throws Exception {
        partida.getManoActual().aceptarApuesta(getJugador());
    }

    void apostar(double apuesta) throws Exception {
        partida.getManoActual().apostar(getJugador(), apuesta);
    }

    void pasar() {
        try {
            partida.getManoActual().pasar(getJugador());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void checkPuedeJugar() {
        //se agrega el chequeo !finalizada para que no se borre como observer
        //en el caso que queden solo dos getJugador()es, ya que los dos tienen que ver el 
        //dialog de ganador en juego
        try {
            if (!puedeSeguirEnJuego() && !partida.isFinalizada()) {
                estaEnLaPartida = false;
                partida.quitar(this);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean puedeSeguirEnJuego() {
        try {
            return partida.puedeJugar(getJugador());
        } catch (Exception ex) {
            Logger.getLogger(FramePoker.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    public List<CartaPoker> getCartasJugador() throws Exception {
        return partida.getCartasJugador(getJugador());
    }

    int getCantidadMaxJugadores() {
        try {
            return partida.getCantidadMaxJugadores();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    int getSizeJugadoresPartida() {
        try {
            return partida.getJugadoresPartida().size();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    double getApuestaMaximaManoActual() {
        try {
            return partida.getManoActual().getApuestaMaxima();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    boolean estaEnLaPartida() {
        return estaEnLaPartida;
    }

    Apuesta getApuestaActual() {
        try {
            return partida.getManoActual().getApuesta();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    double getApuestaMaxima() {
        try {
            return partida.getManoActual().getApuestaMaxima();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    int getTotalJugadoresApuesta() {
        try {
            return partida.getManoActual().getApuesta().getTotalJugadores();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    Apuesta getApuesta() {
        try {
            return partida.getManoActual().getApuesta();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    List<CartaPoker> descartarse(ArrayList<CartaPoker> cartasDescartadas) throws Exception {
        try {
            return partida.getManoActual().descartarse(getJugador(), cartasDescartadas);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    FiguraPoker getFiguraRealizada() throws RemoteException {
        return partida.getManoActual().getFiguraRealizada(getJugador());
    }

    boolean jugadorAposto() {
        try {
            return partida.getManoActual().getApuesta().getJugador().equals(getJugador());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Jugador getGanadorPartida() throws Exception, RemoteException {
        return partida.getGanadorPartida();
    }

    public PartidaPoker getPartida() {
        return partida;
    }

    @Override
    public Jugador getJugador() {
        return jugador;
    }

}
