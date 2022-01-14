
package tps.tp1.pack3Arrays;

import java.util.Arrays;
import java.util.Scanner;

public class P01ArrayIntsInit {
    public static void main(String[] args) 
    {

        Scanner s = new Scanner(System.in);
        int[] array = new int[10] ;
        System.out.println("Introduza 10 numeros: ");

        //------------------print do array
        for(int indice = 0; indice < array.length; indice++) 
        {
            array[indice] = s.nextInt();
        }
        System.out.println(Arrays.toString(array));
        //-----------------Encontrar o maximo impar 
        int max=0 , soma = 0, impar = 0, pares = 0;
        for (int i = 0; i < array.length; i++)
        {
            if(array[i] % 2 != 0 && array[i] > max)
            {
                   max = array[i];
            }

        //Numeros pares somados com o impar a seguir 
        if (array[i] % 2 == 0)
            {
                pares = array[i];
                for (int n = array[i]; n< array.length; n++)
                {
                    if (array[n] % 3 == 0)
                    {
                        impar = array[n];
                        soma = pares + impar;
                    }
                }
                System.out.println("Numeros pares somados com o ímpar mais proximo:"+ soma);
            }


        }System.out.println("o maior número ímpar no array é: " + max);
    }

}