/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observableremoto;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DocenteFI
 */
public class ControladorObservador extends UnicastRemoteObject
        implements ObservadorRemoto {

    private ObservableRemoto observable;
    private ObservableLocal observableLocal = new ObservableLocal();

    public ControladorObservador() throws RemoteException {
    }

    public void registrar(String url) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            observable = (ObservableRemoto) Naming.lookup(url);
            observable.agregar(this);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public ObservableRemoto getObservable() {
        return observable;
    }

    public void desregistrar() {
        try {
            observable.quitar(this);
        } catch (RemoteException ex) {

        }
    }

    @Override
    public void actualizar(ObservableRemoto origen, Object param) throws RemoteException {
        System.out.println("ControladorObservador.actualizar " + param);
        observableLocal.avisar(param);
    }

    public void addObserver(Observer o) {
        observableLocal.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        observableLocal.deleteObserver(o);
    }

}
