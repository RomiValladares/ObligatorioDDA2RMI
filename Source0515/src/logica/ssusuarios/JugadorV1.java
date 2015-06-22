package logica.ssusuarios;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JugadorV1 extends UsuarioV1 implements Jugador {

    public JugadorV1(String nombre) throws RemoteException {
        super(nombre);
    }

    private double saldo;

    public JugadorV1() throws RemoteException {

    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void restarSaldo(double apuestaBase) {
        setSaldo(getSaldo() - apuestaBase);
    }

    @Override
    public String toString() {
        try {
            return getEtiqueta();
        } catch (RemoteException ex) {
            Logger.getLogger(JugadorV1.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public void agregarSaldo(double pozo) {
        this.saldo += pozo;
    }

    @Override
    public String getEtiqueta() throws RemoteException {
        return "Jugador " + getNombre();
    }

}
