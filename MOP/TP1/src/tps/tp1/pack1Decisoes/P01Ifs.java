package tps.tp1.pack1Decisoes;

import java.util.Scanner;

public class P01Ifs {
	public static void main(String args[]){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Escolha um número entre 0 e 20: ");
		int numbers = keyboard.nextInt();
		if(numbers > 0 && numbers < 20) {
			int quociente = numbers/6;
			int resto = numbers % 6;
			System.out.println("O valor do quociente é: " + quociente + " e o resto é: "+ resto);
			if(resto == 0) {
				System.out.println("Como o resto é: " + resto + " o número é múltiplo de 7");
			}else {
				System.out.println("Como o resto é: " + resto + " o número não é múltiplo de 7");
			}
		}else {
			System.out.println("O número que escolheu não está dentro do intervalo pretendido!" + "\n" +
		"O programa irá encerrar...");
		}
	}
}
