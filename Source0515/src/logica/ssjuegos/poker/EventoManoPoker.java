/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssjuegos.poker;

import java.io.Serializable;

/**
 *
 * @author Romi
 */
public class EventoManoPoker implements Serializable {

    public enum EventosManoPoker {

        COMENZO_MANO,
        NUEVA_APUESTA, FINALIZO_MANO, GANADOR, DESCARTAR_CARTAS;
    }

    private EventosManoPoker evento;
    private String descripcion;

    protected EventoManoPoker(EventosManoPoker evento) {
        this.evento = evento;
    }

    protected EventoManoPoker(EventosManoPoker evento, String descripcion) {
        this.evento = evento;
        this.descripcion = descripcion;
    }

    public EventosManoPoker getEvento() {
        return evento;
    }

    public String getDescripcion() {
        return descripcion != null ? descripcion : evento.toString();
    }

    @Override
    public String toString() {
        return "EventoManoPoker{" + "evento=" + evento + ", descripcion=" + descripcion + '}';
    }
}
