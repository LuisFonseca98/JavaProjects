package tps.tp2.pack1Recursive;
import java.util.Arrays;

/**
 * Classe com exercï¿½cios recursivos com arrays
 *
 */
public class P02ArraysSymmetric {

    private static final int NCOLUMNS = 50;

    /**
     * Main
     */
    public static void main(String[] args) {

        // keep the number of errors
        int nerrors = 0;

        // getLastElement
        System.out.println(" --- getLastDigit ---");
        nerrors += test_getLastElement(new char[] { 'a' }, 'a');
        nerrors += test_getLastElement(new char[] { 'a', 'b' }, 'b');
        nerrors += test_getLastElement(new char[] { 'a', 'b', 'a' }, 'a');
        nerrors += test_getLastElement(new char[] { 'a', 'b', 'c', 'h', 'k' }, 'k');

        // isSymmetric
        System.out.println("\n --- getLastDigit ---");
        nerrors += test_isSymmetric(new char[] { 'a' }, true);
        nerrors += test_isSymmetric(new char[] { 'a', 'a' }, true);
        nerrors += test_isSymmetric(new char[] { 'a', 'c' }, false);
        nerrors += test_isSymmetric(new char[] { 'a', 'b', 'a' }, true);
        nerrors += test_isSymmetric(new char[] { 'a', 'b', 'c' }, false);
        nerrors += test_isSymmetric(new char[] { 'a', 'b', 'c', 'd' }, false);
        nerrors += test_isSymmetric(new char[] { 'a', 'b', 'c', 'd', 'd', 'c', 'b', 'a' }, true);
        nerrors += test_isSymmetric(new char[] { 'a', 'b', 'c', 'd', 'e', 'c', 'b', 'a' }, false);

        // final result
        System.out.println("\n --- Final result ---");
        System.out.println("Number of errors = " + nerrors);
    }

    /**
     * Auxiliary method that calls getLastElement with the received argument,
     * shows the result and checks the returned value against expected result
     *
     * @return 0, if returned value == expectedResult; 1, otherwise
     */
    private static int test_getLastElement(char[] array, char expectedResult) {
        try {

            char res = getLastElement(array);
            String outputStr = String.format("getLastElement (%s) = %c", Arrays.toString(array), res);
            String errorStr = res == expectedResult ? "OK" : "NOK";
            System.out.format("%-" + NCOLUMNS + "s %s%n", outputStr, errorStr);
            return res == expectedResult ? 0 : 1;

        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
            return 1;
        }
    }

    /**
     * Devolve, de forma recursiva, o último elemento de um array, o elemento de
     * maior índice. Pode aceder ao elemento do array de índice 0, array[0].
     * Pode utilizar o método Arrays.copyOfrange.
     *
     * @param array
     *            deve ser um array com pelo menos um elemento
     * @return o elemento na maior posiï¿½ï¿½o do array
     */
    public static char getLastElement(char[] array) {
        if (array == null) throw new IllegalArgumentException("The received array is null");
        
        if(array.length != 1) {
        	char [] newArray = Arrays.copyOfRange(array, 1, array.length);
        	return getLastElement(newArray);
        }else {
        	return array[0];
        }
    }


    /**
     * Auxiliary method that calls isSymmetric with the received argument, shows
     * the result and checks the returned value against expected result
     *
     * @return 0, if returned value == expectedResult; 1, otherwise
     */
    private static int test_isSymmetric(char[] array, boolean expectedResult) {
        try {

            boolean res = isSymmetric(array);
            String outputStr = String.format("isSymmetric (%s) = %b", Arrays.toString(array), res);
            String errorStr = res == expectedResult ? "OK" : "NOK";
            System.out.format("%-" + NCOLUMNS + "s %s%n", outputStr, errorStr);
            return res == expectedResult ? 0 : 1;

        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
            return 1;
        }
    }

    /**
     * Devolve verdadeiro se o array recebido for simï¿½trico, ou seja, ï¿½ um array
     * idï¿½ntico se lido do inï¿½cio para o fim ou em sentido inverso. Em termos de
     * acesso ao seu conteï¿½do, o mï¿½todo sï¿½ pode aceder ï¿½ primeira posiï¿½ï¿½o, com
     * array[0], e ï¿½ ï¿½ltima com getLastElement. O mï¿½todo pode utilizar o mï¿½todo
     * Arrays.copyOfRange e array.length para saber a dimensï¿½o do array.
     *
     * @param array
     *            nï¿½o nulo, mas que pode ser vazio
     *
     * @return true se o array for simï¿½trico em termos do seu conteï¿½do
     */

    public static boolean isSymmetric(char[] array) {
        if (array == null) throw new IllegalArgumentException("The received array is null");

        char lastElement = getLastElement(array);

        if(array.length == 1 || (array.length == 2 && array[0] == lastElement)){
           return true;
        }
        else if (lastElement == array[0]){
            char[] array1 = Arrays.copyOfRange(array, 1, array.length-1);
            return isSymmetric(array1);
		}
        return false;
    }
}
