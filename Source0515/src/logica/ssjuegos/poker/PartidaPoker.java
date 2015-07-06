/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import logica.ssjuegos.PartidaJuegoCasino;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import logica.ssusuarios.Jugador;

/**
 *
 * @author Romi
 */
public interface PartidaPoker extends PartidaJuegoCasino {

    //se invoca cada vez que termina una mano y se les pregunta a los jugadores si seguir o no
    public void continuarEnJuego(Jugador jugador, boolean seguir) throws RemoteException, Exception;

    public int getCantidadMaxJugadores() throws RemoteException;

    public List<CartaPoker> getCartasJugador(Jugador j) throws RemoteException, Exception;

    public ManoPoker getManoActual() throws RemoteException;

    public boolean puedeJugar(Jugador nuevoJugador) throws RemoteException, Exception;

    public void retirarse(Jugador jugador) throws RemoteException, Exception;

    // <editor-fold defaultstate="collapsed" desc="METODOS DELEGADOS A MANO ACTUAL">  
    Map.Entry<Jugador, FiguraPoker> getGanadorYFiguraManoActual() throws RemoteException;

    void pasar(Jugador jugador) throws RemoteException;

    void pasarApuesta(Jugador jugador) throws RemoteException;

    /**
     * descarta las cartas que recibe y devuelve cartas nuevas
     */
    List<CartaPoker> descartarse(Jugador j, List<CartaPoker> cartasDescartadas) throws Exception, RemoteException;

    void aceptarApuesta(Jugador jugador) throws Exception, RemoteException;

    void apostar(Jugador jugador, double montoApostado) throws Exception, RemoteException;
    // </editor-fold>

    public boolean jugadorAceptoApuesta(Jugador j) throws RemoteException;

    public int getTimer() throws RemoteException;

    public void ready(Jugador j) throws RemoteException;
}
