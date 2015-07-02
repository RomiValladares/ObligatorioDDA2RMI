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
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssjuegos.JuegoCasino;
import logica.ssjuegos.poker.Apuesta;
import logica.ssjuegos.poker.CartaPoker;
import logica.ssjuegos.poker.ManoPoker;
import logica.ssjuegos.poker.PartidaPoker;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import logica.ssusuarios.DatosUsuario;
import logica.ssusuarios.Jugador;
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
    private DatosUsuario datos;
    private boolean avisoReady = false;

    ControladorFramePoker(JuegoCasino juego, Jugador jugador, Observer aThis) throws RemoteException, Exception {
        setJugador(jugador);
        if (aThis != null) {
            addObserver(aThis);
        }
        //la partida la recibe por callback en setPartida (abajo)
        System.out.println("ControladorFramePoker.JUGAR");
        this.partida = (PartidaPoker) juego.jugar(jugador, this);
    }

    boolean partidaComenzada() {
        try {
            System.out.println("ControladorFramePoker.partidaComenzada");
            return partida.isComenzada();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    Map.Entry<Jugador, FiguraPoker> getGanadorManoActual() {
        try {
            return partida.getGanadorYFiguraManoActual();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    void retirarse() throws Exception {
        try {
            partida.retirarse(getJugador());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getNumeroPartida() {
        try {
            return partida.getNumeroPartida();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
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
            partida.pasarApuesta(getJugador());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void aceptarApuesta() throws Exception {
        System.out.println("Controlador Frame Poker aceptarApuesta");
        partida.aceptarApuesta(getJugador());
    }

    void apostar(double apuesta) throws Exception {
        partida.apostar(getJugador(), apuesta);
    }

    void pasar() {
        try {
            partida.pasar(getJugador());
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

    /**
     *
     * @return -1 si no es cronometrada, x si lo es
     */
    protected int getPartidaTimer() {
        try {
            //porque son milisegundos
            return partida.getTimer() / 1000;
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
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
        } catch (Exception e) {
            //TODO borrar es solo para debug
        }
        return -1;
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
            return partida.descartarse(getJugador(), cartasDescartadas);
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
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    boolean jugadorAceptoApuesta() {
        try {
            return partida.jugadorAceptoApuesta(jugador);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void setPartida(PartidaPoker p) throws RemoteException {
        System.out.println("ControladorFramePoker.setPartida");
        this.partida = p;
    }

    void ready() {
        try {
            if (!avisoReady) {
                System.out.println("ready frame");
                partida.ready(getJugador());
                avisoReady = true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean partidaCronometrada() {
        try {
            return partida.isCronometrada();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFramePoker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
