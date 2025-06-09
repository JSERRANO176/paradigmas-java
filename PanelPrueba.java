package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import logica.Pregunta;
import logica.GestorPreguntas;

public class PanelPrueba extends JFrame {
    private List<Pregunta> preguntas;
    private int tiempoRestante;
    private int indicePregunta = 0;
    private JLabel lblEnunciado, lblTiempo;
    private JPanel panelOpciones;
    private JButton btnSiguiente, btnAnterior, btnEntregar;
    private Timer temporizador;
    private Map<Pregunta, String> respuestasUsuario = new HashMap<>();

    public PanelPrueba(GestorPreguntas gestor, int tiempo) {
        preguntas = gestor.getPreguntas();

        // Verificar si la primera línea es un encabezado
        if (!preguntas.isEmpty() && preguntas.get(0).getEnunciado().equals("Enunciado")) {
            preguntas.remove(0); // Eliminar encabezado si está presente
        }

        tiempoRestante = tiempo * 60; // Convertir minutos a segundos

        setTitle("Prueba en Curso");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior para mostrar el tiempo restante
        lblTiempo = new JLabel("Tiempo restante: " + tiempoRestante + " segundos", SwingConstants.CENTER);
        add(lblTiempo, BorderLayout.NORTH);

        // Panel central para mostrar la pregunta y opciones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());

        lblEnunciado = new JLabel("", SwingConstants.CENTER);
        panelCentral.add(lblEnunciado, BorderLayout.NORTH);

        panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(0, 1)); // Organiza las opciones en filas
        panelCentral.add(panelOpciones, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones de navegación
        JPanel panelBotones = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnEntregar = new JButton("Entregar prueba");

        btnAnterior.addActionListener(e -> mostrarPregunta(indicePregunta - 1));
        btnSiguiente.addActionListener(e -> mostrarPregunta(indicePregunta + 1));
        btnEntregar.addActionListener(e -> entregarPrueba());

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnEntregar);
        add(panelBotones, BorderLayout.SOUTH);

        // Inicializar temporizador
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                lblTiempo.setText("Tiempo restante: " + tiempoRestante + " segundos");
                if (tiempoRestante <= 0) {
                    temporizador.stop();
                    entregarPrueba();
                }
            }
        });
        temporizador.start();

        // Mostrar la primera pregunta correctamente
        if (!preguntas.isEmpty()) {
            mostrarPregunta(0);
        }

        setVisible(true);
    }

    private void mostrarPregunta(int nuevoIndice) {
        if (nuevoIndice >= 0 && nuevoIndice < preguntas.size()) {
            indicePregunta = nuevoIndice;
            Pregunta pregunta = preguntas.get(indicePregunta);

            lblEnunciado.setText("Pregunta: " + pregunta.getEnunciado());

            // Limpiar opciones anteriores
            panelOpciones.removeAll();
            ButtonGroup grupoOpciones = new ButtonGroup();

            if (pregunta.getTipo().equalsIgnoreCase("Seleccion Multiple")) {
                for (String opcion : pregunta.getOpciones()) {
                    if (!opcion.isEmpty()) {
                        JRadioButton rb = new JRadioButton(opcion);
                        grupoOpciones.add(rb);
                        panelOpciones.add(rb);
                        
                        rb.addActionListener(e -> respuestasUsuario.put(pregunta, opcion));
                    }
                }
            } else if (pregunta.getTipo().equalsIgnoreCase("Verdadero/Falso")) {
                JRadioButton rbVerdadero = new JRadioButton("Verdadero");
                JRadioButton rbFalso = new JRadioButton("Falso");
                grupoOpciones.add(rbVerdadero);
                grupoOpciones.add(rbFalso);
                panelOpciones.add(rbVerdadero);
                panelOpciones.add(rbFalso);

                rbVerdadero.addActionListener(e -> respuestasUsuario.put(pregunta, "Verdadero"));
                rbFalso.addActionListener(e -> respuestasUsuario.put(pregunta, "Falso"));
            }

            panelOpciones.revalidate();
            panelOpciones.repaint();
        }
    }

    private void entregarPrueba() {
        temporizador.stop();
        dispose();
        new PanelResultados(preguntas, respuestasUsuario); // Ahora pasa el mapa de respuestas
    }
}