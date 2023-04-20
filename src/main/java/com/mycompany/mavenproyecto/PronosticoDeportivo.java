package com.mycompany.mavenproyecto;

import de.vandermeer.asciitable.AsciiTable;


public class PronosticoDeportivo {
    private int idParticipante;
    private ListaEquipos equipos;
    private ListaPartidos partidos;
    private ListaParticipantes participantes;
    private ListaPronosticos pronosticos;
    
    
    
    public PronosticoDeportivo() {
        equipos = new ListaEquipos();
        partidos = new ListaPartidos();
        participantes = new ListaParticipantes();
        pronosticos = new ListaPronosticos();
        //Realmente no llego a saber si tengo que crear estos constructores aqui, entiendo q solo equipos esta bien
    }

    public void play(){
        // cargar y listar los equipos
        equipos.cargaDeDB();
        // una vez cargados los equipos, para cada uno de ellos muestro el nombre
        AsciiTable tablaequipo = new AsciiTable();
        tablaequipo.addRule();
        tablaequipo.addRow("NOMBRE", "DESCRIPCION");
        tablaequipo.addRule();
        for(Equipo e: equipos.getEquipos()){
            tablaequipo.addRow(e.getNombre(), e.getDescripcion());
        }
        tablaequipo.addRule();
        System.out.println(tablaequipo.render());
        
        
        // cargar y listar los partidos
        partidos.cargaDeDB(equipos);
        // una vez cargados los partidos, para cada uno de ellos muestro el nombre
        AsciiTable tablapartido = new AsciiTable();
        tablapartido.addRule();
        tablapartido.addRow("EQUIPO1", "GOLES", "EQUIPO2", "GOLES");
        tablapartido.addRule();
        for(Partido p: partidos.getPartidos()){
            tablapartido.addRow(p.getEquipo1(), p.getGolesEquipo1(), p.getEquipo2(), p.getGolesEquipo2());
        }
        tablapartido.addRule();
        System.out.println(tablapartido.render());
        
        
        
        
        // cargar y listar los participantes
        participantes.cargaDeDB();
        // una vez cargados los participantes, para cada uno de ellos cargo pronostico
        for (Participante p: participantes.getParticipantes()){
            p.cargarPronosticos(equipos, partidos);
        }
        
        AsciiTable tabla = new AsciiTable();
        tabla.addRule();
        tabla.addRow("PARTICIPANTE", "PUNTAJE");
        tabla.addRule();
        for(Participante p: participantes.getParticipantes()){
            tabla.addRow(p.getNombre(), p.getPuntaje());
        }
        tabla.addRule();
        System.out.println(tabla.render());
        
        AsciiTable tabla2 = new AsciiTable();
        tabla2.addRule();
        tabla2.addRow("PARTICIPANTE", "PUNTAJE (ORDENADO)");
        tabla2.addRule();
        for(Participante p: participantes.getOrdenadosPorPuntaje()){
            tabla2.addRow(p.getNombre(), p.getPuntaje());
        }
        tabla2.addRule();
        System.out.println(tabla.render());
        
        

        // cargar y listar los pronosticos
        pronosticos.cargaDeDB(idParticipante,equipos, partidos);
        // una vez cargados los pronosticos, los muestro
        AsciiTable tablapronosticos = new AsciiTable();
        tablapronosticos.addRule();
        tablapronosticos.addRow("EQUIPOS", "PARTIDOS", "RESULTADO");
        tablapronosticos.addRule();
        for(Pronostico p: pronosticos.getPronosticos()){
            tablapartido.addRow(p.getEquipo(), p.getPartido(), p.getResultado());
        }
        tablapronosticos.addRule();
        System.out.println(tablapronosticos.render());
 
        
        System.out.println ("Buscando el equipo");
        int idEquipo = 17;
        Equipo eq = equipos.getEquipo(idEquipo);
        if (eq != null) {
            System.out.println (eq);
        } else {
            System.out.println ("No se encontro el equipo...");
        }
    }   
}
