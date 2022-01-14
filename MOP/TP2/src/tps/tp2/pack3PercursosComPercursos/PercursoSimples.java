package tps.tp2.pack3PercursosComPercursos;

/**
 * Classe que suporta um percurso simples
 * 
 * ATEN��O: esta classe deve ser substitu�da pela classe feita por v�s no
 * package 2. Aqui n�o � necess�rio fazer nada (se ela j� estiver feita).
 *
 */
public class PercursoSimples {

	/**
	 * nome do percurso, deve conter s� letras, digitos e espa�os, deve come�ar
	 * por uma letra e ter pelo menos mais uma letra ou d�gito
	 */
	private String nome;

	/**
	 * Nome do ponto de in�cio do percurso
	 */
	private String inicio;

	/**
	 * Nome do ponto de fim do percurso
	 */
	private String fim;

	/**
	 * Dist�ncia em metros do percurso
	 */
	private int distancia;

	/**
	 * Declive em metros, positivo, se fim mais alto que in�cio
	 */
	private int declive;

	/**
	 * Deve validar o nome, inicio e fim com o m�todo de valida��o
	 * validarNomeDelocal. A dist�ncia tem de ser positiva (maior que 0). Em
	 * caso de argumentos inv�lidos deve ser lan�ada a excep��o
	 * IllegalArgumentException com uma mensagem a indicar o erro ocorrido e o
	 * argumento inv�lido.
	 * 
	 * @param nome
	 *            Nome do percurso
	 * @param inicio
	 *            Local do in�cio do percurso
	 * @param fim
	 *            Local de im do percurso
	 * @param distancia
	 *            Distancia do percurso
	 * @param declive
	 *            Declive do percurso
	 */
	public PercursoSimples(String nome, String inicio, String fim, int distancia, int declive) {
		/*a)deve conter apenas letras, digitos, underscores e espa�os; 
		 * b)deve come�ar por uma letra ou por um underscore; 
		 * c)ter pelo menos dois caracteres (diferentes de espa�o); 
		 * d)ter pelo menos uma letra.*/
		if(validarNomeDeLocal(nome) || validarNomeDeLocal(inicio)  || validarNomeDeLocal(fim)) 
			throw new IllegalArgumentException("O nome tem de conter pelo menos uma letra, um underscore" + 
					"letras,digitos,undersocres e espacos, comecar com uma letra ou um underscore, e ter dois caracteres");
		this.nome = nome;
		this.inicio = inicio;
		this.fim = fim;
		if(distancia < 0)
			throw new IllegalArgumentException("A distancia tem de ser positiva");
		this.distancia = distancia;

		this.declive = declive;
	}

	/**
	 * Construtor de c�pia, deve copiar os valores do percurso recebido para o
	 * novo percurso.
	 * 
	 * @param p
	 *            O percurso a copiar
	 */
	public PercursoSimples(PercursoSimples p) {
		// TODO
		this.nome = p.getInicio();
		this.inicio = p.getFim();
		this.fim = p.getFim();
		this.distancia = p.getDistancia();
		this.declive = p.getDeclive();
	}

	/**
	 * Deve criar uma c�pia do percurso recebido
	 * 
	 * @return O novo percurso id�ntico ao corrente
	 */
	public PercursoSimples clone() {
		// TODO
		return new PercursoSimples(this);
	}

	/**
	 * Deve validar se cont�m s� letras, digitos e espa�os, deve come�ar por uma
	 * letra e ter pelo menos mais uma letra ou d�gito
	 * 
	 * @param nome
	 *            Nome a validar
	 * @return True se o nome for v�lido
	 */
	private static boolean validarNomeDeLocal(String nome) {
		int contador = 0;
		if(nome.length() > 1) {
			char[] newArray = nome.toCharArray();
			for(int i = 0; i < newArray.length;i++) {
				if(Character.isLetter(newArray[i]) && Character.isWhitespace(newArray[i])) {
					contador++;
				}else {
					if(contador == nome.length() -1) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Devolve o nome do percurso
	 * 
	 * @return O nome do percurso
	 */
	public String getNome() {
		// TODO
		return this.nome;
	}

	/**
	 * Devolve o local de in�cio do percurso
	 * 
	 * @return O local de in�cio do percurso
	 */
	public String getInicio() {
		// TODO
		return this.inicio;
	}

	/**
	 * Devolve o local de fim do percurso
	 * 
	 * @return O local de fim do percurso
	 */
	public String getFim() {
		// TODO
		return this.fim;
	}

	/**
	 * Devolve a dist�ncia do percurso
	 * 
	 * @return a dist�ncia do percurso
	 */
	public int getDistancia() {
		// TODO
		return this.distancia;
	}

	/**
	 * Devolve o declive do percurso
	 * 
	 * @return O declive do percurso
	 */
	public int getDeclive() {
		// TODO
		return this.declive;
	}

	/**
	 * ToString, deve devolver uma String tal como:
	 * "A2 de Lisboa para Faro, com 278000 metros e com 0 de declive"
	 * 
	 * @return A string que descreve o percurso
	 */
	public String toString() {
		// TODO
		return getNome() + " de " + getInicio() + " para " + getFim() + ",com " + getDistancia() + " metros e com " + getDeclive();
	}

	/**
	 * Equals, deve devolver true se o percurso recebido tem o mesmo nome, o
	 * mesmo in�cio e o mesmo fim.
	 * 
	 * @param percurso
	 *            Percurso a comparar
	 * @return True se o percurso corrente for igual, por nome, in�cio e fim com
	 *         o percurso recebido
	 */
	public boolean equals(PercursoSimples percurso) {
		// TODO
		if(this.nome.equals(percurso.nome) && this.inicio.equals(percurso.inicio) && this.fim.equals(fim)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Print, deve imprimir na consola o prefixo seguido da informa��o que se
	 * obt�m com o toString
	 * 
	 * @param prefix
	 *            Prefixo a colocar antes da informa��o do toString
	 */
	public void print(String prefix) {
		System.out.println(prefix + toString());
	}

	/**
	 * Main, para realizar testes aos m�todos
	 * 
	 * @param args
	 *            Argumentos do main
	 * 
	 */
	public static void main(String[] args) {
		PercursoSimples ps1 = new PercursoSimples("A2", "Lisboa", "Faro",
				278_000, 0);
		ps1.print("ps1 -> ");

		PercursoSimples ps2 = new PercursoSimples("A1", "Lisboa", "Porto",
				317_000, 0);
		ps2.print("ps2 -> ");

		boolean ps1ps2 = ps1.equals(ps2);
		System.out.println("ps1.equals(ps2) -> " + ps1ps2);

		System.out.println("ps1 toString -> " + ps1);
	}

}

/*- outputs esperado
 * ps1 -> A2 de Lisboa para Faro, com 278000 metros e com 0 de declive 
 * ps2 -> A1 de Lisboa para Porto, com 317000 metros e com 0 de declive
 * ps1.equals(ps2) -> false 
 * ps1 toString -> A2 de Lisboa para Faro, com 278000 metros e com 0 de declive
 */