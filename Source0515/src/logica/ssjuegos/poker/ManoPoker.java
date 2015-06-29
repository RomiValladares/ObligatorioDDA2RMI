/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import logica.ssusuarios.Jugador;

/**
 *
 * @author Romi
 */
public interface ManoPoker extends Remote {


    Apuesta getApuesta() throws RemoteException;

    /*
     *@return valor de la apuesta maxima, que es igual al saldo minimo
     */
    double getApuestaMaxima() throws RemoteException;

    List<CartaPoker> getCartasJugador(Jugador j) throws Exception, RemoteException;

    FiguraPoker getFiguraRealizada(Jugador j) throws RemoteException;

    double getPozo() throws RemoteException;

    boolean isComenzada() throws RemoteException;

    boolean isFinalizada() throws RemoteException;



}
