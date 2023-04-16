package com.mycompany.mavenproyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ListaParticipantes {
    //Atributos
    private List<Participante> participantes;
    private String participantesCSV;
    
    
    //Metodos
    
    //Constructor  
    public ListaParticipantes(List<Participante> participantes, String participantesCSV) {
        this.participantes = participantes;
        this.participantesCSV = participantesCSV;
    }
    
    public ListaParticipantes() {
        this.participantes = new ArrayList<Participante>();
        this.participantesCSV = "participantes.csv";
    }
    
    
    //Getters and Setters
    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public String getParticipantesCSV() {
        return participantesCSV;
    }

    public void setParticipantesCSV(String participantesCSV) {
        this.participantesCSV = participantesCSV;
    }
    
    
    // add y remove elementos
    public void addParticipante(Participante p) {
        this.participantes.add(p);
    }
    public void removeParticipante(Participante p) {
        this.participantes.remove(p);
    }
    
    
    /***
     * Este metodo devuelve un objeto Participante (o null) buscandolo por idParticipante
     * @param idParticipante Identificador del participante deseado
     * @return Objeto Participante (o null si no se encuentra)
     */
    public Participante getParticipante (int idParticipante) {
        // Defini un objeto de tipo Participante en donde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el participante que 
        // buscaba todavi­a.
        Participante encontrado = null;
        // Recorro la lista de participantes que esta cargada
        for (Participante eq : this.getParticipantes()) {
            // Para cada participante obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (eq.getIdParticipante() == idParticipante) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = eq;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el participante, pueden pasar dos cosas:
        // 1- Lo encontro en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontro en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }
    
    
    //ToString - revisar
    @Override
    public String toString() {
        return "ListaParticipantes{" + "participantes=" + participantes + ", participantesCSV=" + participantesCSV + '}';
    }
    
    public String listar() {
        String lista = "";
        for (Participante participante: participantes) {
            lista += "\n" + participante;
        }           
        return lista;
    }
    
    // cargar desde la Base de Datos
    public void cargaDeDB() {     
        
        Connection com=null;
        try { 
            //Establecer la conexion
            com = DriverManager.getConnection("jdbc:sqlite:pronosticos.db" );
            Statement stmt = com.createStatement();
            
            //String sql;
            String sql =  "Select * from participantes";
            ResultSet rs = stmt.executeQuery(sql); //Ejecutar la consulta y obtener resultado
            System.out.println ("conectado GRUPO 4");    
            
            while (rs.next()) {
                //levanta los datos de cada fila
                System.out.println(rs.getInt("idParticipante") + "\t"
                + rs.getString("Nombre") + "\t \t \t");
                        
                int idParticipante = rs.getInt("idParticipante") ;
                String nombre = rs.getString("Nombre");
                
                // crea el objeto en memoria
                Participante participante = new Participante(idParticipante, nombre);
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addParticipante(participante);

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
