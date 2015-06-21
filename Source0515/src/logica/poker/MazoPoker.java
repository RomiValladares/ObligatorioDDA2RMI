package logica.poker;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.poker.CartaPoker.PaloCarta;
import logica.poker.figuras.FabricaFigurasPoker;
import logica.poker.figuras.FiguraPoker;

public class MazoPoker {

    //cartas siendo usadas
    private ArrayList<CartaPoker> cartasAsignadas;
    private ArrayList<CartaPoker> cartasNoAsignadas;

    public MazoPoker() {
        resetear();
    }

    private void fillMazo() {
        cartasNoAsignadas = new ArrayList<>();

        agregarCartasPalo(cartasNoAsignadas, PaloCarta.CORAZON);
        agregarCartasPalo(cartasNoAsignadas, PaloCarta.DIAMANTE);
        agregarCartasPalo(cartasNoAsignadas, PaloCarta.TREBOL);
        agregarCartasPalo(cartasNoAsignadas, PaloCarta.PIQUE);
    }
    /*
     * vuelve el mazo a su estado inicial
     */

    public void resetear() {
        fillMazo();
        cartasAsignadas = new ArrayList<>();
    }

    public void barajar() {
        Collections.shuffle(cartasNoAsignadas);
    }

    private void agregarCartasPalo(ArrayList<CartaPoker> cartas, PaloCarta palo) {
        for (int i = 14; i > 1; i--) {
            String numeroCarta = i + "";
            if (i > 10) {
                switch (i) {
                    case 14:
                        numeroCarta = "A";
                        break;
                    case 13:
                        numeroCarta = "K";
                        break;
                    case 12:
                        numeroCarta = "Q";
                        break;
                    case 11:
                        numeroCarta = "J";
                        break;
                }
            }
            cartas.add(new CartaPoker(palo, numeroCarta));
        }
    }

    public ArrayList<CartaPoker> getCartas() {
        return cartasNoAsignadas;
    }

    public List<CartaPoker> getCartas(int cantCartas) {
        //if (cartasNoAsignadas.size() >= cantCartas) {
        List<CartaPoker> rangoCartas = new ArrayList<>(cartasNoAsignadas.subList(0, cantCartas));

       

        cartasAsignadas.addAll(cartasNoAsignadas.subList(0, cantCartas));

     
        cartasNoAsignadas.removeAll(cartasNoAsignadas.subList(0, cantCartas));

        return rangoCartas;
        // } else {
        //    throw new Exception("No hay suficientes cartas para devolver " + cantCartas + ".");
        //}
    }

    /*
     * descarta las cartas que recibe
     * y devuelve nuevas cartas
     */
    public List<CartaPoker> descartar(List<CartaPoker> cartasDescartadas) {
        cartasAsignadas.removeAll(cartasDescartadas);
        cartasNoAsignadas.addAll(cartasDescartadas);
        barajar();
        List<CartaPoker> rangoCartas = new ArrayList<>(cartasNoAsignadas.subList(0, cartasDescartadas.size()));
        cartasAsignadas.addAll(rangoCartas);
        cartasNoAsignadas.removeAll(rangoCartas);

        return rangoCartas;
    }

    public FiguraPoker getFigura(List<CartaPoker> cartas) {
        ArrayList<FiguraPoker> figuras = FabricaFigurasPoker.getFiguras();
        for (FiguraPoker f : figuras) {
            if (f.esFigura(cartas)) {
                return f;
            }
        }

        return null;
    }
}
