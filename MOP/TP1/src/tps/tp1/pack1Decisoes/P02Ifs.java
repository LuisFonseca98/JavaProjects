package tps.tp1.pack1Decisoes;

import java.util.Scanner;
public class P02Ifs {
	public static void main (String args[]) {
		System.out.println("Insira três números inteiros");
		int menor,maior,meio,n1,n2,n3;
		Scanner numero = new Scanner(System.in);
		n1 = numero.nextInt();
		n2 = numero.nextInt();
		n3 = numero.nextInt();
	if (n1>n2) {
		maior = n1;
		if(n1>n3) {
		maior = n1;
			if(n3>n2) {
				meio = n3;
				menor = n2;
				System.out.print("O maior valor é "+ maior + ", o valor do meio é " + meio + " e o menor valor é " + menor);
			}
			else {
				meio = n2;
				menor = n3;
				System.out.print("O maior valor é "+ maior + ", o valor do meio é " + meio + " e o menor valor é " + menor);
			}
			}
		else {
			maior = n3;
			menor = n2;
			meio = n1;
			System.out.print("O maior valor é "+ maior + ", o valor do meio é " + meio + " e o menor valor é " + menor);
		}
		}
	if (n1<n2) {
		maior = n2;
		if(n2>n3) {
		maior = n2;
			if(n3>n1) {
				meio = n3;
				menor = n1;
				System.out.print("O maior valor é "+ maior + ", o valor do meio é " + meio + " e o menor valor é " + menor);
			}
			else {
				meio = n1;
				menor = n3;
				System.out.print("O maior valor é "+ maior + ", o valor do meio é " + meio + " e o menor valor é " + menor);
			}
			}
		else {
			maior = n3;
			menor = n1;
			meio = n2;
			System.out.print("O maior valor é "+ maior + ", o valor do meio é " + meio + " e o menor valor é " + menor);
		}
		}
	}
}