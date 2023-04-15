package com.mycompany.mavenproyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ListaPronosticos {
    //Atributos
    private List<Pronostico> pronosticos;
    private String pronosticosCSV;
    
    
    
    //Metodos
    
    //Constructor
    public ListaPronosticos(List<Pronostico> pronosticos, String pronosticosCSV) {
        this.pronosticos = pronosticos;
        this.pronosticosCSV = pronosticosCSV;
    }
    
    public ListaPronosticos() {
        this.pronosticos = new ArrayList<Pronostico>();
        this.pronosticosCSV = "pronosticos.csv";
    }
    
    //Getters and Setters
    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }

    public String getPronosticosCSV() {
        return pronosticosCSV;
    }

    public void setPronosticosCSV(String pronosticosCSV) {
        this.pronosticosCSV = pronosticosCSV;
    }
    
    // add y remove elementos
    public void addPronostico(Pronostico p) {
        this.pronosticos.add(p);
    }
    public void removePronostico(Pronostico p) {
        this.pronosticos.remove(p);
    }
    
    
    //ToString - revisar
    @Override
    public String toString() {
        return "ListaPronosticos{" + "pronosticos=" + pronosticos + ", pronosticosCSV=" + pronosticosCSV + '}';
    }
    
    
    public String listar() {
        String lista = "";
        for (Pronostico pronostico: pronosticos) {
            lista += "\n" + pronostico;
        }           
        return lista;
    }
    
    
    // cargar desde la Base de Datos
    public void cargaDeDB(
            int idParticipante, // id del participante que realizó el pronóstico
            ListaEquipos listaequipos, // lista de equipos
            ListaPartidos listapartidos // lista de partidos
    ) {
        
        
        Connection com=null;
        try { 
            //Establecer la conexion
            com = DriverManager.getConnection("jdbc:sqlite:pronosticos.db" );
            //Crear el "statement" para enviar comandos
            Statement stmt = com.createStatement();
            
            //String sql;
            String sql =  "Select"
                + "idPronostico, idParticipante, idPartido, idEquipo, resultado "
		+ "FROM pronosticos "
		+ "WHERE idParticipante = " + idParticipante;
            ResultSet rs = stmt.executeQuery(sql); //Ejecutar la consulta y obtener resultado
            
            
            System.out.println ("conectado GRUPO 4");    
            while (rs.next()) {
                //Obtener los objetos que necesito para el constructor
                Partido partido = listapartidos.getPartido(rs.getInt("idPartido"));
		Equipo equipo = listaequipos.getEquipo(rs.getInt("idEquipo"));
		// crea el objeto en memoria
                    Pronostico pronostico = new Pronostico(
			rs.getInt("idPronostico"), // El id leido de la tabla
			equipo, // El Equipo que obtuvimos de la lista
			partido, // El Partido que obtuvimos de la lista
			// El primer caracter es una comilla delimitadora de campo
			rs.getString("resultado").charAt(1) // El resultado que leimos de la tabla,
		);

		// llama al metodo add para grabar el equipo en la lista en memoria
                this.addPronostico(pronostico);
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