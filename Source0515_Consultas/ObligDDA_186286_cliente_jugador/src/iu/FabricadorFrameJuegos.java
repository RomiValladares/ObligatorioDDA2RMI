package iu;

import iu.poker.FramePoker;
import javax.swing.JFrame;
import logica.ssjuegos.JuegoCasino;
import logica.ssusuarios.Jugador;

public class FabricadorFrameJuegos {

    public static JFrame getVentanaJuego(JuegoCasino juego, Jugador jugador) throws Exception {
        try {
            switch (juego.getCodigo()) {
                case POKER:
                    return new FramePoker(juego, jugador);
            }
        } catch (Exception ex) {
            throw ex;
        }
        throw new Exception("La ventana para el codigo " + juego.getCodigo()
                + " no ha sido implementada.");
    }
}
