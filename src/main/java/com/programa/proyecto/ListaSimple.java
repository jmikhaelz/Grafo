package com.programa.proyecto;

public class ListaSimple {

    public int tam = 20;
    private Nodo raiz[] = new Nodo[tam];

    public ListaSimple() {
        for (int i = 0; i < tam; i++) {
            raiz[i] = null;
        }
    }

    public void insertar(int actual, int anterior) {
        //Creamos la variable temporal para el manejo de la raices
        Nodo temp = new Nodo(actual);
        //Buscamos en que raiz(indice) esta el dato anterior
        int indice = 0;
        boolean encontrado = false;
        if (anterior != -1) {
            for (int i = 0; i < tam; i++) {
                if (raiz[i] != null) {
                    if (anterior == raiz[i].inf) {
                        indice = i;
                        encontrado = true;
                    }
                }
            }
            if (!encontrado) {
                for (int i = 0; i < tam; i++) {
                    if (raiz[i] != null) {
                        indice++;
                    }
                }
            }
            if (raiz[indice] == null) {
                raiz[indice] = temp;
                for (int i = 0; i < indice; i++) {
                    if (raiz[i] != null) {
                        Nodo aux = raiz[i];
                        while (aux != null) {
                            if (aux.inf == anterior) {
                                raiz[indice].liga = aux;
                                break;
                            } else {
                                aux = aux.liga;
                            }
                        }
                    }
                }
            } else {
                temp.liga = raiz[indice];
                raiz[indice] = temp;
            }
        } else {
            if (raiz[indice] == null) {
                raiz[indice] = temp;
            }
        }
    }

    public void informacion() {
        for (int j = 0; j < tam; j++) {
            if (raiz[j] != null) {
                System.out.println("\nRaiz no." + (j + 1));
                listado(raiz[j]);
                System.out.println("\nNo. de datos[" + Num_datos(raiz[j]) + "] \n");
            }
        }
    }

    public void listado(Nodo aux) {
        while (aux != null) {
            if (aux.liga!=null) {
                System.out.print(aux.inf + " - ");
            }
            else{
                System.out.print(aux.inf);
            }
            aux = aux.liga;
        }
    }

    public int Num_datos(Nodo aux) {
        int num = 0;
        while (aux != null) {
            aux = aux.liga;
            num++;
        }
        return num;
    }

    public int ciclos(int x, int y) {
        int cont = 0;
        for (int i = 0; i < tam; i++) {
            if (raiz[i] != null) {
                Nodo aux = raiz[i];
                boolean recorrer = false;
                while (aux != null) {
                    if ((aux.inf == x) || (aux.inf == y)) {
                        if (!recorrer) {
                            recorrer = true;
                        } else {
                            System.out.print(aux.inf);
                            recorrer = false;
                            i = tam;
                            cont++;
                        }
                    }
                    if (recorrer) {
                        cont++;
                        System.out.print(aux.inf + " - ");
                    }
                    aux = aux.liga;
                }
            }
        }
        return cont;
    }
}
