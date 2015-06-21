/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.consultas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import logica.ssjuegos.DatosPartidaJuegoCasino;
import logica.Fachada;
import logica.ssjuegos.JuegoCasino;
import observableremoto.ControladorObservador;

/**
 *
 * @author Romi
 */
public class ControladorFrameConsultas extends ControladorObservador {

    private Fachada modelo;
    private final String stringConexion = "casino";

    public ControladorFrameConsultas() throws RemoteException {
        registrar(stringConexion);
        modelo = (Fachada) getObservable();
    }

    double getGanancias() throws RemoteException {
        return modelo.getGanancias();
    }

    ArrayList<JuegoCasino> getJuegos() throws RemoteException {
        return modelo.getJuegos();
    }

    ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(JuegoCasino juegoCasino) throws RemoteException {
        return modelo.getDatosPartidas(juegoCasino);
    }

}
