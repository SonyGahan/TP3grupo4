package com.mycompany.mavenproyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListaEquipos {
    
    // atributo
    private List<Equipo> equipos;
    private String equiposCSV;

    
    //Metodos
    
    //Constructor
    public ListaEquipos(List<Equipo> equipos, String equiposCSV) {
        this.equipos = equipos;
        this.equiposCSV = equiposCSV;
    }

    public ListaEquipos() {
        this.equipos = new ArrayList<Equipo>();
        this.equiposCSV = "equipos.csv";
    }

    
    //Getters and Setters
    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public String getEquiposCSV() {
        return equiposCSV;
    }

    public void setEquiposCSV(String equiposCSV) {
        this.equiposCSV = equiposCSV;
    }

    
    // add y remove elementos
    public void addEquipo(Equipo e) {
        this.equipos.add(e);
    }
    public void removeEquipo(Equipo e) {
        this.equipos.remove(e);
    }  
    
    /***
     * Este mÃ©todo devuelve un Equipo (o null) buscandolo por idEquipo
     * @param idEquipo Identificador del equipo deseado
     * @return Objeto Equipo (o null si no se encuentra)
     */
    public Equipo getEquipo (int idEquipo) {
        // Defini un objeto de tipo Equipo en dÃ³nde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el equipo que 
        // buscaba todavÃ­a.
        Equipo encontrado = null;
        // Recorro la lista de equipos que estÃ¡ cargada
        for (Equipo eq : this.getEquipos()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (eq.getIdEquipo() == idEquipo) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = eq;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el equipo, pueden pasar dos cosas:
        // 1- Lo encontrÃ© en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontrÃ© en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }

    //ToString
    @Override
    public String toString() {
        return "ListaEquipos{" + "equipos=" + equipos + '}';
    }

    public String listar() {
        String lista = "";
        for (Equipo equipo: equipos) {
            lista += "\n" + equipo;
        }           
        return lista;
    }
    
    // cargar desde la Base de Datos
    public void cargaDeDB()
    {
        
        
        Connection com=null;
        try { 
            //Establecer la conexion
            com = DriverManager.getConnection("jdbc:sqlite:pronosticos.db" );
            Statement stmt = com.createStatement();
            
            //String sql;
            String sql =  "Select * from equipos";
            ResultSet rs = stmt.executeQuery(sql); //Ejecutar la consulta y obtener resultado
            
            
            System.out.println ("conectado GRUPO 4");    
            while (rs.next()) {
                //Obtener los objetos que necesito para el constructor
                System.out.println(rs.getInt("idEquipo") + "\t"
                + rs.getString("Nombre") + "\t \t \t"
                + rs.getString("Descripcion") + "\t");
                
           
                int idEquipo = rs.getInt("idEquipo") ;
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                
                // crea el objeto en memoria
                Equipo equipo = new Equipo(idEquipo, nombre, descripcion);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addEquipo(equipo);

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
