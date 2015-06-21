/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker;

import java.rmi.RemoteException;
import java.util.List;
import logica.Jugador;
import logica.PartidaJuegoCasino;
import observableremoto.ObservableRemoto;

/**
 *
 * @author Romi
 */
public interface PartidaPoker extends PartidaJuegoCasino {

    public void agregar(Jugador nuevoJugador) throws RemoteException, Exception;

    //se invoca cada vez que termina una mano y se les pregunta a los jugadores si seguir o no
    public void continuarEnJuego(Jugador jugador, boolean seguir) throws RemoteException, Exception;

    public int getCantidadMaxJugadores() throws RemoteException;

    public List<CartaPoker> getCartasJugador(Jugador j) throws RemoteException, Exception;

    public ManoPoker getManoActual() throws RemoteException;

    public boolean puedeJugar(Jugador nuevoJugador) throws RemoteException, Exception;

    public void retirarse(Jugador jugador) throws RemoteException, Exception;
}
