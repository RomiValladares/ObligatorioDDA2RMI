package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Timer implements Runnable {

    private ArrayList<TareaTimer> tareas = new ArrayList<TareaTimer>();
    /**
     * por defecto cuenta hasta 30
     */
    private int hasta = 30000;
    /**
     * los intervalos en los que espera
     */
    private int intervalo = 1000;
    /**
     * total transcurrido desde que empezo el timer
     */
    private int totalTranscurrido = 0;
    private boolean on;

    public Timer() {
    }

    /**
     *
     * @param hasta tiempo en MILISEGUNDOS
     */
    public Timer(int hasta, TareaTimer t) {
        tareas.add(t);
        this.hasta = hasta;
    }

    public Timer(TareaTimer t) {
        tareas.add(t);
    }

    public void comenzar() {
        on = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (on && totalTranscurrido < hasta) {
                Thread.sleep(intervalo);
                totalTranscurrido += intervalo;
                debug();
                intervaloTranscurrido();
            }
            if (on) {
                timerFinalizado();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void timerFinalizado() {
        // TODO Auto-generated method stub
        ArrayList<TareaTimer> tareasTemp = new ArrayList<TareaTimer>(tareas);
        for (TareaTimer t : tareasTemp) {
            new DespachadorTimer(this)
                    .notificarTimerFinalizado(t, totalTranscurrido);
        }
    }

    private void intervaloTranscurrido() {
        // TODO Auto-generated method stub
        ArrayList<TareaTimer> tareasTemp = new ArrayList<TareaTimer>(tareas);
        for (TareaTimer t : tareasTemp) {
            new DespachadorTimer(this).notificarIntervaloTranscurrido(t,
                    intervalo, totalTranscurrido);
        }
    }

    public void agregarTarea(TareaTimer t) {
        tareas.add(t);
    }

    public void cancelar() {
        on = false;
    }

    private void debug() {
        //System.out.println("DEBUG TIMER date=" + new Date() + " totalTranscurrido=" + totalTranscurrido);
    }
}
