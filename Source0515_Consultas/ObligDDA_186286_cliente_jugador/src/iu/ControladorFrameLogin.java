/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu;

import java.rmi.RemoteException;
import logica.Fachada;
import logica.ssusuarios.Usuario;
import observableremoto.ControladorObservador;

/**
 *
 * @author Romi
 */
public class ControladorFrameLogin extends ControladorObservador {

    private final String stringConexion = "casino";
    private Fachada modelo;

    public ControladorFrameLogin() throws RemoteException {
        registrar(stringConexion);
        modelo = (Fachada) getObservable();
    }

    Usuario ingresar(String usuario, String contrasena) throws Exception {
        return modelo.ingresar(usuario, contrasena);
    }
}
