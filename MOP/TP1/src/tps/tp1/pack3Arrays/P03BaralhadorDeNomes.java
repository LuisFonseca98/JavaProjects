package tps.tp1.pack3Arrays;

import java.util.*;

public class P03BaralhadorDeNomes{
	public static void main (String arg[]){
		String names[] = new String[10];

		System.out.println("Digite 10 nomes completos (no minimo 5): ");

		Scanner userInput1 = new Scanner(System.in);
		int contador = 0;
		int now = 0;
		for (int i = 0; i < 10; i ++){
			while (true){
				contador = 0;
				names[i] = userInput1.nextLine();
				if (names[i].length() <= 120){
					if (names[i].equalsIgnoreCase("fim")){
						if (i >= 5){
							now = i;
							i = 10;
							break;
						}else
							System.out.println("É necessario introduzir pelo menos 5 nomes");
					}

					if (names[i].split(" ").length >= 2){
						for (int a = 0; a < names[i].split(" ").length; a ++){
							if (names[i].split(" ")[a].length() >= 4){
								contador += 1;
							}
						}
						if (contador == names[i].split(" ").length){
							now = i;
							break;
						}else{
							int contador1 = 0;
							for (int a = 0; a < names[i].split(" ").length; a ++)
							{
								if (names[i].split(" ")[a].length() > 4)
									contador1 += 1;
							}
							if (contador >= 2)
							{
								System.out.println("Nome(s) com menos de 4 caracteres descartados");
								break;
							}else
								System.out.println("ERROR: Nome deve conter pelo menos 4 caracteres");
						}
					}else
						System.out.println("ERROR: Têm que ser introduzidos pelo menos 2 nomes");
				}else
					System.out.println("ERROR: Nome(s)com o maximo de 120 caracteres");

			}
		}


		// Coloca os nomes que foram introduzidos no array
		System.out.print("\nNomes Introduzidos: [");
		for (int b = 0; b < now; b ++)
		{
			if (b != now - 1)
				System.out.print("\"" + names[b] + "\"" + ", ");
			else
				System.out.print("\"" + names[b] + "\"");
		}
		System.out.println("] \n");

		String firstNames[] = new String [now];
		String lastNames[] = new String [now];

		boolean outOfnames = false;

		for (int i = 0; i < now; i ++)
		{
			outOfnames = false;

			//Procura pelo primeiro e ultimo nome 
			for (int a = 0; a < names[i].split(" ").length; a++)
			{
				if (names[i].split(" ")[a].length() >= 4 && !outOfnames)
				{
					firstNames[i] = names[i].split(" ")[a];
					outOfnames = true;
				}
				else if (names[i].split(" ")[a].length() >= 4 && outOfnames)
				{
					for (int b = names[i].split(" ").length - 1; b > 0; b ++)
					{
						if (names[i].split(" ")[b].length() >= 4)
						{
							lastNames[i] = names[i].split(" ")[a];
							break;
						}
					}
				}
			}	
		}

		System.out.println("Primeiros nomes de cada nome: " + Arrays.toString(firstNames) + "\n");
		System.out.println("Ultimos nomes de cada nome: " + Arrays.toString(lastNames) + "\n");

		System.out.println("Todos os nomes introduzidos: " + Arrays.toString(GatherNames(firstNames, lastNames)) + "\n");


		Combinations (firstNames, lastNames);


	}

	public static String[] GatherNames (String[] firstNames, String[] lastNames)
	{
		int newSize = firstNames.length + lastNames.length;

		String allNames[] = new String [newSize];

		for (int i = 0; i < firstNames.length; i ++)
		{
			allNames[i] = firstNames[i];
		}
		for (int i = 0; i < lastNames.length; i ++)
		{
			allNames[i + 9] = lastNames[i];
		}

		return allNames;

	}

	public static void Combinations (String[] firstNames, String[] lastNames)
	{
		String newArray [] = new String[firstNames.length];

		int randmz = 0;
		boolean fazer = false;
		int contador = 0;
		int storage [] = new int[newArray.length];

		for (int i = 0; i < newArray.length; i ++)
		{
			fazer = false;
			while (!fazer)
			{
				randmz = randNumber (newArray.length - 1);
				contador = 0;

				// Se for indice 0
				if (randmz != i && i == 0)
				{
					newArray[i] = firstNames[i] + " " + lastNames[randmz];
					storage[i] = randmz;
					fazer = true;
				}

				// Se nao for 0
				if (randmz != i)
				{
					for (int a = 0; a < i; a ++)
					{
						if (storage[a] != randmz)
						{
							contador += 1;
						}
					}

					if (contador == i)
					{
						newArray[i] = firstNames[i] + " " + lastNames[randmz];
						storage[i] = randmz;
						fazer = true;
					}
				}
			}
		}

		System.out.println("Resultado Final do Baralhador de Nomes: " + Arrays.toString(newArray));
	}

	public static int randNumber (int max)
	{
		int generated = 0;
		max += 1;

		Random rand = new Random();
		generated = rand.nextInt(max);

		return generated;
	}
}