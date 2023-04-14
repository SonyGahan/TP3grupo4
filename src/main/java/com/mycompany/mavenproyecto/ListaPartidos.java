package com.mycompany.mavenproyecto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ListaPartidos {
   //Atributos
    private List<Partido> partidos;
    private String partidosCSV;
    
    
//Metodos
    
    //Constructor 
    public ListaPartidos(List<Partido> partidos, String partidosCSV) {
        this.partidos = partidos;
        this.partidosCSV = partidosCSV;
    }
    
    public ListaPartidos() {
        this.partidos = new ArrayList<Partido>();
        this.partidosCSV = "partidos.csv";
    }
    
    
    //Getters and Setters
    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public String getPartidosCSV() {
        return partidosCSV;
    }

    public void setPartidosCSV(String partidosCSV) {
        this.partidosCSV = partidosCSV;
    }
    
    // add y remove elementos
    public void addPartido(Partido p) {
        this.partidos.add(p);
    }
    public void removePartido(Partido p) {
        this.partidos.remove(p);
    }
    
    
    //ToString - revisar
    @Override
    public String toString() {
        return "ListaPartidos{" + "partidos=" + partidos + ", partidosCSV=" + partidosCSV + '}';
    }
    
    
    public String listar() {
        String lista = "";
        for (Partido partido: partidos) {
            lista += "\n" + partido;
        }           
        return lista;
    }
    
    
    /***
     * Este metodo devuelve un objeto Partido (o null) buscandolo por idPartido
     * @param idPartido Identificador del partido deseado
     * @return Objeto Partido (o null si no se encuentra)
     */
    public Partido getPartido (int idPartido) {
        // Defini un objeto de tipo Partido en donde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el partido que 
        // buscaba todavi­a.
        Partido encontrado = null;
        // Recorro la lista de partidos que esta¡ cargada
        for (Partido eq : this.getPartidos()) {
            // Para cada partido obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (eq.getIdPartido() == idPartido) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = eq;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el partido, pueden pasar dos cosas:
        // 1- Lo encontro en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontro en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }
    
    
    // cargar desde el archivo
    public void cargarDeArchivo(
            int idPartido, // id del partido
            ListaEquipos listaequipos // lista de equipos       
    ) {
        // para las lineas del archivo csv
        String datosPartido;
        // para los datos individuales de cada linea
        String vectorPartido[];
        // para el objeto en memoria
        Partido partido;
        int fila = 0;
       
        try { 
            Scanner sc = new Scanner(new File(this.getPartidosCSV()));
            sc.useDelimiter("\n");   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPartido = sc.next();
                // Descomentar si se quiere mostrar cada li­nea lei­da desde el archivo
                // System.out.println(datosPartido);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPartido = datosPartido.split(",");   
                
                // graba el partido en memoria
                //convertir un string a un entero;
                int readidPartido = Integer.parseInt(vectorPartido[0]);
                int readidEquipo1 = Integer.parseInt(vectorPartido[1]);
                int readidEquipo2 = Integer.parseInt(vectorPartido[2]);
                int readgolesEquipo1 = Integer.parseInt(vectorPartido[3]);
                int readgolesEquipo2 = Integer.parseInt(vectorPartido[4]);
                if (readidPartido == idPartido) {
                    // Obtener los objetos que necesito para el constructor
                    Equipo equipo1 = listaequipos.getEquipo(readidEquipo1);
                    Equipo equipo2 = listaequipos.getEquipo(readidEquipo2);
                    // crea el objeto en memoria
                    partido = new Partido(
                            readidPartido, // El id leido del archivo
                            equipo1, // El objeto Equipo1 que obtuvimos de la lista
                            equipo2, // El objeto Equipo2 que obtuvimos de la lista
                            readgolesEquipo1, // Goles del equipo1 que leimos del archivo
                            readgolesEquipo2 // Goles del equipo2 que leimos del archivo
                    );
                
                    // llama al metodo add para grabar el partido en la lista en memoria
                    this.addPartido(partido);
                }
            }
            //closes the scanner
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       
    }
}
