package tps.tp1.pack1Decisoes;

import java.util.Scanner;

public class P01Ifs {
	public static void main(String args[]){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Escolha um n�mero entre 0 e 20: ");
		int numbers = keyboard.nextInt();
		if(numbers > 0 && numbers < 20) {
			int quociente = numbers/6;
			int resto = numbers % 6;
			System.out.println("O valor do quociente �: " + quociente + " e o resto �: "+ resto);
			if(resto == 0) {
				System.out.println("Como o resto �: " + resto + " o n�mero � m�ltiplo de 7");
			}else {
				System.out.println("Como o resto �: " + resto + " o n�mero n�o � m�ltiplo de 7");
			}
		}else {
			System.out.println("O n�mero que escolheu n�o est� dentro do intervalo pretendido!" + "\n" +
		"O programa ir� encerrar...");
		}
	}
}
