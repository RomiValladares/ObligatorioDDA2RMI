/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.poker;

import java.io.Serializable;
import logica.Jugador;

/**
 *
 * @author Romi
 */
public class EventoPartidaPoker implements Serializable {

    public enum EventosPartidaPoker {

        COMENZO_PARTIDA, FINALIZO_PARTIDA, JUGADOR_SALDO_INSUFICIENTE, SALIDA_JUGADOR
    }

    private EventosPartidaPoker evento;
    private String descripcion;
    private Jugador jugador;

    protected EventoPartidaPoker(EventosPartidaPoker evento) {
        this.evento = evento;
    }

    protected EventoPartidaPoker(EventosPartidaPoker evento, String descripcion) {
        this.evento = evento;
        this.descripcion = descripcion;
    }

    protected EventoPartidaPoker(EventosPartidaPoker evento, String descripcion, Jugador j) {
        this.evento = evento;
        this.descripcion = descripcion;
        this.jugador = j;
    }

    public EventosPartidaPoker getEvento() {
        return evento;
    }

    public String getDescripcion() {
        return descripcion != null ? descripcion : evento.toString();
    }

    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public String toString() {
        return "EventoPartidaPoker{" + "evento=" + evento + ", descripcion=" + descripcion + ", jugador=" + jugador + '}';
    }

}
