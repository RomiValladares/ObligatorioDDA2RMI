/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.rmi.RemoteException;
import java.util.ArrayList;
import observableremoto.ObservableRemoto;

/**
 *
 * @author Romi
 */
public interface PartidaJuegoCasino extends ObservableRemoto {

    public Jugador getGanadorPartida() throws Exception, RemoteException;

    public double getGanancias() throws RemoteException;

    public ArrayList<Jugador> getJugadoresPartida() throws RemoteException;

    //METODOS DELEGADOS
    public int getNumeroPartida() throws RemoteException;

    public boolean isComenzada() throws RemoteException;

    public boolean isFinalizada() throws RemoteException;

}
