package tps.tp2.pack1Recursive;

/**
 * Classe com exercícios recursivos com inteiros
 *
 */
public class P01Capicua {

	private static final int NCOLUMNS = 35;

	/**
	 * Main
	 */
	public static void main(String[] args) {

		// keep the number of errors
		int nerrors = 0;

		// getLastDigit
		System.out.println(" --- getLastDigit ---");
		nerrors += test_getLastDigit(1, 1);
		nerrors += test_getLastDigit(25, 2);
		nerrors += test_getLastDigit(359, 3);
		nerrors += test_getLastDigit(359668686, 3);

		//remLastDigit

		System.out.println("\n --- remLastDigit ---");
		nerrors += test_remLastDigit(4, 0);
		nerrors += test_remLastDigit(25, 5);
		nerrors += test_remLastDigit(359, 59);
		nerrors += test_remLastDigit(359668686, 59668686);

		// isCapicua
		System.out.println("\n --- remLastDigit ---");
		nerrors += test_isCapicua(4, true);
		nerrors += test_isCapicua(22, true);
		nerrors += test_isCapicua(23, false);
		nerrors += test_isCapicua(757, true);
		nerrors += test_isCapicua(75677, false);
		nerrors += test_isCapicua(75657, true);
		nerrors += test_isCapicua(755657, false);
		nerrors += test_isCapicua(1234554321, true);
		nerrors += test_isCapicua(1234544321, false);

		// final result
		System.out.println("\n --- Final result ---");
		System.out.println("Number of errors = " + nerrors);
	}

	/**
	 * Auxiliary method that calls getLastDigit with the received argument, shows
	 * the result and checks the returned value against expected result
	 * 
	 * @return 0, if returned value == expectedResult; 1, otherwise
	 */
	private static int test_getLastDigit(int n, int expectedResult) {
		try {

			int res = getLastDigit(n);
			String outputStr = String.format("getLastDigit (%d) = %d", n, res);
			String errorStr = res == expectedResult ? "OK" : "NOK";
			System.out.format("%-" + NCOLUMNS + "s %s%n", outputStr, errorStr);
			return res == expectedResult ? 0 : 1;

		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
			return 1;
		}
	}

	/**
	 * @param num
	 *            deve ser um número positivo (0 ou maior)
	 * 
	 * @return o dígito de maior peso do número recebido.
	 */
	public static int getLastDigit(int num) {
		
		int length = String.valueOf(num).length();
		if (num < 0)
			throw new IllegalArgumentException("Argumento de valor negativo: " + num);

		if (length <=1) {
			return num;

		}
		int x = num%10;
		num = (num - x)/10;
		return getLastDigit(num);
	}

	/**
	 * Auxiliary method that call remLastDigit with the received argument, shows
	 * the result and checks the returned value against expected result
	 * 
	 * @return 0, if returned value == expectedResult; 1, otherwise
	 */
	private static int test_remLastDigit(int n, int expectedResult) {
		try {

			int res = remLastDigit(n);
			String outputStr = String.format("remLastDigit (%d) = %d", n, res);
			String errorStr = res == expectedResult ? "OK" : "NOK";
			System.out.format("%-" + NCOLUMNS + "s %s%n", outputStr, errorStr);
			return res == expectedResult ? 0 : 1;

		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
			return 1;
		}
	}

	/**
	 * @param num
	 *            deve ser um número positivo (0 ou maior)
	 * 
	 * @return Devolve o número recebido mas sem o seu dígito de maior peso.
	 *         Caso o número seja menor que dez devolve zero.
	 */
	
	
	public static int remLastDigit(int num) {
		
		
		int lastN = 0;
		int nNum =(int) String.valueOf(num).length();
		
		
		if (num < 0)
			throw new IllegalArgumentException("Argumento de valor negativo: " + num);

		if(num<=10) return 0;
		else {
			
			int xlD =  (int) (Math.pow(10,nNum-1));
			lastN = (int) (num/xlD);
			
			return num - lastN * xlD;
		

			}

	}

	/**
	 * Auxiliary method that call isCapicua with the received argument, shows
	 * the result and checks the returned value against expected result
	 * 
	 * @return 0, if returned value == expectedResult; 1, otherwise
	 */
	private static int test_isCapicua(int n, boolean expectedResult) {
		try {

			boolean res = isCapicua(n);
			String outputStr = String.format("isCapicua (%d) = %b", n, res);
			String errorStr = res == expectedResult ? "OK" : "NOK";
			System.out.format("%-" + NCOLUMNS + "s %s%n", outputStr, errorStr);
			return res == expectedResult ? 0 : 1;

		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
			return 1;
		}
	}

	/**
	 * Verifica se o número recebido for uma capicua, ou seja, se é um número
	 * que lido da frente para trás é o mesmo número que lido em sentido
	 * inverso.
	 * 
	 * Este método deve utilizar os outros (gatLastDigit e remLastDigit) na sua
	 * implementação.
	 * 
	 * @param num
	 *            deve ser um número positivo (0 ou maior)
	 * 
	 * @return Devolve verdadeiro se o número recebido for uma capicua, e falso
	 *         se não esse o caso
	 */
	public static boolean isCapicua(int num) {
		if (num < 0)
			throw new IllegalArgumentException("Argumento de valor negativo: " + num);

		if (num >= 0) {
			int digit1 = num % 10;
			int digit2 = getLastDigit(num);
			if(digit1 == digit2) {
				int newNum = remLastDigit(num);
				newNum = newNum / 10;
				if(newNum < 10) return true;
				else return isCapicua(newNum);
			}
			else return false;
		}
		return false;
	}

}
