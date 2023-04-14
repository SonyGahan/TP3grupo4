
package com.mycompany.mavenproyecto;


public class Pronostico {
    //Atributos
    private int idPronostico;
    private Equipo equipo;
    private Partido partido;
    private char resultado;
    private Integer idParticipante;
   
    
    //Metodos
    
    //Constructor

    public Pronostico(int idPronostico, Equipo equipo, Partido partido, char resultado, Integer idParticipante) {
        this.idPronostico = idPronostico;
        this.equipo = equipo;
        this.partido = partido;
        this.resultado = resultado;
        this.idParticipante = idParticipante;
    }
    
    public Pronostico(int idPronostico, Equipo equipo, Partido partido, char resultado) {
        this.idPronostico = idPronostico;
        this.equipo = equipo;
        this.partido = partido;
        this.resultado = resultado;
    }
    
    public Pronostico() {
        this.idPronostico = 0;
        this.equipo = null;
        this.partido = null;
        this.resultado = '\0';
        this.idParticipante = null;
    }
    
    
    //Getters and Setters
    public int getIdPronostico() {
        return idPronostico;
    }

    public void setIdPronostico(int idPronostico) {
        this.idPronostico = idPronostico;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public char getResultado() {
        return resultado;
    }

    public void setResultado(char resultado) {
        this.resultado = resultado;
    }

    public Integer getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Integer idParticipante) {
        this.idParticipante = idParticipante;
    }

    
    
    // Metodo que devuelve 0 o 1 segun haya coincidencia o no, entre el resultado apostado y el resultado real del partido
    // Si hay coincidencia hay 1 punto en esa apuesta, sino hay 0 puntos.
    // Cada pronostico es una "apuesta" a que en un Partido determinado sale un cierto resultado (Gana, Pierde, Empata)
    //Tengo que poder comparar el resultado del pronostico con el resultado del partido.
    //Si coinciden guardo el valor 1, que se usara en el contador de la clase Prticipante, para calcular el valor total del puntaje.
    int puntaje;
    
    
    public int getPuntaje(){
        if (resultado == resultadoPartido) //No logro definir como traer de la clase Partido la variable, resulPartido con su valor.
            puntaje = 1;
        else 
            puntaje = 0;
    }
    
    
    
    //ToString
    @Override
    public String toString() {
        return "Pronostico{" + "idPronostico=" + idPronostico + ", equipo=" + equipo + ", partido=" + partido + ", resultado=" + resultado + ", idParticipante=" + idParticipante + '}';
    }
}
