/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observableremoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DocenteFI
 */
public class Despachador extends Thread {

    private ObservadorRemoto destino;
    private ObservableRemoto origen;
    private Object param;
    private ArrayList<ObservadorRemoto> lista;

    public Despachador(ObservadorRemoto destino, ObservableRemoto origen, Object param, ArrayList<ObservadorRemoto> lista) {
        this.destino = destino;
        this.origen = origen;
        this.param = param;
        this.lista = lista;
    }

    public void run() {
        try {
            destino.actualizar(origen, param);
        } catch (RemoteException ex) {
            System.out.println("Despachador.run " + ex.getMessage());
            lista.remove(destino);
        }
    }

}
