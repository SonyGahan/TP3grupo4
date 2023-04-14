/*
 Para entrega 3 Grupo 4
            Creado por Sonia Pereira
                       Diego Lascano
                       Ernesto Floridia
                       Fredy Muñoz
 */

package com.mycompany.mavenproyecto;


public class TP3Grupo4 {
    public static PronosticoDeportivo PRODE;
    
   
    public static void main(String[] args) {
        System.out.println ("Sistema de simulacion de pronosticos deportivos.");
        
        PRODE = new PronosticoDeportivo();

        PRODE.play();
    }
}
