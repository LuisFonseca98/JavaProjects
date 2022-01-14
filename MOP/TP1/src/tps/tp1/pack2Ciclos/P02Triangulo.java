package tps.tp1.pack2Ciclos;

import java.util.Collections;
import java.util.Scanner;

public class P02Triangulo {
	public static void main (String args[]) {
		System.out.print("Introduza o nº inteiro inicial de cardinais: ");
		Scanner numero = new Scanner(System.in);
		int n,n1;
		n1 = numero.nextInt();
		while(n1 < 0 || n1 > 21) {
			System.out.print("Erro! O número escolhido está fora do intervalo pedido, por favor tente outra vez!");
			n1 = numero.nextInt();
		}
		
		while(n1 < 0 || n1 > 21) {
			System.out.print("Introduza o nº inteiro inicial de cardinais: ");
			n1 = numero.nextInt();
		}

		for(int i=1; i<=n1; i++){
			n = n1 - i;
			System.out.print(String.join("", Collections.nCopies(n, " ")));
			System.out.println(String.join("", Collections.nCopies(i, "#"))+" - " + i);

		}
	}
}