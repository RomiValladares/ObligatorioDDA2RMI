/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import observableremoto.ObservableRemotoV1;

/**
 *
 * @author Romi
 */
public abstract class PartidaJuegoCasinoV1 extends ObservableRemotoV1 implements PartidaJuegoCasino  {

    public PartidaJuegoCasinoV1() throws RemoteException {
    }
    
    private DatosPartidaJuegoCasino datos = new DatosPartidaJuegoCasino(FabricadorJuegosCasino.CodigosJuego.POKER);

    protected void finalizarPartida() {
        setComenzada(false);
        setFinalizada(true);

        setTiempoFinal(new Date());

        obtenerGanador();

        modificar();
    }

    protected void comenzar() {
        setComenzada(true);

        //comenzar timer
        setTiempoInicial(new Date());

        guardar();
    }

    protected void guardar() {
        SsJuegos.getInstancia().guardar(datos);
    }

    protected void modificar() {
        SsJuegos.getInstancia().modificar(datos);
    }

    //METODOS DELEGADOS
    @Override
    public int getNumeroPartida() {
        return datos.getNumeroPartida();
    }

    protected void setNumeroPartida(int numeroPartida) {
        datos.setNumeroPartida(numeroPartida);
    }

    protected long getDuracion() {
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

    protected Date getComienzo() {
        return datos.getComienzo();
    }

    protected Date getFinal() {
        return datos.getFinal();
    }

    protected void setFinal(Date date) {
        datos.setFinal(date);
    }

    protected void setComienzo(Date date) {
        datos.setComienzo(date);
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
        return new ArrayList<>(datos.getJugadores().keySet());
    }

}
