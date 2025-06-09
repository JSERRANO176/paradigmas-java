package logica;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Prueba {
    private List<Pregunta> preguntas;
    private int tiempoRestante; // En segundos
    private int indicePregunta = 0;
    private Timer temporizador;
    private PruebaListener listener;

    public Prueba(List<Pregunta> preguntas, int tiempoMinutos, PruebaListener listener) {
        this.preguntas = preguntas;
        this.tiempoRestante = tiempoMinutos * 60; // Convertir minutos a segundos
        this.listener = listener;
        iniciarTemporizador();
    }

    private void iniciarTemporizador() {
        temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tiempoRestante--;
                listener.actualizarTiempo(tiempoRestante);
                if (tiempoRestante <= 0) {
                    temporizador.cancel();
                    listener.finalizarPrueba();
                }
            }
        }, 1000, 1000);
    }

    public Pregunta obtenerPreguntaActual() {
        return preguntas.get(indicePregunta);
    }

    public void siguientePregunta() {
        if (indicePregunta < preguntas.size() - 1) {
            indicePregunta++;
            listener.actualizarPregunta(preguntas.get(indicePregunta));
        }
    }

    public void anteriorPregunta() {
        if (indicePregunta > 0) {
            indicePregunta--;
            listener.actualizarPregunta(preguntas.get(indicePregunta));
        }
    }

    public void entregarPrueba() {
        temporizador.cancel();
        listener.finalizarPrueba();
    }
}