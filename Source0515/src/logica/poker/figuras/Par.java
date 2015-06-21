/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker.figuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import logica.poker.CartaPoker;
import logica.poker.CartaPoker.ComparadorPorNumeroCartaDesc;

/**
 *
 * @author Romi
 */
public class Par implements FiguraPoker {

    private int valorTotalCartas = 0;

    @Override
    public boolean esFigura(List<CartaPoker> cartas) {
        //ordena para que agarre el par de mayor valor primero
        Collections.sort(cartas, new ComparadorPorNumeroCartaDesc());
        //mapea getValorNumero() con la lista de cartas
        HashMap<Integer, List<CartaPoker>> gruposCartasMismoValor = new HashMap<>();
        for (CartaPoker carta : cartas) {
            if (gruposCartasMismoValor.containsKey(carta.getValorNumero())) {
                List<CartaPoker> cartasMismoValor = gruposCartasMismoValor.get(carta.getValorNumero());
                cartasMismoValor.add(carta);
                //gruposCartasMismoValor.put(carta.getValorNumero(), cartasMismoValor);
                if (cartasMismoValor.size() == 2) {
                    setValorTotalCartas(cartasMismoValor);
                    return true;
                }
            } else {
                ArrayList<CartaPoker> nuevaListaCartas = new ArrayList<>();
                nuevaListaCartas.add(carta);
                gruposCartasMismoValor.put(carta.getValorNumero(), nuevaListaCartas);
            }
        }
        valorTotalCartas = 0;
        return false;
    }

    @Override
    public int getValorUnico() {
        return 100;
    }

    @Override
    public String toString() {
        return "Par";
    }

    @Override
    public int getValorCartas() {
        return valorTotalCartas;
    }

    private void setValorTotalCartas(List<CartaPoker> cartasMismoValor) {
        for (CartaPoker c : cartasMismoValor) {
            valorTotalCartas += c.getValorNumero();
        }
    }

}
