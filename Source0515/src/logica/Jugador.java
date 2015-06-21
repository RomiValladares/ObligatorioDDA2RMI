package logica;

public class Jugador extends Usuario {

    public Jugador(String nombre) {
        super(nombre);
    }

    private double saldo;

    public Jugador() {

    }

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
