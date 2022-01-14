package tps.tp1.pack1Decisoes;

import java.util.Scanner;

public class P03OpTernário {
	public static void main(String args[]) {
		Scanner elementos = new Scanner(System.in);
		System.out.println("Insira os 5 elementos da Teoria Taoista (Fire, Earth, Metal, Water, e Wood)");
		String text = elementos.next(); 
		if(text.equalsIgnoreCase("Fire") || text.equalsIgnoreCase("Earth") || text.equalsIgnoreCase("Metal") || 
				text.equalsIgnoreCase("Water") || text.equalsIgnoreCase("Wood")) {
			System.out.println(text.equalsIgnoreCase("Fire")? text + " generates Earth" : "");
			System.out.println(text.equalsIgnoreCase("Earth")? text + " generates Metal" : "");
			System.out.println(text.equalsIgnoreCase("Metal")? text + " generates Water" : "");
			System.out.println(text.equalsIgnoreCase("Water")? text + " generates Wood" : "");
			System.out.println(text.equalsIgnoreCase("Wood")? text +  " generates Fire" : "");

		}else {
			System.out.println("Foi inserido um elemento diferente dos pedidos,o programa irá encerrar...");
		}
	}
}

