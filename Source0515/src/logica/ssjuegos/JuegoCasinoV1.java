package logica.ssjuegos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssjuegos.poker.PartidaPokerV1;
import observableremoto.ObservableLocal;

/**
 *
 * @author Romi Observable porque lo observa la interfaz para actualizarse
 * cuando por ejemplo entra un jugador a la partida Observer porque observa a la
 * partida
 */
//public abstract class JuegoCasino extends Observable implements Observer {
public abstract class JuegoCasinoV1 extends UnicastRemoteObject implements JuegoCasino {

    private ObservableLocal observable = new ObservableLocal();

    double ganancias;
    private int ultimoNumeroPartida;
    // todas las partidas activas (siendo jugadas) en el momento
    private ArrayList<PartidaPokerV1> partidas = new ArrayList<>();
    // partida que esta esperando a ser comenzada
    private PartidaPokerV1 proximaPartida;

    public enum EventosJuegoCasino {

        NUEVA_GANANCIA
    }

    protected JuegoCasinoV1() throws RemoteException {
    }

    protected double getGanancias() {
        return ganancias;
    }

    public void setGanancias(double ganancias) {
        this.ganancias = ganancias;
    }

    @Override
    public String toString() {
        try {
            return getNombre();
        } catch (RemoteException ex) {
            Logger.getLogger(JuegoCasinoV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setUltimoNumeroPartida(int nPartida) {
        this.ultimoNumeroPartida = nPartida;
    }

    public int getUltimoNumeroPartida() {
        return ultimoNumeroPartida;
    }

    protected abstract void crearPartida();

    protected ArrayList<PartidaPokerV1> getPartidas() {
        return partidas;
    }

    protected void setPartidas(ArrayList<PartidaPokerV1> partidas) {
        this.partidas = partidas;
    }

    protected PartidaPokerV1 getProximaPartida() {
        return proximaPartida;
    }

    protected void setProximaPartida(PartidaPokerV1 proximaPartida) {
        this.proximaPartida = proximaPartida;
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS OBSERVABLE">  
    public void registrar(Observer obs) {
        observable.addObserver(obs);
    }

    public void desregistrar(Observer obs) {
        observable.deleteObserver(obs);
    }

    protected void notificar() {
        notificar(null);
    }

    protected void notificar(Object param) {
        observable.avisar(param);
    }
// </editor-fold> 
}
