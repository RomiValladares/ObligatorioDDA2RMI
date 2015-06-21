/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import logica.FabricadorJuegosCasino.CodigosJuego;

/**
 *
 * @author Romi
 */
public class DatosPartidaJuegoCasino implements Serializable {

    private HashMap<Jugador, Double> jugadores = new HashMap<>();
    private double ganancias;
    private int oid;
    private int numeroPartida;
    private Jugador ganador;

    //para calcular la duracion
    private Date tiempoInicial, tiempoFinal;
    private long duracion;
    private double totalApostado;
    private boolean comenzada = false;
    private boolean finalizada;

    private CodigosJuego codigoJuego;

    public DatosPartidaJuegoCasino(CodigosJuego codigoJuego) {
        this.codigoJuego = codigoJuego;
    }

    public DatosPartidaJuegoCasino(int nPartida, CodigosJuego codigoJuego) {
        numeroPartida = nPartida;
        this.codigoJuego = codigoJuego;
    }

    public int getNumeroPartida() {
        return numeroPartida;
    }

    protected void setNumeroPartida(int numeroPartida) {
        this.numeroPartida = numeroPartida;
    }

    /**
     * @return duracion desde que empieza hasta que se obtiene el ganador, en
     * segundos
     */
    public long getDuracion() {
        if (tiempoInicial == null) {
            return 0;
        }
        if (tiempoFinal == null) {
            return new Date().getTime() - tiempoInicial.getTime();
        }
        return tiempoFinal.getTime() - tiempoInicial.getTime();
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public void setTotalApostado(double t) {
        totalApostado = t;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getOid() {
        return oid;
    }

    public void setNumero(int aInt) {
        numeroPartida = aInt;
    }

    /*
     * solo para cuando se va a leer desde la bd
     */
    public void setDuracion(long aLong) {
        duracion = aLong;
    }

    public double getGanancias() {
        return ganancias;
    }

    public void setGanancias(double ganancias) {
        this.ganancias = ganancias;
    }

    public Date getTiempoInicial() {
        return tiempoInicial;
    }

    public void setTiempoInicial(Date tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public Date getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(Date tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public boolean isComenzada() {
        return comenzada;
    }

    public void setComenzada(boolean comenzada) {
        this.comenzada = comenzada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Date getComienzo() {
        return tiempoInicial;
    }

    public Date getFinal() {
        return tiempoFinal;
    }

    public void setFinal(Date date) {
        tiempoFinal = date;
    }

    public void setComienzo(Date date) {
        tiempoInicial = date;
    }

    protected void obtenerGanador() {
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador j) {
        ganador = j;
    }

    public HashMap<Jugador, Double> getJugadores() {
        return jugadores;
    }

    /**
     * lectura desde BD
     *
     * @param j
     */
    public void agregarJugador(Jugador j, double ganancias) {
        jugadores.put(j, ganancias);
    }

}
