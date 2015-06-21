/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import logica.FabricadorJuegosCasino.CodigosJuego;

/**
 *
 * @author Romi
 */
public interface ServiciosJuego {

    public ArrayList<JuegoCasinoV1> getJuegos();

    public double getGanancias();

    public void setGanancias(double ganancias);

    public ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(CodigosJuego codigoJuego);

    public void guardar(DatosPartidaJuegoCasino p);

    public void modificar(DatosPartidaJuegoCasino p);
}
