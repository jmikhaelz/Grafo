package com.programa.proyecto;
public class Cola
{
	int entrada, salida,tam;
	int C[]= new int[100];
	public Cola(int t) // constructor de la clase
	{
		tam=t;
		entrada=salida=0;
                C = new int[t];
                for (int i = 0; i < tam; i++) {
                    C[i]=0;
                }
	}
	public void insertar(int dato) // insertar una persona
	{
		C[entrada]=dato;
		entrada++;
	}
	public int extraer() // extraer una persona
	{
		int n;
		n=C[salida];
		salida++;
		return(n);
	}
	public int vacia() // verificar si esta vacia la cola
	{
		if(entrada==salida) return 1;
		else return 0;
	}
	public int llena() // verificar si esta llena la cola
	{
		if(entrada==tam) return 1;
		else return 0;
	}
	public void muestra() // muestra los datos de la cola
	{
	String datos="Datos de la COLA\n";
            for (int i = salida; i < entrada; i++) {
                System.out.println("\t¦ "+i+" ¦ ["+C[i]+"]");
            }
	}
			    
}
