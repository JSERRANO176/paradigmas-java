package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import logica.Pregunta;

public class PanelRevisar extends JFrame {
    private List<Pregunta> preguntas;
    private Map<Pregunta, String> respuestasUsuario;
    private int indicePregunta = 0;
    private JLabel lblEnunciado, lblRespuestaUsuario, lblRespuestaCorrecta;
    private JButton btnSiguiente, btnAnterior, btnSalir;

    public PanelRevisar(List<Pregunta> preguntasRealizadas, Map<Pregunta, String> respuestasUsuario) {
        this.preguntas = preguntasRealizadas;
        this.respuestasUsuario = respuestasUsuario;

        setTitle("Revisión de la Prueba");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel central para mostrar pregunta y respuestas
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(3, 1));

        lblEnunciado = new JLabel("", SwingConstants.CENTER);
        lblRespuestaUsuario = new JLabel("", SwingConstants.CENTER);
        lblRespuestaCorrecta = new JLabel("", SwingConstants.CENTER);
        
        panelCentral.add(lblEnunciado);
        panelCentral.add(lblRespuestaUsuario);
        panelCentral.add(lblRespuestaCorrecta);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones de navegación
        JPanel panelBotones = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnSalir = new JButton("Salir");

        btnAnterior.addActionListener(e -> mostrarPregunta(indicePregunta - 1));
        btnSiguiente.addActionListener(e -> mostrarPregunta(indicePregunta + 1));
        btnSalir.addActionListener(e -> cerrarVentana());

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);

        // Mostrar la primera pregunta
        mostrarPregunta(indicePregunta);

        setVisible(true);
    }

    private void mostrarPregunta(int nuevoIndice) {
        if (nuevoIndice >= 0 && nuevoIndice < preguntas.size()) {
            indicePregunta = nuevoIndice;
            Pregunta pregunta = preguntas.get(indicePregunta);
            
            String respuestaUsuario = respuestasUsuario.getOrDefault(pregunta, "No hubo respuesta");

            lblEnunciado.setText("Pregunta: " + pregunta.getEnunciado());
            lblRespuestaUsuario.setText("Tu respuesta: " + respuestaUsuario);
            lblRespuestaCorrecta.setText("Respuesta correcta: " + pregunta.getRespuestaCorrecta());
        }
    }

    private void cerrarVentana() {
        dispose();
    }
}