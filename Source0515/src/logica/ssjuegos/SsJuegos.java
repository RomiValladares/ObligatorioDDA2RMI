package logica.ssjuegos;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import logica.ssjuegos.JuegoCasinoV1.EventosJuegoCasino;

//Observer porque observa los juegos para saber cuando se produce una nueva ganancia
//Observable porque la fachada lo observa
public class SsJuegos extends Observable implements Observer {

    private final ServiciosJuego servicios;

    private static SsJuegos instancia;
    private ArrayList<JuegoCasinoV1> juegos;
    private double ganancias;

    private SsJuegos() {
//        juegos = FabricadorJuegosCasino.getJuegosCasino();
        servicios = new ServiciosJuegoV1();
        juegos = servicios.getJuegos();
        ganancias = servicios.getGanancias();
        for (JuegoCasinoV1 juego : juegos) {
            juego.registrar(this);
        }
    }

    public static SsJuegos getInstancia() {
        if (instancia == null) {
            instancia = new SsJuegos();
        }
        return instancia;
    }

    public ArrayList<JuegoCasino> getJuegos() {
        return new ArrayList<JuegoCasino>(juegos);
    }

    public double getGanancias() {
        return ganancias;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EventosJuegoCasino) {
            actualizarGanancias();
        }

        setChanged();
        notifyObservers();
    }

    private void actualizarGanancias() {
        ganancias = 0;
        for (JuegoCasinoV1 juego : juegos) {
            ganancias += juego.getGanancias();
        }

        servicios.setGanancias(ganancias);
    }

    public boolean hayJuegoActivos() throws RemoteException {
        for (JuegoCasinoV1 juego : juegos) {
            if (juego.tienePartidasActivas()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param codigoJuego juego por el cual se van a filtrar las partidas, o -1
     * si se quieren obtener todas
     */
    public ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(JuegoCasino codigoJuego) throws RemoteException {
        return servicios.getDatosPartidas(codigoJuego.getCodigo());
    }

    protected void guardar(DatosPartidaJuegoCasino p) {
        servicios.guardar(p);
        //TODO avisar que cambio el numero partida
    }

    protected void modificar(DatosPartidaJuegoCasino p) {
        servicios.modificar(p);
    }
}
