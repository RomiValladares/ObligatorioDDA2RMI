package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.Jugador;
import logica.poker.CartaPoker;
import logica.poker.MazoPoker;
import logica.poker.figuras.FiguraPoker;

//copia de ManoPoker para poder usar los metodos en TestingPoker
public class TestManoPoker {

    private final MazoPoker mazo = new MazoPoker();
    private HashMap<Jugador, List<CartaPoker>> cartasJugadores = new HashMap<>();

    protected void agregarJugadorConCartas(Jugador j, List<CartaPoker> cartas) {
        cartasJugadores.put(j, cartas);
    }

    protected SimpleEntry<Jugador, FiguraPoker> obtenerGanador() {
        List<JugadorYFigura> jugadoresYfiguras = new ArrayList<>();

        Logger.getLogger(getClass().getName()).log(Level.INFO, null,
                "obtenerGanador");
        for (Entry<Jugador, List<CartaPoker>> entrySet : cartasJugadores
                .entrySet()) {
            Jugador jugador = entrySet.getKey();
            Logger.getLogger(getClass().getName()).log(Level.INFO, null,
                    "jugador=" + jugador);
            List<CartaPoker> cartas = entrySet.getValue();

            FiguraPoker figuraRealizada = mazo.getFigura(cartas);
            Logger.getLogger(getClass().getName()).log(Level.INFO, null,
                    "figura=" + figuraRealizada);

            jugadoresYfiguras.add(new JugadorYFigura(jugador, figuraRealizada,
                    cartas));
        }
        Collections.sort(jugadoresYfiguras);

        return new SimpleEntry<>(jugadoresYfiguras.get(0).getJugador(),
                jugadoresYfiguras.get(0).getFigura());
    }

	// clase wrapper para ordenar el array de jugadores-figuras
    // basado en las figuras, para evitar usar un hashmap que no se puede
    // ordenar
    // si hay empate entre las figuras ordena por carta en la mano del jugador
    public static class JugadorYFigura implements Comparable {

        private Jugador jugador;
        private FiguraPoker figura;
        private final List<CartaPoker> cartas;

        public JugadorYFigura(Jugador jugador, FiguraPoker figura,
                List<CartaPoker> cartas) {
            this.jugador = jugador;
            this.figura = figura;
            this.cartas = new ArrayList<>(cartas);
            Collections.sort(this.cartas,
                    new CartaPoker.ComparadorPorNumeroCartaDesc());
        }

        public Jugador getJugador() {
            return jugador;
        }

        public FiguraPoker getFigura() {
            return figura;
        }

        //-1 if a < b
        //+1 if a > b
        @Override
        public int compareTo(Object o) {
            if (o instanceof JugadorYFigura) {
                JugadorYFigura otro = (JugadorYFigura) o;

                System.out.println("COMPARANDO");
                System.out.println("this=" + this + " otro=" + otro);

                // gana el otro porque tiene figura
                if (figura == null && otro.getFigura() != null) {
                    System.out.println("gana el otro porque tiene figura");
                    return 1;
                } else if (figura != null && otro.getFigura() == null) {
                    // gana this porque tiene figura y el otro no
                    System.out.println("gana this porque tiene figura y el otro no");
                    return -1;
                } else if (figura == null && otro.getFigura() == null) {
                    // desempate por quien tenga la carta mas alta en su mano
                    System.out.println("desempate por quien tenga la carta mas alta en su mano");

                    List<CartaPoker> cartasOtro = new ArrayList<CartaPoker>(otro.getCartas());
                    Collections.sort(cartasOtro, new CartaPoker.ComparadorPorNumeroCartaDesc());

                    System.out.println("cartas otro:");
                    for (CartaPoker cartaPoker : cartasOtro) {
                        System.out.println(cartaPoker.getEtiqueta());
                    }
                    System.out.println("cartas this:");
                    for (CartaPoker cartaPoker : cartas) {
                        System.out.println(cartaPoker.getEtiqueta());
                    }

                    System.out.println("this carta mas alta " + cartas.get(0).getEtiqueta());
                    System.out.println("otro carta mas alta " + cartasOtro.get(0).getEtiqueta());

                    System.out.println("return " + (cartas.get(0).getValorUnico()
                            - cartasOtro.get(0).getValorUnico()));

                    return cartas.get(0).getValorUnico() - cartasOtro.get(0).getValorUnico();
                }

                // gana la figura mas alta (poker>escalera>pierna>par)
                if (figura.getValorUnico() != otro.getFigura().getValorUnico()) {
                    System.out.println("gana la figura mas alta");
                    return otro.getFigura().getValorUnico() - figura.getValorUnico();
                }

                // gana la figura con cartas mas altas
                if (figura.getValorCartas() != otro.getFigura()
                        .getValorCartas()) {
                    System.out.println("gana la figura con cartas mas altas");
                    System.out.println("figura.getValorCartas()=" + figura.getValorCartas());
                    System.out.println("otro.getFigura().getValorCartas()=" + otro.getFigura().getValorCartas());
                    return otro.getFigura().getValorCartas() - figura.getValorCartas();
                }

                // tienen la misma figura, compuesta de las mismas cartas.
                // desempate por quien tenga la carta mas alta en su mano
                List<CartaPoker> cartasOtro = new ArrayList<CartaPoker>(otro.getCartas());
                Collections.sort(cartasOtro, new CartaPoker.ComparadorPorNumeroCartaDesc());

                System.out.println("tienen la misma figura, compuesta de las mismas cartas");

                System.out.println("this carta mas alta " + cartas.get(0).getValorUnico());
                System.out.println("otro carta mas alta " + cartasOtro.get(0).getValorUnico());

                return cartas.get(0).getValorUnico()
                        - cartasOtro.get(0).getValorUnico();
            }
            return 0;
        }

        private List<CartaPoker> getCartas() {
            return cartas;
        }

        @Override
        public String toString() {
            return "Jugador=" + jugador + ", figura=" + figura;
        }

    }
}
