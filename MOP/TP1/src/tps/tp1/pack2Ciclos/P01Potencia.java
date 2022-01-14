package tps.tp1.pack2Ciclos;

import java.util.Scanner;

public class P01Potencia {
	public static void main(String args[]) {
		int num,nReps;
		Scanner numeros = new Scanner(System.in);
		System.out.println("Introduza um inteiro entre 1 e 20 -> ");
		num = numeros.nextInt();
		while(num < 0 || num > 20) {
			System.out.println("Número Inválido! Por favor escolha outro número entre 1 e 20!");
			num = numeros.nextInt();
		}
		System.out.println("Introduza o número de repetições (1..10) ->");
		nReps = numeros.nextInt();
		while(nReps < 1 || nReps > 10) {
			System.out.println("Erro! Escolha outro expoente dentro do intervalo pretendido!");
			nReps = numeros.nextInt();
		}
		int contador = 0;
		int resultado = 1;
		while(contador < nReps) {
			contador++;
			resultado = resultado * num;
		}
		String repeticao = Integer.toString(num);
		for(int i = 1; i < nReps;i++) {
			int b = num;
			repeticao = repeticao + "*" + b;
		}
		System.out.println("Potência de base " + num + " e expoente " + nReps + " = " + repeticao + " = " + resultado);	

	}
}