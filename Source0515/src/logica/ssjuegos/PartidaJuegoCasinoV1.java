/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ssusuarios.Jugador;
import observableremoto.ObservableRemotoV1;

/**
 *
 * @author Romi
 */
public abstract class PartidaJuegoCasinoV1 extends ObservableRemotoV1 implements PartidaJuegoCasino {

    /**
     * para saber si usa timer o no
     */
    private boolean cronometrada = false;
    private PartidaTareaTimer timer;
    private int tiempoTimer = 15000;

    public PartidaJuegoCasinoV1() throws RemoteException {
    }

    public PartidaJuegoCasinoV1(boolean timed, int tiempo) throws RemoteException {
        this.cronometrada = timed;
        this.tiempoTimer = tiempo;
        timer = new PartidaTareaTimer(tiempoTimer, this);
    }

    private DatosPartidaJuegoCasino datos = new DatosPartidaJuegoCasino(FabricadorJuegosCasino.CodigosJuego.POKER);

    protected void finalizarPartida() {
        setComenzada(false);
        setFinalizada(true);

        setTiempoFinal(new Date());

        obtenerGanador();

        PartidaJuegoCasinoV1.this.modificar();
    }

    protected void comenzar() {
        if (!isComenzada()) {
            setComenzada(true);

            //comenzar timer
            setTiempoInicial(new Date());

            guardar();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="DB">  
    protected void guardar() {
        SsJuegos.getInstancia().guardar(datos);
    }

    protected void modificar(Jugador j) {
        SsJuegos.getInstancia().modificar(j);
    }

    protected void modificar() {
        SsJuegos.getInstancia().modificar(datos);
    }
// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="GETTERS/SETTERS">  
    @Override
    public int getNumeroPartida() {
        return datos.getNumeroPartida();
    }

    protected void setNumeroPartida(int numeroPartida) {
        datos.setNumeroPartida(numeroPartida);
    }

    protected double getDuracion() {
        return datos.getDuracion();
    }

    protected double getTotalApostado() {
        return datos.getTotalApostado();
    }

    protected void setTotalApostado(double t) {
        datos.setTotalApostado(t);
    }

    protected void setOid(int oid) {
        datos.setOid(oid);
    }

    protected int getOid() {
        return datos.getOid();
    }

    protected void setNumero(int aInt) {
        datos.setNumero(aInt);
    }

    protected void setDuracion(long aLong) {
        datos.setDuracion(aLong);
    }

    @Override
    public double getGanancias() {
        return datos.getGanancias();
    }

    protected void setGanancias(double ganancias) {
        datos.setGanancias(ganancias);
    }

    protected Date getTiempoInicial() {
        return datos.getTiempoInicial();
    }

    protected void setTiempoInicial(Date tiempoInicial) {
        datos.setTiempoInicial(tiempoInicial);
    }

    protected Date getTiempoFinal() {
        return datos.getTiempoFinal();
    }

    protected void setTiempoFinal(Date tiempoFinal) {
        datos.setTiempoFinal(tiempoFinal);
    }

    @Override
    public boolean isComenzada() {
        return datos.isComenzada();
    }

    protected void setComenzada(boolean comenzada) {
        datos.setComenzada(comenzada);
    }

    protected void setFinalizada(boolean finalizada) {
        datos.setFinalizada(finalizada);
    }

    @Override
    public boolean isFinalizada() {
        return datos.isFinalizada();
    }

    protected void setFinal(Date date) {
        datos.setFinal(date);
    }

    protected abstract void obtenerGanador();

    protected void setGanador(Jugador j) {
        datos.setGanador(j);
    }

    protected Jugador getGanador() {
        return datos.getGanador();
    }

    @Override
    public Jugador getGanadorPartida() throws Exception {
        if (!isFinalizada()) {
            throw new Exception("La partida no finalizo, todavia no existe un ganador.");
        }
        return datos.getGanador();
    }

    protected HashMap<Jugador, Double> getJugadores() {
        return datos.getJugadores();
    }

    @Override
    public ArrayList<Jugador> getJugadoresPartida() {
        return new ArrayList<Jugador>(datos.getJugadores().keySet());
    }
// </editor-fold> 

    protected void setJugadorActivo(Jugador j) {
        if (cronometrada && timer != null) {
            timer.remove(j);
        }
    }

    //el throws exception es porque la partida poker tira excepcion nomas
    protected void quitarJugador(Jugador j) throws Exception {
        if (getJugadores().containsKey(j)) {
            setJugadorActivo(j);
        } else {
            throw new Exception("El jugador no esta en esta partida.");
        }
    }

    protected void empezarTimer() {
        System.out.println("empezarTimer");
        cancelarTimer();
        timer = new PartidaTareaTimer(tiempoTimer, this, getJugadoresPartida());
        timer.comenzar();
    }

    protected void cancelarTimer() {
        if (timer != null) {
            timer.cancelar();
        }
    }

    @Override
    public boolean isCronometrada() {
        return cronometrada;
    }

    /**
     *
     * @return -1 si no es cronometrada, tiempo en segs si lo es
     */
    public int getTimer() throws RemoteException {
        if (cronometrada) {
            return tiempoTimer;
        }
        return -1;
    }

    protected void descontarSaldo(Jugador j, double monto) {
        try {
            j.restarSaldo(monto);
            modificar(j);
        } catch (RemoteException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void agregarSaldo(Jugador j, double monto) {
        try {
            j.agregarSaldo(monto);
            modificar(j);
        } catch (RemoteException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     *
     * @param jugadoresTimeout que no hicieron nada antes de que terminara el
     * timer
     */
    protected abstract void timeout(ArrayList<Jugador> jugadoresTimeout);
}
