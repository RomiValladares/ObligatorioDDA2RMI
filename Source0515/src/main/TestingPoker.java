/**
 *
 */
package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import junit.framework.TestCase;
import logica.poker.CartaPoker;
import logica.FachadaV1;
import logica.JuegoCasinoV1;
import logica.Jugador;
import logica.poker.CartaPoker.ComparadorPorNumeroCartaDesc;
import logica.poker.MazoPoker;
import logica.poker.figuras.Escalera;
import logica.poker.figuras.FiguraPoker;
import logica.poker.figuras.Par;
import logica.poker.figuras.Pierna;
import logica.poker.figuras.Poker;
import main.TestManoPoker.JugadorYFigura;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Romi
 * 
 */
public class TestingPoker {

	@Test
	public void testearMazo() {
		MazoPoker mazo = new MazoPoker();
		assertEquals(52, mazo.getCartas().size());

		String cartasSinBarajar = "";
		for (CartaPoker c : mazo.getCartas()) {
			// System.out.println(c.getEtiqueta());
			cartasSinBarajar += c.getEtiqueta();
		}

		mazo.barajar();

		String cartasBarajadas = "";
		for (CartaPoker c : mazo.getCartas()) {
			cartasBarajadas += c.getEtiqueta();
		}

		assertNotEquals(cartasSinBarajar, cartasBarajadas);
	}

	@Test
	public void testearOrdenamientoCartas() {
		MazoPoker mazo = new MazoPoker();
		assertEquals(52, mazo.getCartas().size());

		Collections.sort(mazo.getCartas(), new ComparadorPorNumeroCartaDesc());
		for (CartaPoker c : mazo.getCartas()) {
			System.out.println(c.getValorNumero());
		}
	}

	@Test
	public void testearFiguras() {
		MazoPoker mazo = new MazoPoker();

		List<CartaPoker> manoConPoker = new ArrayList<>();
		manoConPoker.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "A"));
		manoConPoker.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "A"));
		manoConPoker.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "Q"));
		manoConPoker.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "A"));
		manoConPoker.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "A"));

		assertEquals(mazo.getFigura(manoConPoker).getClass(), Poker.class);

		List<CartaPoker> manoConEscalera = new ArrayList<>();
		manoConEscalera.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "Q"));
		manoConEscalera.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "K"));
		manoConEscalera.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "A"));
		manoConEscalera.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "J"));
		manoConEscalera.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "10"));

		assertEquals(mazo.getFigura(manoConEscalera).getClass(), Escalera.class);

		List<CartaPoker> manoConPierna = new ArrayList<>();
		manoConPierna.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "9"));
		manoConPierna.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "K"));
		manoConPierna.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "9"));
		manoConPierna.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "J"));
		manoConPierna.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "9"));

		assertEquals(mazo.getFigura(manoConPierna).getClass(), Pierna.class);

		List<CartaPoker> manoConPar = new ArrayList<>();
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "9"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "K"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "9"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "J"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "10"));

		assertEquals(mazo.getFigura(manoConPar).getClass(), Par.class);
	}

	/*
	 * poker>escalera>pierna>par si son figuras iguales, gana la que tenga las
	 * cartas mas altas si tienen igual valor o son las dos null, gana el que
	 * tenga la carta mas alta en su mano
	 */
	@Test
	public void testearOrdenamientoFiguras() {
		//
		Jugador jugador1 = new Jugador("j1");

		List<CartaPoker> manoConPoker1 = new ArrayList<>();
		manoConPoker1.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "A"));
		manoConPoker1.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "A"));
		manoConPoker1.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "Q"));
		manoConPoker1.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "A"));
		manoConPoker1.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "A"));

		FiguraPoker figuraManoConPoker = new MazoPoker()
				.getFigura(manoConPoker1);

		JugadorYFigura j1 = new JugadorYFigura(jugador1, figuraManoConPoker,
				manoConPoker1);
		//
		Jugador jugador2 = new Jugador("j2");

		List<CartaPoker> manoConPoker2 = new ArrayList<>();
		manoConPoker2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "10"));
		manoConPoker2.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "10"));
		manoConPoker2.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "Q"));
		manoConPoker2.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "10"));
		manoConPoker2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "10"));

		FiguraPoker figuraManoConPoker2 = new MazoPoker()
				.getFigura(manoConPoker2);
		JugadorYFigura j2 = new JugadorYFigura(jugador2, figuraManoConPoker2,
				manoConPoker2);
		//
		Jugador jugador3 = new Jugador("j3");

		List<CartaPoker> manoConPar = new ArrayList<>();
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "7"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "K"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "7"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "J"));
		manoConPar.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "10"));

		FiguraPoker figuraManoConPar = new MazoPoker().getFigura(manoConPar);

		JugadorYFigura j3 = new JugadorYFigura(jugador3, figuraManoConPar,
				manoConPar);
		//
		Jugador jugador4 = new Jugador("j4");

		List<CartaPoker> manoConPar2 = new ArrayList<>();
		manoConPar2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "9"));
		manoConPar2.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "K"));
		manoConPar2.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "9"));
		manoConPar2.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "J"));
		manoConPar2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "10"));

		FiguraPoker figuraManoConPar2 = new MazoPoker().getFigura(manoConPar2);
		JugadorYFigura j4 = new JugadorYFigura(jugador4, figuraManoConPar2,
				manoConPar2);
		//
		Jugador jugador5 = new Jugador("j5");

		List<CartaPoker> manoSinFigura = new ArrayList<>();
		manoSinFigura.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "3"));
		manoSinFigura.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "2"));
		manoSinFigura.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "4"));
		manoSinFigura.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "10"));
		manoSinFigura.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "7"));

		FiguraPoker sinFigura = new MazoPoker().getFigura(manoSinFigura);
		JugadorYFigura j5 = new JugadorYFigura(jugador5, sinFigura,
				manoSinFigura);
		//
		Jugador jugador6 = new Jugador("j6");

		List<CartaPoker> manoSinFigura2 = new ArrayList<>();
		manoSinFigura2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "K"));
		manoSinFigura2.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "2"));
		manoSinFigura2.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "4"));
		manoSinFigura2.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "10"));
		manoSinFigura2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "7"));

		FiguraPoker sinFigura2 = new MazoPoker().getFigura(manoSinFigura2);
		JugadorYFigura j6 = new JugadorYFigura(jugador6, sinFigura2,
				manoSinFigura2);
		//
		List<JugadorYFigura> ordenEsperado = new ArrayList<>();
		ordenEsperado.add(j1);
		ordenEsperado.add(j2);
		ordenEsperado.add(j4);
		ordenEsperado.add(j3);
		ordenEsperado.add(j6);
		ordenEsperado.add(j5);
		//
		List<JugadorYFigura> arrayDesordenado = new ArrayList<>();
		arrayDesordenado.add(j4);
		arrayDesordenado.add(j5);
		arrayDesordenado.add(j3);
		arrayDesordenado.add(j6);
		arrayDesordenado.add(j2);
		arrayDesordenado.add(j1);
		//
		Collections.sort(arrayDesordenado);

		for (JugadorYFigura c : arrayDesordenado) {
			System.out.println(c.toString());
		}

		assertEquals(ordenEsperado, arrayDesordenado);
	}

	@Test
	public void testearDesempateSinFiguras() {
		System.out.println("testearDesempateSinFiguras");

		TestManoPoker mano = new TestManoPoker();

		Jugador j1 = new Jugador("JUGADOR1");
		// J1 en este caso no tiene figuras
		List<CartaPoker> manoJ1 = new ArrayList<>();
		manoJ1.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "2"));
		manoJ1.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "3"));
		manoJ1.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "5"));
		manoJ1.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "7"));
		manoJ1.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "9"));

		mano.agregarJugadorConCartas(j1, manoJ1);

		Jugador j2 = new Jugador("JUGADOR2");
		// J2 en este caso no tiene figuras, pero tiene la carta mas alta
		List<CartaPoker> manoJ2 = new ArrayList<>();
		manoJ2.add(new CartaPoker(CartaPoker.PaloCarta.PIQUE, "2"));
		manoJ2.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "3"));
		manoJ2.add(new CartaPoker(CartaPoker.PaloCarta.DIAMANTE, "5"));
		manoJ2.add(new CartaPoker(CartaPoker.PaloCarta.TREBOL, "7"));
		manoJ2.add(new CartaPoker(CartaPoker.PaloCarta.CORAZON, "A"));
		mano.agregarJugadorConCartas(j2, manoJ2);

		SimpleEntry<Jugador, FiguraPoker> ganador = mano.obtenerGanador();
		System.out.println(ganador.getKey());
		System.out.println(ganador.getValue());
		assertEquals(j2, ganador.getKey());
	}
}
