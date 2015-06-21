/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker.figuras;

import java.io.Serializable;
import java.util.List;
import logica.ssjuegos.poker.CartaPoker;

/**
 *
 * @author Romi
 */
public interface FiguraPoker extends Serializable {

    /*
     * @return true si la lista cumple con esta figura
     */
    public boolean esFigura(List<CartaPoker> cartas);

    /*
    * @return valor unico de la figura
    */
    public int getValorUnico();
    
    /*
    * El valor se setea si se llamo previamente esFigura()
    * @return valor total de las cartas que forman esta figura
    */
    public int getValorCartas();
}
