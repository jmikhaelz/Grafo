package com.programa.proyecto;
public class Pila{
        int tope, tam, dato[];

        public Pila(){
            tope = 0;
            tam = 0;
        }

        public Pila(int t){
            tope = 0;
            tam = t;
            dato = new int[t];
            for (int i = 0; i < tam; i++) {
                dato[i]=0;
            }
        }

        public void Push(int x){
            dato[tope] = x;
            tope++;
        }

        public int Pop(){
            tope--;
            int aux = dato[tope];
            dato[tope]=0;
            return aux;
        }

        public int Vacia(){
            if(tope == 0)   return 1;
            else    return 0;
        }

        public void Vaciar(){
            for (int i = 0; i < tope; i++) {
                Pop();
                dato[i] = 0;
            }
        }

        public int Llena(){
            if(tope == tam)   return 1;
            else    return 0;
        }

        public void Mostrar(){
            for (int i = 0; i <= tope-1; i++) {
                System.out.println("\t¦ "+i+" ¦ ["+dato[i]+"]");
            }
        }

        public int RevTam(Pila inf){
                if (inf.tam == tam) return tam;
                else return 0;
        }

        public int RevTope(Pila inf){
            if (inf.tope == tope) return tam;
            else return 0;
        }
}
