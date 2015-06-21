package logica;

import java.rmi.RemoteException;
import logica.poker.JuegoPoker;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricadorJuegosCasino {

    public enum CodigosJuego {

        POKER(5);
        private int codigo;

        private CodigosJuego(int value) {
            this.codigo = value;
        }

        public int getCodigo() {
            return codigo;
        }
    }

    public static ArrayList<JuegoCasinoV1> getJuegosCasino() {
        ArrayList<JuegoCasinoV1> juegos = new ArrayList<>();
        try {
            juegos.add(new JuegoPoker());
        } catch (RemoteException ex) {
            Logger.getLogger(FabricadorJuegosCasino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return juegos;
    }
}
