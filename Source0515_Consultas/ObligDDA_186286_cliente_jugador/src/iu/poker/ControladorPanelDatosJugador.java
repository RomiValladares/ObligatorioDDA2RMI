/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.poker;

import logica.ssusuarios.DatosUsuario;
import logica.ssusuarios.Jugador;

/**
 *
 * @author Romi
 */
public interface ControladorPanelDatosJugador {

    public Jugador getJugador();

    public DatosUsuario getDatosJugador();

    public double getSaldoJugador();
}
