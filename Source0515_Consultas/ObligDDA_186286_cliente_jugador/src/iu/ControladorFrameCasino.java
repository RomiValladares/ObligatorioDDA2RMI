/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logica.Fachada;
import observableremoto.ControladorObservador;

/**
 *
 * @author Romi
 */
public class ControladorFrameCasino extends ControladorObservador {

    private Fachada modelo;
    private final String stringConexion = "casino";

    public ControladorFrameCasino() throws RemoteException {
        registrar(stringConexion);
        modelo = (Fachada) getObservable();
    }

    boolean salir() {
        try {
            return !modelo.hayJuegosActivos();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorFrameCasino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    void nuevaInterfazJugador(FrameCasino parent) {
        FrameLogin frameJugador = new FrameLogin(parent, true);
        frameJugador.setVisible(true);
    }

    double getGanancias() throws RemoteException {
        return modelo.getGanancias();
    }

}
