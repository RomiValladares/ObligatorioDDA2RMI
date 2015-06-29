package logica;

public interface TareaTimer {
	/**
	 * se llama desde {@link Timer#intervaloTranscurrido} cada vez que
	 * transcurre un intervalo
	 * 
	 * @param timer
	 *            que notifica
	 * @param intervaloTranscurrido
	 *            desde la anterior llamada
	 * @param tiempoTranscurrido
	 *            desde el inicio hasta el final del timer
	 */
	public void intervaloTranscurrido(Timer timer, int intervaloTranscurrido,
			int tiempoTranscurrido);

	/**
	 * 
	 * @param timer
	 *            que notifica
	 * @param tiempoTranscurrido
	 *            desde el inicio hasta el final del timer
	 */
	public void timerFinalizado(Timer timer, int tiempoTranscurrido);
}
