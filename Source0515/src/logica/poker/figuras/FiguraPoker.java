/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker.figuras;

import java.util.List;
import logica.poker.CartaPoker;

/**
 *
 * @author Romi
 */
public interface FiguraPoker {

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
