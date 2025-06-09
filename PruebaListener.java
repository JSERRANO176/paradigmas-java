package logica;

public interface PruebaListener {
    void actualizarTiempo(int tiempoRestante);
    void actualizarPregunta(Pregunta pregunta);
    void finalizarPrueba();
}
