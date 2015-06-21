/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observableremoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DocenteFI
 */
public class ObservableRemotoV1 extends UnicastRemoteObject
        implements ObservableRemoto {

    private ArrayList<ObservadorRemoto> observadores = new ArrayList();

    public ObservableRemotoV1() throws RemoteException {
    }

    @Override
    public void agregar(ObservadorRemoto obs) throws RemoteException {
        System.out.println("ObservableRemotoV1.agregar ");
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    @Override
    public void quitar(ObservadorRemoto obs) throws RemoteException {
        observadores.remove(obs);
    }

    public void notificar(Object param) {
        System.out.println("ObservableRemotoV1.notificar " + param);
        ArrayList<ObservadorRemoto> tmp = new ArrayList(observadores);
        for (ObservadorRemoto obs : tmp) {
            System.out.println("ObservableRemotoV1.notificar obs");
            new Despachador(obs, this, param, observadores).start();
        }
    }

    public void notificar() {
        notificar(null);
    }

}
