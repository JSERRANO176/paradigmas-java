package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logica.Pregunta;

public class PanelResultados extends JFrame {
    private List<Pregunta> preguntas;
    private Map<Pregunta, String> respuestasUsuario;

    public PanelResultados(List<Pregunta> preguntasRealizadas, Map<Pregunta, String> respuestasUsuario) {
        this.preguntas = preguntasRealizadas;
        this.respuestasUsuario = respuestasUsuario;

        setTitle("Resultados de la Prueba");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelResumen = new JPanel();
        panelResumen.setLayout(new GridLayout(0, 1));

        JLabel lblTitulo = new JLabel("Resumen de respuestas:", SwingConstants.CENTER);
        panelResumen.add(lblTitulo);

        Map<String, Integer> conteo = contarRespuestasPorTipo();
        int totalCorrectas = conteo.get("TotalCorrectas");
        int totalPreguntas = conteo.get("TotalPreguntas");
        int totalNoRespondidas = totalPreguntas - respuestasUsuario.size();

        int correctasVF = conteo.get("Verdadero/Falso Correctas");
        int incorrectasVF = conteo.get("Verdadero/Falso Incorrectas");
        int totalVF = correctasVF + incorrectasVF;
        int porcentajeVF = totalVF > 0 ? (correctasVF * 100 / totalVF) : 0;

        int correctasSM = conteo.get("Seleccion Multiple Correctas");
        int incorrectasSM = conteo.get("Seleccion Multiple Incorrectas");
        int totalSM = correctasSM + incorrectasSM;
        int porcentajeSM = totalSM > 0 ? (correctasSM * 100 / totalSM) : 0;

        JLabel lblCorrectasVF = new JLabel("Verdadero/Falso: " + correctasVF + " correctas, " + incorrectasVF + " incorrectas (" + porcentajeVF + "% de aciertos)", SwingConstants.CENTER);
        JLabel lblCorrectasSM = new JLabel("Selección Múltiple: " + correctasSM + " correctas, " + incorrectasSM + " incorrectas (" + porcentajeSM + "% de aciertos)", SwingConstants.CENTER);
        JLabel lblTotalPorcentaje = new JLabel("Porcentaje total de respuestas correctas: " + (totalCorrectas * 100 / totalPreguntas) + "%", SwingConstants.CENTER);
        JLabel lblNoRespondidas = new JLabel("Preguntas no contestadas: " + totalNoRespondidas, SwingConstants.CENTER);

        panelResumen.add(lblCorrectasVF);
        panelResumen.add(lblCorrectasSM);
        panelResumen.add(lblTotalPorcentaje);
        panelResumen.add(lblNoRespondidas);

        JButton btnRevisarPrueba = new JButton("Revisar respuestas");
        btnRevisarPrueba.addActionListener(e -> revisarPrueba());

        add(panelResumen, BorderLayout.CENTER);
        add(btnRevisarPrueba, BorderLayout.SOUTH);

        setVisible(true);
    }

    private Map<String, Integer> contarRespuestasPorTipo() {
        Map<String, Integer> conteoPorTipo = new HashMap<>();
        conteoPorTipo.put("Verdadero/Falso Correctas", 0);
        conteoPorTipo.put("Seleccion Multiple Correctas", 0);
        conteoPorTipo.put("Verdadero/Falso Incorrectas", 0);
        conteoPorTipo.put("Seleccion Multiple Incorrectas", 0);

        int totalCorrectas = 0;
        int totalPreguntas = preguntas.size();

        for (Pregunta pregunta : preguntas) {
            String respuestaUsuario = respuestasUsuario.get(pregunta);
            boolean esCorrecta = respuestaUsuario != null && respuestaUsuario.equalsIgnoreCase(pregunta.getRespuestaCorrecta());

            if (esCorrecta) {
                totalCorrectas++;
                if (pregunta.getTipo().equalsIgnoreCase("Verdadero/Falso")) {
                    conteoPorTipo.put("Verdadero/Falso Correctas", conteoPorTipo.get("Verdadero/Falso Correctas") + 1);
                } else if (pregunta.getTipo().equalsIgnoreCase("Seleccion Multiple")) {
                    conteoPorTipo.put("Seleccion Multiple Correctas", conteoPorTipo.get("Seleccion Multiple Correctas") + 1);
                }
            } else { // Contar respuestas incorrectas
                if (pregunta.getTipo().equalsIgnoreCase("Verdadero/Falso")) {
                    conteoPorTipo.put("Verdadero/Falso Incorrectas", conteoPorTipo.get("Verdadero/Falso Incorrectas") + 1);
                } else if (pregunta.getTipo().equalsIgnoreCase("Seleccion Multiple")) {
                    conteoPorTipo.put("Seleccion Multiple Incorrectas", conteoPorTipo.get("Seleccion Multiple Incorrectas") + 1);
                }
            }
        }

        conteoPorTipo.put("TotalCorrectas", totalCorrectas);
        conteoPorTipo.put("TotalPreguntas", totalPreguntas);

        return conteoPorTipo;
    }

    private void revisarPrueba() {
        dispose();
        new PanelRevisar(preguntas, respuestasUsuario); // Ahora pasa el mapa de respuestas
    }
}