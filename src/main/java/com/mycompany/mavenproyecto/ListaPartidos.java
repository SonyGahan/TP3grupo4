package com.mycompany.mavenproyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
    public void cargaDeDB(
            ListaEquipos listaequipos
    ) {
        
        Connection com=null;
        try { 
            //Establecer la conexion
            com = DriverManager.getConnection("jdbc:sqlite:pronosticos.db" );
            Statement stmt = com.createStatement();
            
            //String sql;
            String sql =  "Select "
                + "idPartido, idEquipo1, idEquipo2, golesEquipo1, golesEquipo2"
                + "FROM partidos";        
            ResultSet rs = stmt.executeQuery(sql); //Ejecutar la consulta y obtener resultado
           
            System.out.println ("conectado GRUPO 4");    
            while (rs.next()) {
                //Obtener los objetos que necesito para el constructor
                Equipo equipo1 = listaequipos.getEquipo(rs.getInt("idEquipo1"));
                Equipo equipo2 = listaequipos.getEquipo(rs.getInt("idEquipo2"));
		// crea el objeto en memoria
                Partido partido = new Partido(
                        rs.getInt("idPartido"),
                        equipo1,
                        equipo2,
                        rs.getInt("golesEquipo1"),
                        rs.getInt("golesEquipo2")
                );

		// llama al metodo add para grabar el equipo en la lista en memoria
                this.addPartido(partido);

            }
            //closes the scanner
        } catch (SQLException e) {
                System.out.println("Mensaje: " + e.getMessage());
        } finally {
            try {
                if (com != null) {
                    com.close();
                }
            } catch (SQLException e) {
                // conn close failed.
                System.out.println(e.getMessage());
            }
        }       
    }  
}
