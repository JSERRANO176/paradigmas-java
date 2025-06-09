package logica;

import java.io.*;
import java.util.*;

public class GestorPreguntas {
    private List<Pregunta> preguntas;

    public GestorPreguntas() {
        preguntas = new ArrayList<>();
    }

    public boolean validarArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignora la primera linea si es un encabezado
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");

                // Validar que la linea tenga 4 columnas
                if (partes.length != 6) {
                    System.out.println("Error: La linea no tiene el formato correcto: " + linea);
                    return false;
                }
                
                String nivel = partes[1].replace("\"", "").trim();
                
                //Validar nivel taxonomico de la pregunta
                try {
                    NivelTaxonomico.valueOf(nivel.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Nivel taxonómico inválido en línea: " + linea);
                    return false;
                }

                String tipo = partes[2].replace("\"", "").trim();

                // Validar tipo de pregunta
                if (!tipo.equalsIgnoreCase("Verdadero/Falso") && !tipo.equalsIgnoreCase("Seleccion Multiple")) {
                    System.out.println("Error: Tipo de pregunta invalido en linea: " + linea);
                    return false;
                }

                // Validar opciones si es de seleccion multiple
                if (tipo.equalsIgnoreCase("Seleccion Multiple") && partes[3].trim().isEmpty()) {
                    System.out.println("Error: Pregunta de seleccion multiple sin opciones en linea: " + linea);
                    return false;
                }
                
                //Validar si el tiempo es numérico
                try {
                    int tiempo = Integer.parseInt(partes[5].replace("\"", "").trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Tiempo inválido en línea: " + linea);
                    return false;
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void cargarPreguntasDesdeArchivo(String rutaArchivo) {
        if (!validarArchivo(rutaArchivo)) {
            System.out.println("El archivo tiene errores en su formato. No se pueden cargar las preguntas.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignora la primera linea si es un encabezado
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length >= 6) {
                    String enunciado = partes[0].replace("\"", "").trim();
                    String nivelStr = partes[1].replace("\"", "").trim();
                    String tipo = partes[2].replace("\"", "").trim();
                    String opcionesStr = partes[3].replace("\"", "").trim();
                    String respuestaCorrecta = partes[4].replace("\"", "").trim();
                    int tiempo = Integer.parseInt(partes[5].replace("\"", "").trim());
                    
                    NivelTaxonomico nivel = NivelTaxonomico.valueOf(nivelStr.toUpperCase());

                    String[] opciones = opcionesStr.isEmpty() ? new String[0] : opcionesStr.split(",");

                    Pregunta pregunta = new Pregunta(enunciado, tipo, opciones, respuestaCorrecta, nivel, tiempo);
                    preguntas.add(pregunta);
                } else {
                    System.out.println("Error en formato de linea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
    
    public int getTiempoTotal(){
        int total = 0;
        for (Pregunta p : preguntas) {
            total += p.getTiempo();
        }
        return total;
    }
}