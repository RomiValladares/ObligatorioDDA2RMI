/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import logica.ssusuarios.Jugador;
import logica.ssjuegos.poker.figuras.FiguraPoker;

/**
 *
 * @author Romi
 */
public interface ManoPoker extends Remote {

    void aceptarApuesta(Jugador jugador) throws Exception, RemoteException;

    // <editor-fold defaultstate="collapsed" desc="METODOS APUESTA">
    void apostar(Jugador jugador, double montoApostado) throws Exception, RemoteException;

    /*
     * descarta las cartas que recibe
     * y devuelve cartas nuevas
     */
    List<CartaPoker> descartarse(Jugador j, List<CartaPoker> cartasDescartadas) throws Exception, RemoteException;

    Apuesta getApuesta() throws RemoteException;

    /*
     *@return valor de la apuesta maxima, que es igual al saldo minimo
     */
    double getApuestaMaxima() throws RemoteException;

    List<CartaPoker> getCartasJugador(Jugador j) throws Exception, RemoteException;

    FiguraPoker getFiguraRealizada(Jugador j) throws RemoteException;

    Map.Entry<Jugador, FiguraPoker> getGanadorYFigura() throws RemoteException;

    double getPozo() throws RemoteException;

    boolean isComenzada() throws RemoteException;

    boolean isFinalizada() throws RemoteException;

    void pasar(Jugador jugador) throws RemoteException;

    void pasarApuesta(Jugador jugador) throws RemoteException;

}
