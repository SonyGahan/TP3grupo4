package com.mycompany.mavenproyecto;


public class PronosticoDeportivo {
    private ListaEquipos equipos;

    public PronosticoDeportivo() {
        equipos = new ListaEquipos();
    }

    public void play(){
        // cargar y listar los equipos
        equipos.cargaDeDB();
        System.out.println("Los equipos cargados son: " + equipos.listar());
        
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
