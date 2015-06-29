package logica;

public class DespachadorTimer {

    private Timer timer;
    private TareaTimer tarea;

    public DespachadorTimer(Timer timer) {
        // TODO Auto-generated constructor stub
        this.timer = timer;
    }

    public void notificarTimerFinalizado(TareaTimer t, int totalTranscurrido) {
        // TODO Auto-generated method stub
        new Thread(new TimerFinalizado(timer, t, totalTranscurrido)).start();
    }

    public void notificarIntervaloTranscurrido(TareaTimer t, int intervalo,
            int totalTranscurrido) {
        // TODO Auto-generated method stub
        new Thread(new IntervaloTranscurrido(timer, t, intervalo, totalTranscurrido)).start();
    }

    private class IntervaloTranscurrido implements Runnable {

        private final int totalTranscurrido;
        private final Timer timer;
        private final TareaTimer tarea;
        private final int intervalo;

        public IntervaloTranscurrido(Timer timer, TareaTimer tarea, int intervalo, int totalTranscurrido) {
            this.totalTranscurrido = totalTranscurrido;
            this.timer = timer;
            this.tarea = tarea;
            this.intervalo = intervalo;
        }

        @Override
        public void run() {
            tarea.intervaloTranscurrido(timer, intervalo, totalTranscurrido);
        }
    }

    private class TimerFinalizado implements Runnable {

        private final int totalTranscurrido;
        private final Timer timer;
        private final TareaTimer tarea;

        protected TimerFinalizado(Timer t, TareaTimer tt, int totalTranscurrido) {
            this.timer = t;
            this.totalTranscurrido = totalTranscurrido;
            this.tarea = tt;
        }

        @Override
        public void run() {
            tarea.timerFinalizado(timer, totalTranscurrido);
        }
    }
}
