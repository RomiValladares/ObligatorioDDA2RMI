package iu;

import iu.poker.FramePoker;
import javax.swing.JFrame;
import logica.JuegoCasino;
import logica.Jugador;
import logica.PartidaJuegoCasino;

public class FabricadorFrameJuegos {

    public static JFrame getVentanaJuego(JuegoCasino juego, Jugador jugador, PartidaJuegoCasino partida) throws Exception {
        try {
            switch (juego.getCodigo()) {
                case POKER:
                    return new FramePoker(partida, jugador);
            }
        } catch (Exception ex) {
            throw ex;
        }
        throw new Exception("La ventana para el codigo " + juego.getCodigo()
                + " no ha sido implementada.");
    }
}