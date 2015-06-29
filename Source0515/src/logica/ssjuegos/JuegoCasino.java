/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos;

import logica.ssusuarios.JugadorV1;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observable;
import logica.ssusuarios.Jugador;
import observableremoto.ObservadorRemoto;

/**
 *
 * @author Romi
 */
public interface JuegoCasino extends Remote {

    /**
     * @return Codigo identificador del juego
     */
    FabricadorJuegosCasino.CodigosJuego getCodigo() throws RemoteException;

    /**
     * @return Texto que se muestra en el listado de juegos del casino
     */
    String getEtiqueta() throws RemoteException;

    /**
     * @return Texto que se muestra en el toString
     */
    String getNombre() throws RemoteException;

    //TODO estos metodos de abajo podrian reutilizarse mas con un coso template
    /*
     * Agrega un jugador a la partida que corresponda
     */
    PartidaJuegoCasino jugar(Jugador nuevoJugador, ObservadorRemoto obs) throws RemoteException, Exception;

    /*
     * validacion previa para ingresar al juego
     */
    boolean puedeJugar(Jugador nuevoJugador) throws RemoteException, Exception;

    boolean tienePartidasActivas() throws RemoteException;

}
