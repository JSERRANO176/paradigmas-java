package logica;

public class Pregunta {
    private String enunciado;
    private NivelTaxonomico nivel;
    private String tipo;
    private String[] opciones; // Para seleccion multiple
    private String respuestaCorrecta;
    private int tiempo;

    public Pregunta(String enunciado, String tipo, String[] opciones, String respuestaCorrecta, NivelTaxonomico nivel, int tiempo) {
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.nivel = nivel;
        this.tiempo = tiempo;
    }

    public String getEnunciado() {
        return enunciado;
    }
    
    public NivelTaxonomico getNivel(){
        return nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
    
    public int getTiempo(){
        return tiempo;
    }
}