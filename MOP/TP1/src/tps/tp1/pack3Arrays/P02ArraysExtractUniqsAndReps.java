package tps.tp1.pack3Arrays;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class P02ArraysExtractUniqsAndReps {
	public static void main(String args[]) {
		int [] array1 = ThreadLocalRandom.current().ints(0, 21).distinct().limit(10).toArray();
		System.out.println("Primeiro Array" + Arrays.toString(array1));
		int [] array2 = ThreadLocalRandom.current().ints(0, 21).distinct().limit(10).toArray();
		System.out.println("Segundo array" + Arrays.toString(array2));
		int [] valoresIguais = new int[10];
		int [] valoresDiferentes = new int[10];
		for (int counter = 0; counter < array1.length; counter++) {
			for(int counter2 = 0; counter2 < array2.length; counter2++) {
				if(array1[counter] == array2[counter2]) {	
					valoresIguais[counter] = array1[counter];
				}else{
					valoresDiferentes[counter] = array1[counter] + array2[counter];
				}
			}
		}System.out.println("Numeros Iguais" + Arrays.toString(valoresIguais));
		System.out.println("Numeros Diferentes" + Arrays.toString(valoresDiferentes));
	}
}
