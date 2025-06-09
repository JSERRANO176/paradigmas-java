package gui;

import javax.swing.*;
import java.awt.*;
import logica.GestorPreguntas;

public class PanelStart extends JFrame {
    private GestorPreguntas gestorPreguntas;
    
    public PanelStart(GestorPreguntas gestor) {
        this.gestorPreguntas = gestor;

        setTitle("PRUEBA");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Mostrar cantidad de preguntas disponibles
        JLabel lblCantidadPreguntas = new JLabel("Preguntas cargadas: " + gestorPreguntas.getPreguntas().size());

        // Campo para ingresar tiempo limite
        JLabel lblTiempo = new JLabel("Tiempo de prueba (min): " + gestorPreguntas.getTiempoTotal());

        JButton btnIniciarPrueba = new JButton("Iniciar Prueba");

        btnIniciarPrueba.addActionListener(e -> iniciarPrueba(gestorPreguntas.getTiempoTotal()));

        add(lblCantidadPreguntas);
        add(lblTiempo);
        add(btnIniciarPrueba);

        setVisible(true);
    }

    private void iniciarPrueba(int tiempo) {
        dispose();
        new PanelPrueba(gestorPreguntas, tiempo);
    }
}
