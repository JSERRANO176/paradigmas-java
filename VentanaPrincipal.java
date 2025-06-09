package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.GestorPreguntas;

public class VentanaPrincipal extends JFrame {
    private GestorPreguntas gestorPreguntas;
    private JLabel lblArchivo;
    private String rutaArchivo; // Almacenar la ruta del archivo

    public VentanaPrincipal() {
        gestorPreguntas = new GestorPreguntas();

        setTitle("Carga de Preguntas");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        lblArchivo = new JLabel("Archivo no seleccionado");
        JButton btnSeleccionar = new JButton("Seleccionar Archivo");
        JButton btnContinuar = new JButton("Continuar");

        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int resultado = fileChooser.showOpenDialog(null);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath(); // Guardar la ruta del archivo
                    lblArchivo.setText("Seleccionado: " + fileChooser.getSelectedFile().getName());

                    if (!gestorPreguntas.validarArchivo(rutaArchivo)) {
                        JOptionPane.showMessageDialog(null, "El archivo tiene errores en su formato. Verifique y vuelva a intentarlo.", "Error de archivo", JOptionPane.ERROR_MESSAGE);
                        rutaArchivo = null; // Restablecer la ruta si el archivo es invalido
                    } else {
                        gestorPreguntas.cargarPreguntasDesdeArchivo(rutaArchivo);
                    }
                }
            }
        });

        btnContinuar.addActionListener(e -> avanzarConfiguracion());

        add(btnSeleccionar);
        add(lblArchivo);
        add(btnContinuar);
    }

    private void avanzarConfiguracion() {
        if (rutaArchivo == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo valido antes de continuar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dispose();
        new PanelStart(gestorPreguntas);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}