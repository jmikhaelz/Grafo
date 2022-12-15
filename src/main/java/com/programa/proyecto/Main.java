package com.programa.proyecto;

public class Main {

    public static void main(String[] args) {
        Grafo G = new Grafo();
        G.LeeGrafo("../Proyecto/entrada.dat");
        G.escribematriz();
        G.gradototal();
        System.out.println("\n Recorrido a lo ancho");
        G.aloancho(6);
        System.out.println("\n Recorrido a lo profundo");
        G.aloprofundo(2);
        
        G.dibujarGrafo1("Grafo");
    }

}
