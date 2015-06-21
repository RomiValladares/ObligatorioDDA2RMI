/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker.figuras;

import java.util.Collections;
import java.util.List;
import logica.poker.CartaPoker;
import logica.poker.CartaPoker.ComparadorPorNumeroCartaAsc;

/**
 *
 * @author Romi
 */
public class Escalera implements FiguraPoker {

    private int valorTotalCartas = 0;

    @Override
    public boolean esFigura(List<CartaPoker> cartas) {
        if (cartas != null && cartas.size() >= 2) {
            Collections.sort(cartas, new ComparadorPorNumeroCartaAsc());

            for (int i = 0; i < cartas.size() - 1; i++) {
                CartaPoker cartaActual = cartas.get(i);
                CartaPoker cartaProxima = cartas.get(i + 1);
                if (!(cartaActual.getValorNumero() == cartaProxima.getValorNumero() - 1)) {
                    return false;
                } else {
                    valorTotalCartas += cartaActual.getValorUnico();
                }
            }
            valorTotalCartas += cartas.get(cartas.size() - 1).getValorUnico();
            return true;
        }
        return false;
    }

    @Override
    public int getValorUnico() {
        return 300;
    }

    @Override
    public String toString() {
        return "Pierna";
    }

    @Override
    public int getValorCartas() {
        return valorTotalCartas;
    }
}
