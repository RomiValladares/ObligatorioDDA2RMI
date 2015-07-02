package logica;

import logica.ssjuegos.SsJuegos;
import logica.ssjuegos.JuegoCasino;
import logica.ssjuegos.DatosPartidaJuegoCasino;
import logica.ssusuarios.SsUsuarios;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssusuarios.Usuario;
import observableremoto.ObservableRemotoV1;

//Observable: lo observa la interfaz de usuario
//Observer: observa los subsistemas
//public class FachadaV1 extends Observable implements Observer, Fachada {
public class FachadaV1 extends ObservableRemotoV1 implements Fachada, Observer {

    private static FachadaV1 instancia;
    private final SsUsuarios ssUsuarios;
    private final SsJuegos ssJuegos;

    // <editor-fold defaultstate="collapsed" desc="SINGLETON">  
    private FachadaV1() throws RemoteException {
        ssUsuarios = SsUsuarios.getInstancia();
        ssJuegos = SsJuegos.getInstancia();
        ssJuegos.addObserver(this);
        //ssJuegos.addObserver(this);
    }

    public static FachadaV1 getInstancia() {
        if (instancia == null) {
            try {
                instancia = new FachadaV1();
            } catch (RemoteException ex) {
                Logger.getLogger(FachadaV1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instancia;
    }
    // </editor-fold> 

    @Override
    public Usuario ingresar(String nombreUsuario, String contrasena)
            throws RemoteException, Exception {
        return ssUsuarios.ingresar(nombreUsuario, contrasena);
    }

    // <editor-fold defaultstate="collapsed" desc="SS JUEGOS">  
    @Override
    public ArrayList<JuegoCasino> getJuegos() throws RemoteException {
        return ssJuegos.getJuegos();
    }

    @Override
    public boolean hayJuegosActivos() throws RemoteException {
        return ssJuegos.hayJuegoActivos();
    }

    @Override
    public double getGanancias() throws RemoteException {
        return ssJuegos.getGanancias();
    }

    /**
     *
     * @param codigoJuego juego por el cual se van a filtrar las partidas, o -1
     * si se quieren obtener todas
     */
    @Override
    public ArrayList<DatosPartidaJuegoCasino> getDatosPartidas(JuegoCasino codigoJuego) throws RemoteException {
        return ssJuegos.getDatosPartidas(codigoJuego);
    }
    // </editor-fold> 

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("FachadaV1 update arg=" + arg);
        notificar(arg);
    }

}
