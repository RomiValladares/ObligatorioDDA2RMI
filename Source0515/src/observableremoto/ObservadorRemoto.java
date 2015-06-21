/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package observableremoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author DocenteFI
 */
public interface ObservadorRemoto extends Remote{

    public void actualizar(ObservableRemoto origen, Object param) throws RemoteException;
    
}
