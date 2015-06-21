/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker.figuras;

import java.util.ArrayList;

/**
 *
 * @author Romi
 */
public class FabricaFigurasPoker {

    /**
     *
     * @return devuelve todas las figuras de poker disponibles
     * en orden descendente
     */
    public static ArrayList<FiguraPoker> getFiguras() {
        ArrayList<FiguraPoker> figuras = new ArrayList<>();

        figuras.add(new Poker());
        figuras.add(new Escalera());
        figuras.add(new Pierna());
        figuras.add(new Par());

        return figuras;
    }
}
