package logica.poker;

import java.util.Comparator;

public class CartaPoker {

    public enum PaloCarta {

        CORAZON("C"), DIAMANTE("D"), TREBOL("T"), PIQUE("P");

        private final String text;

        private PaloCarta(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private PaloCarta palo;
    private String numero;

    public CartaPoker(PaloCarta palo, String numero) {
        this.palo = palo;
        this.numero = numero;
    }

    public String getEtiqueta() {
        return numero + palo;
    }

    public String getNumero() {
        return numero;
    }

    public int getValorUnico() {
        int valorPalo = 0;
        switch (palo) {
            case CORAZON:
                valorPalo = 40;
                break;
            case DIAMANTE:
                valorPalo = 30;
                break;
            case TREBOL:
                valorPalo = 20;
                break;
            case PIQUE:
                valorPalo = 10;
        }

        return valorPalo + getValorNumero();
    }

    public int getValorNumero() {
        int valorNumero = 0;

        if (!numero.matches("\\d+")) {
            // no es un entero
            switch (numero) {
                case "A":
                    valorNumero = 14;
                    break;
                case "K":
                    valorNumero = 13;
                    break;
                case "Q":
                    valorNumero = 12;
                    break;
                case "J":
                    valorNumero = 11;
                    break;
            }
        } else {
            valorNumero = Integer.parseInt(numero);
        }
        return valorNumero;
    }

    public static class ComparadorPorNumeroCartaDesc implements Comparator<CartaPoker> {

        @Override
        public int compare(CartaPoker a, CartaPoker b) {
            return b.getValorNumero() - a.getValorNumero();
        }
    }

    public static class ComparadorPorNumeroCartaAsc implements Comparator<CartaPoker> {

        //-1 if a < b
        //+1 if a > b
        @Override
        public int compare(CartaPoker a, CartaPoker b) {
            return a.getValorNumero() - b.getValorNumero();
        }

    }

}
