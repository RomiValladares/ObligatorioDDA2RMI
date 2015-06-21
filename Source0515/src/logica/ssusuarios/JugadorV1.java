package logica.ssusuarios;

import java.rmi.RemoteException;

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
        return "Jugador " + getNombre();
    }

    public void agregarSaldo(double pozo) {
        this.saldo += pozo;
    }

}
