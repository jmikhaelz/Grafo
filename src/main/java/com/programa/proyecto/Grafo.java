package com.programa.proyecto;

import java.io.*;
import java.util.StringTokenizer;

public class Grafo {

    private int NN, aristas, origen[] = new int[100], destino[] = new int[100],  M[][] = new int[20][20];

    public void escribematriz() {
        int i, j;
        System.out.println(" Matriz de Adyacencias");
        System.out.print(" # |\t1");
        for (i = 2; i <= NN; i++) {
            System.out.print(" " + i);
        }
        System.out.println("\t");
        System.out.println();
        for (i = 1; i <= NN; i++) {
            System.out.print(" " + i + " |\t");
            for (j = 1; j <= NN; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void LeeGrafo(String arch) //Lee archivo con los datos del grafo
    {
        FileInputStream fp;
        DataInputStream f;
        String linea = null;
        int token1, token2, i, j;
        aristas = 0;
        try {
            fp = new FileInputStream(arch);
            f = new DataInputStream(fp);
            linea = f.readLine();

            NN = Integer.parseInt(linea);
            System.out.println(" Numero de Nodos: " + NN);
            // Inicializamos la matriz con ceros
            for (i = 1; i <= NN; i++) {
                for (j = 1; j <= NN; j++) {
                    M[i][j] = 0;
                }
            }

            // Leemos el archivo linea por linea
            do {
                linea = f.readLine();
                if (linea != null) {
                    StringTokenizer tokens = new StringTokenizer(linea);
                    token1 = Integer.parseInt(tokens.nextToken());
                    token2 = Integer.parseInt(tokens.nextToken());
                    // escribimos en pantalla los datos leidos transformados en numeros
                    System.out.println("\t" + token1 + " , " + token2);
                    origen[aristas] = token1;
                    destino[aristas] = token2;
                    aristas++;
                    // almacenamos en la matriz
                    M[token1][token2] = 1;
                    M[token2][token1] = 1;
                }
            } while (linea != null);
            fp.close();
        } catch (FileNotFoundException exc) {
            System.out.println("El archivo " + arch + " no fue encontrado ");
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public void aloancho(int inicio) {
        GraphViz gv1 = new GraphViz();
        gv1.addln(gv1.start_graph());

        Cola C = new Cola(100);
        int[] visitados = new int[NN + 1];
        int n, j, padre;
        String cad;

        for (j = 0; j <= NN; j++) {
            visitados[j] = 0;
        }
        C.insertar(inicio);
        visitados[inicio] = 1;
        int cont = 1;
        System.out.print("R_A = { ");
        while (C.vacia() != 1) {
            n = C.extraer();
            if (cont != NN) {
                System.out.print(n + " -> ");
                cont++;
            } else {
                System.out.print(n);
            }
            for (j = 1; j <= NN; j++) {
                if (M[n][j] != 0) {
                    if (visitados[j] == 0) {
                        C.insertar(j);
                        cad = n + "->" + j;
                        gv1.addln(cad);
                        visitados[j] = 1;
                    }
                }
            }
        }
        System.out.print(" }");
        gv1.addln(gv1.end_graph());
//            System.out.println(gv1.getDotSource());
        String type = "jpg";
        File out = new File("recAloAncho_" + inicio + "." + type);    // Windows
        gv1.writeGraphToFile(gv1.getGraph(gv1.getDotSource(), type), out);
    }

    public void aloprofundo(int inicio) {
        if ((inicio > 0) && (inicio <= NN)) {//Revisamos si existe el numero ingresado
            //Inicializamos para crear el dibujo del grafo
            GraphViz gv1 = new GraphViz();
            gv1.addln(gv1.start_graph());
            String cad = null;
            //Creamos variables y objetos para el manejo de los datos
            ListaSimple profundo = new ListaSimple();
            ListaSimple ciclos = new ListaSimple();
            Pila p = new Pila(100);
            int[] visitados = new int[NN + 1];
            for (int j = 0; j <= NN; j++) {
                visitados[j] = 0;
            }
            //Inicializamos el contador y agreamos el primer dato a evaluar
            int cont = 1;
            p.Push(inicio);
            profundo.insertar(inicio, -1);
            int raiz = 0;

            //Variables auxiliares
            int actual = 0, anterior = 0;

            System.out.print("R_P = { ");

            while (p.Vacia() != 1) {
                //Sacamos dato que se va evaluar
                actual = p.Pop();
                if (visitados[actual] == 0) { //Si no es visitado
                    visitados[actual] = 1; //Lo marcamos visitado

                    if (cont != NN) { //Revisamos que no sea el ultimo dato a revisar
                        System.out.print(actual + " -> ");
                        cont++;
                    } else { //si es el ultimo solo lo imprimimos sin la flecha
                        System.out.print(actual);
                    }
                    //Se agreagan a la pila los adyacentes del numero actual
                    for (int j = NN; j >= 1; j--) {
                        if (M[actual][j] != 0) {
                            p.Push(j);
                        }
                    }
                    //Revisamos variable anterior tiene algo
                    if (anterior != 0) {
                        //Revisamos la relacion de los numeros anterior y actual
                        if (M[actual][anterior] == 1) {
                            cad = anterior + "->" + actual;
                            gv1.addln(cad);
                            M[actual][anterior]++;
                            M[anterior][actual]++;
                            profundo.insertar(actual, anterior);
                        } else {
                            for (int j = NN; j >= 1; j--) {
                                if (M[j][actual] == 1) {
                                    if (visitados[j] == 1) {
                                        cad = j + "->" + actual;
                                        gv1.addln(cad);
                                        M[actual][j]++;
                                        M[j][actual]++;
                                        profundo.insertar(actual, j);
                                        j = 1;
                                    }
                                }
                            }
                        }
                    }
                    //Limpiamos variable de cadena
                    cad = "";
                    //Pasamos la variable actual a que sea anterior
                    anterior = actual;
                }
            }
            System.out.print(" } \n");
            profundo.informacion();
//            System.out.println("\n");
//            escribematriz();
            //Revisar ciclos
            int c = 1, total = 0, impar = 0, par = 0;
            for (int x = NN; x >= 1; x--) {
                for (int y = NN; y >= 1; y--) {
                    if (M[x][y] == 1) {
                        System.out.println("\nCiclo No." + c + " del " + x + " a " + y + ": ");
                        total = profundo.ciclos(x, y);
                        System.out.print("\nNo. de datos[" + total + "]");
                        if (total % 2 == 0) {
                            System.out.print(" (Par) \n");
                            par++;
                        } else {
                            System.out.print(" (Impar) \n");
                            impar++;
                        }
                        if (x == inicio) {
                            cad = y + "->" + x;
                        } else {
                            cad = x + "->" + y;
                        }
                        gv1.addln(cad);
                        M[x][y]++;
                        M[y][x]++;
                        c++;
                    }
                }
            }
//            escribematriz();
            if (par == c) {
                System.out.println("\n<Grafo> Es Bipartito");
            } else {
                System.out.println("\n<Grafo> No es Bipartito");
            }
            gv1.addln(gv1.end_graph());
            String type = "jpg";
            File out = new File("recAloProfundo_" + inicio + "." + type);    // Windows
            gv1.writeGraphToFile(gv1.getGraph(gv1.getDotSource(), type), out);
            System.out.println("\n");

        } else {
            System.out.println("<ERROR> -No se puede ingresar el nodo inicial como menor o igual 0, o mayor de " + NN);
        }
    }

    public void gradototal() {
        int entrada = 0, salida = 0, contv = 0, cont = 0;

        System.out.println("Grado de entrada");
        System.out.print("#\t");
        for (int i = 1; i <= NN; i++) {
            System.out.print(" " + i);
        }
        System.out.println("\t");
        System.out.println();
        for (int i = 1; i <= NN; i++) {
            System.out.print(" " + i + " |\t");
            for (int j = 1; j <= NN; j++) {
                System.out.print(M[j][i] + " ");
                if (M[j][i] == 1) {
                    entrada++;
                    contv++;
                }
            }
            System.out.print("\t E:" + contv + "\n");
            contv = 0;
        }
        System.out.println("Gradao Total de Entrada: " + entrada);
        System.out.println("-----------------------------");
        System.out.println("Grado de salida");
        System.out.print("#\t");
        for (int i = 1; i <= NN; i++) {
            System.out.print(" " + i);
        }
        System.out.println("\t");
        System.out.println();
        for (int i = 1; i <= NN; i++) {
            System.out.print(" " + i + " |\t");
            for (int j = 1; j <= NN; j++) {
                System.out.print(M[i][j] + " ");
                if (M[i][j] == 1) {
                    salida++;
                    contv++;
                }
            }
            System.out.print("\t S:" + contv + "\n");
            contv = 0;
        }

        System.out.println("Gradao Total de Salida: " + salida);
        System.out.println("-----------------------------");

        System.out.println("Grado de Total");
        System.out.print("#\t");
        for (int i = 1; i <= NN; i++) {
            System.out.print(" " + i);
        }
        System.out.println("\t");
        System.out.println();
        for (int i = 1; i <= NN; i++) {
            System.out.print(" " + i + " |\t");
            for (int j = 1; j <= NN; j++) {
                if (M[j][i] == 1) {
                    System.out.print(M[j][i] + " ");
                    contv++;
                    cont++;
                } else if (M[i][j] == 1) {
                    contv++;
                    cont++;
                    System.out.print(M[i][j] + " ");
                } else {
                    System.out.print(M[j][i] + " ");
                }
            }
            System.out.print("\t T:" + contv + "\n");
            contv = 0;
        }
        System.out.println("-----------------------------");
        System.out.println("Grado Total: " + cont);
    }

    public void dibujarGrafo1(String nombre) {
        GraphViz gv = new GraphViz();
        String cad;
        gv.addln(gv.start_NOgraph());
        for (int i = 0; i < aristas; i++) {
            cad = this.origen[i] + "--" + destino[i];
            gv.addln(cad);
        }
        gv.addln(gv.end_graph());

        String type = "jpg";
        File out = new File("Garfo_" + nombre + "." + type);    // Windows
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }
}
