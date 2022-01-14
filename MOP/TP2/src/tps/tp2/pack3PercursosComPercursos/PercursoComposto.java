package tps.tp2.pack3PercursosComPercursos;

import java.util.Arrays;

/**
 * Classe que suporta um percurso composto por vários percursos simples ou
 * compostos. A classe tem de ter pelo menos um percurso. Não admite localidades
 * repetidas. Os nomes das localidades são case-sensitive. Os percursos têm de
 * estar em sequência, ou seja, onde termina o percurso de índice 0 tem de ser
 * onde se inicia o percurso de índice 1 e assim sucessivamente ...
 * 
 * Num percurso composto considera-se que os seus percursos compostos estão em
 * sequência, mas depois de todos os percursos simples.
 */
public class PercursoComposto {

	/**
	 * Nome do percurso, deve respeitar a regra de validação dos nomes em
	 * percurso simples
	 */
	private String nome;

	/**
	 * Array com os percursos simples. Os elementos devem ser colocados nos
	 * índices menores.
	 */
	private PercursoSimples[] percursosSimples;

	/**
	 * Nº de percursos simples
	 */
	private int nPercursosSimples;

	/**
	 * Array com os percursos compostos. Os elementos devem ser colocados nos
	 * índices menores. Considera-se que estes percursos estão em sequência com
	 * os percursos simples mas depois deles.
	 */
	private PercursoComposto[] percursosCompostos;

	/**
	 * Nº de percursos compostos
	 */
	private int nPercursosCompostos;

	/*
	 *Numero maximo de percursos
	 * */

	private int maxPercursos;

	/**
	 * Constructor que recebe apenas um percurso simples, além do nome e do nº
	 * máximo de percursos. Sugestão: chamar outro construtor.
	 * 
	 * @param nome
	 *            Nome do percurso simples
	 * @param percursoSimples
	 *            Percurso a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos a suportar
	 */
	public PercursoComposto(String nome, PercursoSimples percursoSimples, int maxPercursos) {
		// TODO
		this(nome,new PercursoSimples[]{percursoSimples},new PercursoComposto[0],maxPercursos);
	}

	/**
	 * Constructor que recebe apenas um percurso composto, além do nome e do nº
	 * máximo de percursos. Sugestão: chamar outro construtor.
	 * 
	 * @param nome
	 *            Nome do percurso composto
	 * @param percursoComposto
	 *            Percurso composto a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos a suportar
	 */
	public PercursoComposto(String nome, PercursoComposto percursoComposto, int maxPercursos) {
		// TODO
		this(nome, new PercursoSimples[0],new PercursoComposto[] {percursoComposto},maxPercursos);
	}

	/**
	 * Constructor que recebe um array percursos simples, além do nome e do nº
	 * máximo de percursos. O array não pode ter nulls, os seus percursos têm de
	 * estar em sequência e não pode haver repetições de localidades. Sugestão:
	 * chamar outro construtor.
	 * 
	 * @param nome
	 *            Nome do percurso simples
	 * @param percursosSimples
	 *            Percursos a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos a suportar
	 */
	public PercursoComposto(String nome, PercursoSimples[] percursosSimples, int maxPercursos) {
		// TODO
		this(nome,percursosSimples,new PercursoComposto[0],maxPercursos);
	}

	/**
	 * Constructor que recebe um array percursos compostos, além do nome e do nº
	 * máximo de percursos. O array não pode ter nulls, os seus percursos têm de
	 * estar em sequência e não pode haver repetições de localidades.
	 * Considera-se que os percursos compostos recebidos, em si, estão bem
	 * formados. Sugestão: chamar outro construtor.
	 * 
	 * @param nome
	 *            Nome do percurso composto
	 * @param percursosCompostos
	 *            Percursos compostos a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos a suportar
	 */
	public PercursoComposto(String nome, PercursoComposto[] percursosCompostos, int maxPercursos) {
		// TODO
		this(nome, new PercursoSimples[0],percursosCompostos,maxPercursos);
	}

	/**
	 * Constructor que recebe um array percursos simples e um array de percursos
	 * compostos, além do nome e do nº máximo de percursos. Os array têm de ter
	 * no conjunto pelo menos um percurso simples. Os percursos simples
	 * recenidos no array consideram-se como ficanado antes dos percursos
	 * compostos recebidos no array e têm de estar em sequência e não pode haver
	 * repetições de localidades sob nenhuma forma. Considera-se que os
	 * percursos compostos recebidos, em si, estão bem formados. Sugestão:
	 * chamar os métodos de adicionar no final.
	 * 
	 * @param nome
	 *            Nome do percurso composto, deve ser validado pelo método em
	 *            percurso simples
	 * @param percursosSimples
	 *            Percursos simples a guardar
	 * @param percursosCompostos
	 *            Percursos compostos a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos simples e de percursos compostos a
	 *            suportar. Max = 20, deve suportar 20 percursos simples e 20
	 *            percursos compostos
	 */
	public PercursoComposto(String nome, PercursoSimples[] percursosSimples, PercursoComposto[] percursosCompostos,
			int maxPercursos) {

		if(nome.length() == 0)
			throw new IllegalArgumentException("Nome inserido inválido!");
		this.nome = nome;

		if(percursosSimples != null) {
			for(int i = 0; i < percursosSimples.length-1;i++) {
				if(percursosSimples[i] != null) {
					if(!(percursosSimples[i].getFim().equals(percursosSimples[i+1].getInicio())) 
							&& !(percursosSimples[i].equals(percursosSimples[percursosSimples.length-1]))) {
						throw new IllegalArgumentException("Os percursos não estão em sequência");
					}	
				}else
					throw new IllegalArgumentException("Os percursos não podem ser nulls");
			}
		}else
			throw new IllegalArgumentException("Percurso sem percursos simples");

		if(maxPercursos < percursosSimples.length)
			throw new IllegalArgumentException("O número de percursos simples não pode ser inferio ao número maximo de percursos");

		this.maxPercursos = maxPercursos;

		this.nPercursosSimples = percursosSimples.length;
		
		this.percursosSimples = new PercursoSimples[nPercursosSimples];
		for(int i  = 0; i < percursosSimples.length;i++) {
			this.percursosSimples[i] = percursosSimples[i];
		}

		this.nPercursosCompostos = percursosCompostos.length;
		this.percursosCompostos = new PercursoComposto[nPercursosCompostos];
		for(int i = 0; i< percursosCompostos.length;i++) {
			this.percursosCompostos[i] = percursosCompostos[i];
		}
	}

	/**
	 * Copy constructor, deve criar uma cópia profunda do percurso recebido.
	 * 
	 * @param pc
	 *            Percurso a copiar
	 */
	public PercursoComposto(PercursoComposto pc) {
		// TODO
		this(pc.nome,pc.percursosSimples,pc.percursosCompostos,pc.maxPercursos);
	}

	/**
	 * Deve criar uma cópia profunda do percurso corrente
	 * 
	 * @return O percurso composto que é uma cópia profunda do percurso composto
	 *         corrente
	 */
	public PercursoComposto clone() {
		return new PercursoComposto(this.nome,this.percursosSimples,this.percursosCompostos,this.maxPercursos);
	}

	/**
	 * Deve adicionar o percurso simples no final, desde que este esteja em
	 * sequência e haja espaço. Não pode adicionar se já existirem percursos
	 * compostos ou se a nova localidade já existir. Sugestão: utilizar os
	 * métodos de getLocalidades e haveRepetições.
	 * 
	 * @param percurso
	 *            Percurso simples a adicionar
	 * @return True se adicionou
	 */
	public boolean addicionarPercursoSimplesNoFinal(PercursoSimples percurso) {

		if(percurso == null){
			return false;
		}

		if (maxPercursos < percursosSimples.length) {
			return false;
		}

		if(!(percursosSimples[percursosSimples.length-1].getFim().equals(percurso.getInicio()))){
			return false;
		}

		PercursoSimples[] novo_array = new PercursoSimples[percursosSimples.length+1];

		System.arraycopy(percursosSimples, 0, novo_array, 0, percursosSimples.length);

		novo_array[percursosSimples.length] = percurso;

		this.percursosSimples = novo_array;



		return true;
	}

	/**
	 * Deve adicionar o percurso composto no final, desde que este esteja em
	 * sequência e haja espaço. Não pode adicionar as novas localidades já
	 * existirem. Sugestão: utilizar os métodos de getLocalidades e
	 * haveRepetições.
	 * 
	 * @param percurso
	 *            Percurso composto a adicionar
	 * @return True se adicionou
	 */
	public boolean addicionarPercursoCompostoNoFinal(PercursoComposto percurso) {

		if(percurso == null) return false;

		if (maxPercursos < nPercursosCompostos) return false;

		if(nPercursosCompostos > 0){
			if(!(percursosCompostos[percursosCompostos.length-1].getFim().equals(percurso.getInicio()))){
				return false;
			}
		}

		PercursoComposto[] novo_array = new PercursoComposto[percursosCompostos.length+1];

		System.arraycopy(percursosCompostos, 0, novo_array, 0, percursosCompostos.length);

		novo_array[percursosCompostos.length] = percurso;

		this.percursosCompostos = novo_array;

		return true;
	}

	/**
	 * Método que retorna true se há repetições entre os dois arrays. Sugere-se
	 * a ordenação de um array, e percorrer o outro e para cada elemento dele
	 * fazer uma pesquisa binária no array ordenado.
	 * 
	 * @param locs1
	 *            Array1 com localidades
	 * @param locs2
	 *            Array2 com localidades
	 * @return True se alguma localidade em Array1 existe em array2, false caso
	 *         contrário
	 */
	private static boolean haveRepetitions(String[] locs1, String[] locs2) {
		// TODO
		for(int i = 0; i < locs1.length;i++) {
			for(int j = 0; j < locs2.length;j++) {
				if(locs1[i].equals(locs2[j])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Obtém todas as localidades distintas existentes do percurso, mesmo as de
	 * início e de fim. Deve devolver as localidades num novo array e sem
	 * posições a null. Sugestão: utilizar o método getNumLocalidades para saber
	 * préviamente quantas localidades há.
	 * 
	 * @return O novo array com todas as localidades existentes no percurso
	 *         composto
	 */
	private String[] getLocalidades() {

		if(getNumLocalidades() < 0)
			throw new IllegalArgumentException("Não existem localidades");


		String[] novo_array = new String[getNumLocalidades()];
		int indice = 0;

		for(int i = 0; i < nPercursosSimples; i++){
			novo_array[i] = percursosSimples[i].getInicio();
			indice++;
		}

		if(nPercursosSimples != 0){
			novo_array[indice] = percursosSimples[nPercursosSimples-1].getFim();
			indice++;
		}


		for(int i = 0; i < nPercursosCompostos; i++){
			novo_array[indice] = percursosCompostos[i].getInicio();
			indice++;
			for(int j = 0; j < percursosCompostos[i].percursosSimples.length; j++){
				novo_array[indice] = percursosCompostos[i].percursosSimples[j].getFim();
				indice++;
			}

		}

		return novo_array;
	}

	/**
	 * Obtém o número de localidades distintas existentes dentro do percurso
	 * composto actual. Devem ser incluidas as localidades de início e de fim do
	 * percurso.
	 * 
	 * @return Número de localidades distintas existentes dentro deste percurso
	 *         composto
	 */
	private int getNumLocalidades() {
		int numLocalidades = this.nPercursosSimples + 1;

		for(int i = 0; i < nPercursosCompostos; i++) {
			numLocalidades = numLocalidades + percursosCompostos[i].percursosSimples.length;
		}
		return numLocalidades;
	}

	/**
	 * Deve adicionar o percurso simples no início, desde que este esteja em
	 * sequência e haja espaço. Não adicionar caso provoque uma repetição de
	 * localidades. Sugestão: utilizar o getLocalidades e haveRepetitions.
	 * 
	 * @param percurso
	 *            Percurso simples a adicionar
	 * @return True se adicionou, ou false em caso contrário
	 */
	public boolean addicionarPercursoSimplesNoInicio(PercursoSimples percurso) {
		//percurso nao pode ser null
		if(percurso == null){
			return false;
		}

		//nao pode haver espaços
		if (maxPercursos < nPercursosSimples) {
			return false;
		}

		//verificar se o inicio  do percursoSimples é inicio ao fim do percurso ( se está em sequencia )
		if(!(percursosSimples[0].getInicio().equals(percurso.getFim()))){
			return false;
		}


		//método para copiar os arrays.

		PercursoSimples[] novo_array = new PercursoSimples[percursosSimples.length+1];

		System.arraycopy(percursosSimples, 0, novo_array, 1, nPercursosSimples);

		novo_array[0] = percurso;

		this.percursosSimples = novo_array;


		return true;
	}

	/**
	 * Deve adicionar o percurso composto recebido no início deste percurso
	 * composto. Não pode adicionar se já existirem percursos simples, se
	 * provocar uma repetição de localidades, ou se não existir espaço.
	 * Sugestão: verificar a repetição das localidades utilizando os métodos
	 * getLocalidades e haveRepetitions.
	 * 
	 * @param percurso
	 *            Percurso composto a adicionar
	 * @return True se adicionou, ou false caso contrário
	 */
	public boolean addicionarPercursoCompostoNoInicio(PercursoComposto percurso) {
		//percurso nao pode ser null
		if(percurso == null){
			return false;
		}

		//verificar se ha espaço
		if (maxPercursos < nPercursosCompostos) {
			return false;
		}

		String[] compostoSemFim = new String[percurso.getLocalidades().length-1];
		System.arraycopy(percurso.getLocalidades(), 0, compostoSemFim, 0, compostoSemFim.length);

		if(haveRepetitions(compostoSemFim, getLocalidades())){
			return false;
		}

		//método para copiar os arrays.


		PercursoComposto[] novo_array = new PercursoComposto[percursosCompostos.length+1];

		System.arraycopy(percursosCompostos, 0, novo_array, 1, nPercursosCompostos);

		novo_array[0] = percurso;

		this.percursosCompostos = novo_array;

		return true;
	}

	/**
	 * Devolve o início do percurso
	 * 
	 * @return O local de início do percurso
	 */
	public String getInicio() {
		// TODO
		if(nPercursosSimples > 0)
			return percursosSimples[0].getInicio();

		if(nPercursosCompostos > 0)
			return percursosCompostos[0].getInicio();

		return null;
	}

	/**
	 * Devolve o fim do percurso
	 * 
	 * @return O local de fim do percurso
	 */
	public String getFim() {
		// TODO

		if(nPercursosSimples > 0)
			return percursosSimples[percursosSimples.length - 1].getFim();

		if(nPercursosCompostos > 0)
			return percursosCompostos[percursosCompostos.length - 1].getFim();

		return null;
	}

	/**
	 * Devolve a distância do percurso, que deve ser o somatório das distâncias
	 * dos seus percursos
	 * 
	 * @return A distância do percurso
	 */
	public int getDistancia() {
		// TODO
		int distTotal = 0;
		int dist1 = 0;
		int dist2 = 0;

		for(int i = 0; i < percursosSimples.length;i++) {
			dist1 = dist1 + percursosSimples[i].getDistancia();
		}
		for(int j = 0; j < percursosCompostos.length;j++) {
			dist2 = dist2 + percursosCompostos[j].getDistancia();
		}

		distTotal = dist1 + dist2;
		return distTotal;
	}

	/**
	 * Devolve o declive do percurso, que deve ser o somatório dos declives dos
	 * seus percursos
	 * 
	 * @return O declive do percurso
	 */
	public int getDeclive() {
		// TODO
		int decliveTotal = 0;
		int declive1 = 0;
		int declive2 = 0;

		for(int i = 0; i < percursosSimples.length;i++) {
			declive1 = declive1 + percursosSimples[i].getDeclive();
		}
		for(int j = 0; j < percursosCompostos.length;j++) {
			declive2 = declive2 + percursosCompostos[j].getDeclive();
		}

		decliveTotal = declive1 + declive2;
		return decliveTotal;
	}

	/**
	 * Devolve o declive do percurso, que deve ser o somatório dos declives dos
	 * seus percursos, mas só se deve considerar os declives positivos
	 * 
	 * @return O declive acumulado do percurso mas só considerando os declives
	 *         positivos
	 */
	public int getSubidaAcumulada() {
		// TODO
		int saTotal = 0;
		int sa1 = 0;
		int sa2 = 0;

		for(int i = 0; i < percursosSimples.length;i++) {
			sa1 = sa1 + percursosSimples[i].getDeclive();
		}
		for(int j = 0; j < percursosCompostos.length;j++) {
			sa2 = sa2 + percursosCompostos[j].getDeclive();
		}

		saTotal = sa1 + sa2;
		return saTotal;
	}

	/**
	 * Devolve o nome do percurso
	 * 
	 * @return O nome do percurso
	 */
	public String getNome() {
		// TODO
		return nome;
	}

	/**
	 * Altera o nome do percurso
	 * 
	 * @param nome
	 *            O novo nome do percurso
	 */
	public void setNome(String nome) {
		// TODO
		this.nome = nome;
	}

	/**
	 * Devolve uma string com uma descrição do percurso, tal como: NORTE_SUL de
	 * Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos
	 * simples e 1 percuros compostos
	 * 
	 * @return O string que descreve o percurso
	 */
	public String toString() {
		// TODO
		return getNome() + " de " + getInicio() + " para " + getFim() + ",com " + 
		getDistancia() + " metros e com " + getDeclive();
	}

	/**
	 * Imprime na consola o percurso. Deve mostrar na primeiro linha o prefixo
	 * seguido da informação do toString deste objecto. Depois deve mostrar os
	 * seus percursos, um por linha, chamando os seus métodos de print, mas
	 * passando como prefixo o prefixo recebido mas prefixado de 2 espaços.
	 * 
	 * @param prefix
	 *            Prefixo a colocar antes da informação do toString e também na
	 *            parte de mostrar os percursos.
	 */
	public void print(String prefix) {
		// TODO
		System.out.println(prefix + toString());
		for(int i = 0; i < percursosSimples.length;i++) {
			System.out.println(" " + prefix + percursosSimples[i]);
		}
		for(int j = 0; j < percursosCompostos.length;j++) {
			percursosCompostos[j].print(" " + prefix);
		}
	}


	/**
	 * Main, para testes
	 * 
	 * @param args
	 *            Argumentos do main
	 */
	public static void main(String[] args) {
		PercursoSimples ps1, ps2, ps3, ps4;

		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1", "Lisboa", "Porto", 317_000, 20);
		ps3 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps4 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);

		String strSeparator = " ---------------------------------------------------------------- ";

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com PercursoSimples");
		PercursoComposto pcA1 = new PercursoComposto("PSA1", ps1, 20);
		pcA1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com array de PercursoSimples");
		PercursoComposto pc1 = new PercursoComposto("NORTE_SUL", new PercursoSimples[] { ps1, ps2 }, 20);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com PercursoComposto");
		PercursoComposto pc2 = new PercursoComposto("NORTE_SUL_V2",
				new PercursoComposto("NORTE_SUL", new PercursoSimples[] { ps1, ps2 }, 20), 20);
		pc2.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com array de PercursoComposto");
		PercursoComposto pc3 = new PercursoComposto("NORTE_SUL_V2", new PercursoComposto[] { pc1 }, 20);
		pc3.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com arrays de PercursoSimple e PercursoComposto");
		PercursoComposto pc4 = new PercursoComposto("SUL_NORTE", new PercursoSimples[] { ps1, ps2 },
				new PercursoComposto[] { new PercursoComposto("NN", ps3, 20) }, 20);
		pc4.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar PercursoSimples No Início");

		pc1.addicionarPercursoSimplesNoInicio(ps4);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar PercursoSimples No Final");
		pc1.print("> ");
		System.out.println();

		boolean result = pc1.addicionarPercursoSimplesNoFinal(ps3);
		System.out.println("A adição de " + ps3 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar PercursoSimples No Final com erro");
		pc1.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoSimplesNoFinal(ps3);
		System.out.println("A adição de " + ps3 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar PercursoComposto No Inicio");
		pc2.print("> ");
		System.out.println();

		result = pc2.addicionarPercursoCompostoNoInicio(new PercursoComposto("SAGRESFARO", ps4, 20));
		System.out.println("A adição de " + ps4 + " deu -> " + result);
		System.out.println();

		pc2.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar PercursoComposto No Inicio com erro");
		pc1.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoCompostoNoInicio(pc2);
		System.out.println("A adição de " + pc2 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar PercursoComposto No Final");
		PercursoComposto pc6 = new PercursoComposto("sul",
				new PercursoComposto("ss", new PercursoSimples[] { ps4, ps1 }, 20), 20);
		pc6.print("> ");
		System.out.println();

		PercursoComposto pc7 = new PercursoComposto("centro", new PercursoSimples[] { ps2, ps3 }, 20);

		result = pc6.addicionarPercursoCompostoNoFinal(pc7);
		System.out.println("A adição de " + pc7 + " deu -> " + result);
		System.out.println();

		pc6.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao clone");

		pc6.print("> ");
		System.out.println();

		PercursoComposto pc8 = pc6.clone();
		pc8.print("> ");
		System.out.println();

		PercursoComposto pcA = new PercursoComposto("ww",
				new PercursoSimples[] { new PercursoSimples("vVC", "Viana do Castelo", "Caminha", 70_000, 20) }, 20);

		result = pc6.addicionarPercursoCompostoNoFinal(pcA);

		System.out.println("Adicionado " + pcA + " ao pc original, deu -> " + result);
		System.out.println();

		pc8.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao subidaAcumulada");
		pc6.print("> ");
		System.out.println();
		System.out.println(pc6 + " tem uma subida acumulada de -> " + pc6.getSubidaAcumulada());
	}
}

/*- Outputs esperados

---------------------------------------------------------------- 
Teste ao construtor com PercursoSimples
> PSA1 de Faro para Lisboa, com 278000 metros, com 10 de declive, com 1 percursos simples e 0 percursos compostos
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive

 ---------------------------------------------------------------- 
Teste ao construtor com array de PercursoSimples
> NORTE_SUL de Faro para Porto, com 595000 metros, com 30 de declive, com 2 percursos simples e 0 percursos compostos
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

 ---------------------------------------------------------------- 
Teste ao construtor com PercursoComposto
> NORTE_SUL_V2 de Faro para Porto, com 595000 metros, com 30 de declive, com 0 percursos simples e 1 percursos compostos
  > NORTE_SUL de Faro para Porto, com 595000 metros, com 30 de declive, com 2 percursos simples e 0 percursos compostos
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

 ---------------------------------------------------------------- 
Teste ao construtor com array de PercursoComposto
> NORTE_SUL_V2 de Faro para Porto, com 595000 metros, com 30 de declive, com 0 percursos simples e 1 percursos compostos
  > NORTE_SUL de Faro para Porto, com 595000 metros, com 30 de declive, com 2 percursos simples e 0 percursos compostos
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

 ---------------------------------------------------------------- 
Teste ao construtor com arrays de PercursoSimple e PercursoComposto
> SUL_NORTE de Faro para Viana do Castelo, com 668800 metros, com 60 de declive, com 2 percursos simples e 1 percursos compostos
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
  > NN de Porto para Viana do Castelo, com 73800 metros, com 30 de declive, com 1 percursos simples e 0 percursos compostos
    > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

 ---------------------------------------------------------------- 
Teste ao adicionar PercursoSimples No Início
> NORTE_SUL de Sagres para Porto, com 662000 metros, com 20 de declive, com 3 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

 ---------------------------------------------------------------- 
Teste ao adicionar PercursoSimples No Final
> NORTE_SUL de Sagres para Porto, com 662000 metros, com 20 de declive, com 3 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

A adição de A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive deu -> true

> NORTE_SUL de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 4 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
  > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

 ---------------------------------------------------------------- 
Teste ao adicionar PercursoSimples No Final com erro
> NORTE_SUL de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 4 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
  > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

A adição de A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive deu -> false

> NORTE_SUL de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 4 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
  > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

 ---------------------------------------------------------------- 
Teste ao adicionar PercursoComposto No Inicio
> NORTE_SUL_V2 de Faro para Porto, com 595000 metros, com 30 de declive, com 0 percursos simples e 1 percursos compostos
  > NORTE_SUL de Faro para Porto, com 595000 metros, com 30 de declive, com 2 percursos simples e 0 percursos compostos
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

A adição de A23 de Sagres para Faro, com 67000 metros e com -10 de declive deu -> true

> NORTE_SUL_V2 de Sagres para Porto, com 662000 metros, com 20 de declive, com 0 percursos simples e 2 percursos compostos
  > SAGRESFARO de Sagres para Faro, com 67000 metros, com -10 de declive, com 1 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > NORTE_SUL de Faro para Porto, com 595000 metros, com 30 de declive, com 2 percursos simples e 0 percursos compostos
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive

 ---------------------------------------------------------------- 
Teste ao adicionar PercursoComposto No Inicio com erro
> NORTE_SUL de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 4 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
  > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

A adição de NORTE_SUL_V2 de Sagres para Porto, com 662000 metros, com 20 de declive, com 0 percursos simples e 2 percursos compostos deu -> false

> NORTE_SUL de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 4 percursos simples e 0 percursos compostos
  > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
  > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
  > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

 ---------------------------------------------------------------- 
Teste ao adicionar PercursoComposto No Final
> sul de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 0 percursos simples e 1 percursos compostos
  > ss de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive

A adição de centro de Lisboa para Viana do Castelo, com 390800 metros, com 50 de declive, com 2 percursos simples e 0 percursos compostos deu -> true

> sul de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 0 percursos simples e 2 percursos compostos
  > ss de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > centro de Lisboa para Viana do Castelo, com 390800 metros, com 50 de declive, com 2 percursos simples e 0 percursos compostos
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
    > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

 ---------------------------------------------------------------- 
Teste ao clone
> sul de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 0 percursos simples e 2 percursos compostos
  > ss de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > centro de Lisboa para Viana do Castelo, com 390800 metros, com 50 de declive, com 2 percursos simples e 0 percursos compostos
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
    > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

> sul de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 0 percursos simples e 2 percursos compostos
  > ss de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > centro de Lisboa para Viana do Castelo, com 390800 metros, com 50 de declive, com 2 percursos simples e 0 percursos compostos
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
    > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

Adicionado ww de Viana do Castelo para Caminha, com 70000 metros, com 20 de declive, com 1 percursos simples e 0 percursos compostos ao pc original, deu -> true

> sul de Sagres para Viana do Castelo, com 735800 metros, com 50 de declive, com 0 percursos simples e 2 percursos compostos
  > ss de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > centro de Lisboa para Viana do Castelo, com 390800 metros, com 50 de declive, com 2 percursos simples e 0 percursos compostos
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
    > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive

 ---------------------------------------------------------------- 
Teste ao subidaAcumulada
> sul de Sagres para Caminha, com 805800 metros, com 70 de declive, com 0 percursos simples e 3 percursos compostos
  > ss de Sagres para Lisboa, com 345000 metros, com 0 de declive, com 2 percursos simples e 0 percursos compostos
    > A23 de Sagres para Faro, com 67000 metros e com -10 de declive
    > A2 de Faro para Lisboa, com 278000 metros e com 10 de declive
  > centro de Lisboa para Viana do Castelo, com 390800 metros, com 50 de declive, com 2 percursos simples e 0 percursos compostos
    > A1 de Lisboa para Porto, com 317000 metros e com 20 de declive
    > A28 de Porto para Viana do Castelo, com 73800 metros e com 30 de declive
  > w de Viana do Castelo para Caminha, com 70000 metros, com 20 de declive, com 1 percursos simples e 0 percursos compostos
    > vVC de Viana do Castelo para Caminha, com 70000 metros e com 20 de declive

sul de Sagres para Caminha, com 805800 metros, com 70 de declive, com 0 percursos simples e 3 percursos compostos tem uma subida acumulada de -> 80
 */