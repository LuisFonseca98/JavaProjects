package tps.tp1.pack1Decisoes;

import java.util.Scanner;

public class P04Switch {
	private static final int Fire = 0;
	private static final int Earth = 1;
	private static final int Wood = 2;
	private static final int Metal = 3;
	private static final int Water = 4;

	public static void main(String args[]) {
		System.out.println("Escolha um dos seguintes elementos: 0->Fire, " +
							"1->Earth" + ", 2->Wood" + ", 3->Metal" + ", 4 ->Water");
		Scanner keyboard = new Scanner(System.in);
		String element = null;
		String elemGenerated = null;
		String elemDestroyed = null;
		int escolha = keyboard.nextInt();
		if((escolha < Fire) || (escolha > Water)){
			System.out.println("Escolheu um elemento, que não era pretendido " +
					"o programa irá encerrar...");
			keyboard.close();
			System.exit(0);
		}
		switch(escolha) {
		case Fire:
			element = "Fire";
			elemGenerated = "Earth";
			elemDestroyed = "Metal";
			break;
		case Earth:
			element = "Earth";
			elemGenerated = "Metal";
			elemDestroyed = "Water";
			break;
		case Wood:
			element = "Wood";
			elemGenerated = "Fire";
			elemDestroyed = "Earth";
			break;
		case Metal:
			element = "Metal";
			elemGenerated = "Water";
			elemDestroyed = "Wood";
			break;
		case Water:
			element = "Water";
			elemGenerated = "Wood";
			elemDestroyed = "Fire";
			break;
		}
		System.out.println(element + " generates " + elemGenerated + " and " + element + " destroys " + elemDestroyed);
		keyboard.close();
	}

}
