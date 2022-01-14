package tps.tp3;

import java.util.Arrays;
import java.util.Scanner;
/**
 * Classe derivada de Percurso que suporta um percurso composto por vários
 * percursos simples ou compostos. A classe tem de ter, sempre, pelo menos um
 * percurso. Não admite localidades repetidas. Os nomes das localidades são
 * case-sensitive. Os percursos têm de estar em sequência, ou seja, onde termina
 * o percurso de índice n tem de ser onde se inicia o percurso de índice n + 1.
 */
public class PercursoComposto extends Percurso {

	/**
	 * Array com os percursos. Os elementos devem ser colocados nos índices
	 * menores.
	 */
	private Percurso[] percursos;

	/**
	 * Nº de percursos
	 */
	private int nPercursos;

	/**
	 * Constructor que recebe apenas um percurso, além do nome e do nº máximo de
	 * percursos. Sugestão: chamar outro construtor.
	 * 
	 * @param nome
	 *            Nome do percurso
	 * @param percurso
	 *            Percurso a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos a suportar
	 */
	public PercursoComposto(String nome, Percurso percurso, int maxPercursos) {
		this(nome,new Percurso[] {percurso},maxPercursos);
	}

	/**
	 * Constructor que recebe um array percursos, além do nome e do nº máximo de
	 * percursos. O array tem de ter pelo menos um percurso. Os percursos
	 * recebidos no array estão bem formados, mas que têm de estar em sequência
	 * e não pode haver repetições de localidades sob nenhuma forma. Sugestão:
	 * chamar os métodos de adicionar no final.
	 * 
	 * @param nome
	 *            Nome do percurso composto, deve ser validado pelo método em
	 *            percurso simples
	 * @param percursos
	 *            Percursos a guardar
	 * @param maxPercursos
	 *            Nº máximo de percursos a suportar
	 */
	public PercursoComposto(String nome, Percurso[] percursos, int maxPercursos) {
		super(nome);

		// quando a false não valida a sequencialidade dos locais
		final boolean CHECKLOCALIDADES = true;

		if (CHECKLOCALIDADES) {

			if(percursos != null ){
				for(int i = 0; i < percursos.length-1; i++){
					if (percursos[i] != null) {
						if(!(percursos[i].getFim().equals(percursos[i+1].getInicio())) && 
								!(percursos[i].equals(percursos[percursos.length-1]))){
							throw new IllegalArgumentException("Os percursos não estão em sequência");
						}
					}
					else
						throw new IllegalArgumentException("Os percursos não podem ser nulls");
				}
			}
			else
				throw new IllegalArgumentException("Percurso sem percursos simples");

		} else {
			for(int i = 0; i < percursos.length; i++){
				addicionarPercursoNoFinal(percursos[i]);
			}

		}

		this.percursos = percursos;
		this.nPercursos = maxPercursos;
	}
	
	/**
	 * Copy constructor, deve criar uma cópia profunda do percurso recebido.
	 * 
	 * @param pc
	 *            Percurso a copiar
	 */
	public PercursoComposto(PercursoComposto pc) {
		// TODO, remover, ou refazer, a linha seguinte
		super(pc.getNome());

		this.percursos = new Percurso[pc.percursos.length];

		System.arraycopy(pc.percursos, 0, this.percursos, 0, pc.percursos.length);

		this.nPercursos = pc.nPercursos;
	}

	/**
	 * Deve criar uma cópia profunda do percurso corrente
	 * 
	 * @return O percurso composto que é uma cópia profunda do percurso composto
	 *         corrente
	 */
	public Percurso clone() {
		return new PercursoComposto(this);
	}

	/**
	 * Deve adicionar o percurso no final, desde que este esteja em sequência e
	 * haja espaço enão não provocar uma repetição de localidades Sugestão:
	 * utilizar os métodos de getLocalidades e haveRepetições.
	 * 
	 * @param percurso
	 *            Percurso a adicionar
	 * @return True se adicionou
	 */
	public boolean addicionarPercursoNoFinal(Percurso percurso) {
		if(percurso == null) return false;

		if (nPercursos < percursos.length) return false;

		if(!(percursos[percursos.length-1].getFim().equals(percurso.getInicio()))) return false;

		Percurso[] novo_array = new Percurso[percursos.length+1];

		System.arraycopy(percursos, 0, novo_array, 0, percursos.length);

		novo_array[percursos.length] = percurso;

		this.percursos = novo_array;

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
	public String[] getLocalidades() {
		String[] localidades = new String[this.getNumLocalidades()]; //string que contem o numero de localidades

		int idx = 0;

		localidades[idx] = percursos[0].getInicio();

		idx++;
		
		for(int i = 0; i < percursos.length;i++) {
			if(percursos[i] instanceof PercursoSimples) {
				localidades[idx] = percursos[i].getFim();
				idx++;
			}else if(percursos[i] instanceof PercursoComposto) {
				PercursoComposto a = (PercursoComposto)percursos[i];
				System.arraycopy(a.getLocalidades(),1, localidades,idx,a.getLocalidades().length-1);
				idx += a.getLocalidades().length-1;
			}
		}

		return localidades;
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
		int contador = 0;

		for(int i = 0; i< percursos.length;i++) {
			if(percursos[i] instanceof PercursoComposto) {
				contador += ((PercursoComposto)percursos[i]).getNumLocalidades()-1;
			}else if(percursos[i] instanceof PercursoSimples) {
				contador ++;
			}
		}
		return contador + 1;
	}

	/**
	 * Deve adicionar o percurso recebido no início deste percurso composto. Não
	 * pode adicionar se não estiver em sequência, se provocar uma repetição de
	 * localidades, ou se não existir espaço. Sugestão: verificar a repetição
	 * das localidades utilizando os métodos getLocalidades e haveRepetitions.
	 * 
	 * @param percurso
	 *            Percurso a adicionar
	 * @return True se adicionou, ou false caso contrário
	 */
	public boolean addicionarPercursoNoInicio(Percurso percurso) {
		if(percurso == null) return false;


		if (percursos.length+1 > nPercursos)  return false;

		if(!(percursos[0].getInicio().equals(percurso.getFim()))) return false;

		Percurso[] novo_array_2 = new Percurso[percursos.length+1];	

		System.arraycopy(percursos, 0, novo_array_2, 1, percursos.length);

		novo_array_2[0] = percurso;

		this.percursos = novo_array_2;

		return true;
	}

	/**
	 * Devolve o início deste percurso composto
	 * 
	 * @return O local de início do percurso
	 */
	public String getInicio() {
		if(nPercursos > 0) return percursos[0].getInicio();
		return null;
	}

	/**
	 * Devolve o fim deste percurso composto
	 * 
	 * @return O local de fim do percurso
	 */
	public String getFim() {
		if(nPercursos > 0) return percursos[percursos.length-1].getFim();
		return null;
	}

	/**
	 * Devolve a distância do percurso, que deve ser o somatório das distâncias
	 * dos seus percursos
	 * 
	 * @return A distância do percurso
	 */
	public int getDistancia() {
		int distancia = 0;
		for(int i = 0; i < percursos.length;i++) {
			distancia = percursos[i].getDistancia();
		}
		return distancia;
	}

	/**
	 * Devolve o declive do percurso, que deve ser o somatório dos declives dos
	 * seus percursos
	 * 
	 * @return O declive do percurso
	 */
	public int getDeclive() {
		int declive = 0;
		for(int i = 0; i < percursos.length;i++) {
			declive = percursos[i].getDeclive();
		}
		return declive;
	}

	/**
	 * Devolve o declive do percurso, que deve ser o somatório dos declives dos
	 * seus percursos, mas só se deve considerar os declives positivos
	 * 
	 * @return O declive acumulado do percurso mas só considerando os declives
	 *         positivos
	 */
	public int getSubidaAcumulada() {
		int subAc = 0;
		for(int i = 0; i < percursos.length; i++) {
			int vd = percursos[i].getDeclive();
			if(vd > 0) subAc += percursos[i].getDeclive();
		}
		return subAc;
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
		System.out.println(prefix+toString());
		for(int i = 0; i < percursos.length;i++) {
			percursos[i].print(" " + " " + " " + prefix + percursos[i]);
		}
	}

	/**
	 * Deve devolver "composto"
	 */
	public String getDescricao() {
		// TODO
		return "composto";
	}

	/**
	 * Deve remover todos os percursos desde o ponto de partida na localidade
	 * recebida até ao final. Devolve true se removeu algo.
	 */
	public boolean removerPercursosNoFimDesde(String localidade) {
		if(getIdxLocalidadeEmInicio(localidade) == -1) return false;


		if(percursos[getIdxLocalidadeEmInicio(localidade)] instanceof PercursoComposto) {
			Percurso[] newArray = new Percurso[getIdxLocalidadeEmInicio(localidade)];

			System.arraycopy(percursos, 0, newArray, 0, getIdxLocalidadeEmInicio(localidade));

			this.percursos = newArray;

			this.nPercursos = percursos.length;

			return true;
		}else {
			if(percursos.length == 1 &&  percursos[getIdxLocalidadeEmInicio(localidade)].getInicio().equals(localidade)){
				return false;
			}
		}if(percursos[getIdxLocalidadeEmInicio(localidade)].getInicio().equals(localidade)) {
			Percurso[] newArray = new Percurso[getIdxLocalidadeEmInicio(localidade)];

			System.arraycopy(percursos, 0, newArray, 0, getIdxLocalidadeEmInicio(localidade));

			this.percursos = newArray;

			this.nPercursos = percursos.length;

			return true;
		}else{
			((PercursoComposto)percursos[getIdxLocalidadeEmInicio(localidade)]).removerPercursosNoFimDesde(localidade);
		}
		return true;
	}

	/**
	 * Remover desde o início até ao local recebido. O local deve estar no
	 * início de um percurso. Devolve true se removeu algum percurso.
	 */
	public boolean removerPercursosNoInicioAte(String localidade) {
		if(getIdxLocalidadeEmInicio(localidade) == -1) return false;


		if(percursos[getIdxLocalidadeEmInicio(localidade)] instanceof PercursoSimples) {
			Percurso[] newArray = new Percurso[percursos.length - getIdxLocalidadeEmInicio(localidade)];

			System.arraycopy(percursos, getIdxLocalidadeEmInicio(localidade), newArray, 0, percursos.length - getIdxLocalidadeEmInicio(localidade));

			this.percursos = newArray;

			this.nPercursos = percursos.length;

			return true;
		}else {
			if(percursos.length == 1 &&  percursos[getIdxLocalidadeEmInicio(localidade)].getInicio().equals(localidade)){
				return false;
			}
			if(percursos[getIdxLocalidadeEmInicio(localidade)].getInicio().equals(localidade)) {
				Percurso[] newArray = new Percurso[percursos.length - getIdxLocalidadeEmInicio(localidade)];

				System.arraycopy(percursos, getIdxLocalidadeEmInicio(localidade), newArray, 0, percursos.length - getIdxLocalidadeEmInicio(localidade));

				this.percursos = newArray;

				this.nPercursos = percursos.length;

				return true;
			}else{
				((PercursoComposto)percursos[getIdxLocalidadeEmInicio(localidade)]).removerPercursosNoInicioAte(localidade);
			}
			return true;
		}
	}

	/**
	 * Devolve o índice do elemento do percurso, directamente dentro do percurso
	 * corrente, que tem a localidade recebida como uma partida
	 */
	private int getIdxLocalidadeEmInicio(String localidade) {
		for(int i = 0; i < percursos.length;i++) {
			if(percursos[i] instanceof PercursoComposto) {
				int a = ((PercursoComposto)percursos[i]).getIdxLocalidadeEmInicio(localidade);
				if (a != 1) return i;
			}else if(percursos[i] instanceof PercursoSimples) {
				if(localidade.equals(percursos[i].getInicio())) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * ========================================================================
	 * ========================================================================
	 * A partir daqui segue-se a zona dos métodos de teste
	 * ========================================================================
	 * ========================================================================
	 */

	/**
	 * Métodos auxiliares para testarem as várias funcionalidades
	 */
	private static String strSeparator = " ---------------------------------------------------------------- ";

	/**
	 * Teste aos contrutores
	 */
	private static void testConstrutores() {
		System.out.println("=====================================================================================");
		System.out.println("Teste aos construtores ==============================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com PercursoSimples");
		PercursoComposto pc1 = new PercursoComposto("PC1", ps1, 20);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com PercursoComposto");
		PercursoComposto pc2 = new PercursoComposto("PC2", new PercursoComposto("PC2", new Percurso[] { ps1, ps2 }, 20),
				20);
		pc2.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao construtor com arrays de PercursoSimple e PercursoComposto");
		PercursoComposto pc3 = new PercursoComposto("PC3",
				new Percurso[] { ps0, ps1, ps2, new PercursoComposto("PCAUX", new Percurso[] { ps3, ps4 }, 20) }, 20);
		pc3.print("> ");
		System.out.println();

		System.out.println();
	}

	/**
	 * Teste ao numLocalidades
	 */
	private static void testNumLocalidades() {
		System.out.println("=====================================================================================");
		System.out.println("Teste aos getNumLocalidades =========================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc1 = new PercursoComposto("PC1", ps1, 20);
		pc1.print("> ");
		System.out.println("nº de localidades -> " + pc1.getNumLocalidades());
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc2 = new PercursoComposto("PC2", new Percurso[] { ps1, ps2 }, 20);
		pc2.print("> ");
		System.out.println("nº de localidades -> " + pc2.getNumLocalidades());
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc3 = new PercursoComposto("PC3", new Percurso[] { pc1 }, 20);
		pc3.print("> ");
		System.out.println("nº de localidades -> " + pc3.getNumLocalidades());
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc4 = new PercursoComposto("PC4", new Percurso[] { pc3 }, 20);
		pc4.print("> ");
		System.out.println("nº de localidades -> " + pc4.getNumLocalidades());
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc22 = new PercursoComposto("PC22", new Percurso[] { ps4, ps5 }, 20);
		pc22.print("> ");
		System.out.println("nº de localidades -> " + pc22.getNumLocalidades());
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc5 = new PercursoComposto("PC5", new Percurso[] { ps0, pc2, ps3, pc22 }, 20);
		pc5.print("> ");
		System.out.println("nº de localidades -> " + pc5.getNumLocalidades());
		System.out.println();

		System.out.println();
	}

	/**
	 * Teste ao getLocalidades
	 */
	private static void testGetLocalidades() {
		System.out.println("=====================================================================================");
		System.out.println("Teste aos getLocalidades ============================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc1 = new PercursoComposto("PC1", ps1, 20);
		pc1.print("> ");
		System.out.println("Localidades -> " + Arrays.toString(pc1.getLocalidades()));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc2 = new PercursoComposto("PC2", new Percurso[] { ps1, ps2 }, 20);
		pc2.print("> ");
		System.out.println("Localidades -> " + Arrays.toString(pc2.getLocalidades()));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc3 = new PercursoComposto("PC3", new Percurso[] { pc1 }, 20);
		pc3.print("> ");
		System.out.println("Localidades -> " + Arrays.toString(pc3.getLocalidades()));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc4 = new PercursoComposto("PC4", new Percurso[] { pc3 }, 20);
		pc4.print("> ");
		System.out.println("Localidades -> " + Arrays.toString(pc4.getLocalidades()));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc22 = new PercursoComposto("PC22", new Percurso[] { ps4, ps5 }, 20);
		pc22.print("> ");
		System.out.println("Localidades -> " + Arrays.toString(pc22.getLocalidades()));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc5 = new PercursoComposto("PC5", new Percurso[] { ps0, pc2, ps3, pc22 }, 20);
		pc5.print("> ");
		System.out.println("Localidades -> " + Arrays.toString(pc5.getLocalidades()));
		System.out.println();

		System.out.println();
	}

	/**
	 * Teste ao getIdxLocalidadesEmInicio
	 */
	private static void testGetIdxLocalidadeEmInicio() {
		System.out.println("=====================================================================================");
		System.out.println("Teste aos getIdxLocalidadeEmInicio ==================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc1 = new PercursoComposto("PC1", new Percurso[] { ps1, ps2 }, 20);
		pc1.print("> ");
		System.out.println();

		String local = "Faro";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc1.getIdxLocalidadeEmInicio(local));
		System.out.println();

		local = "Lisboa";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc1.getIdxLocalidadeEmInicio(local));
		System.out.println();

		local = "Coimbra";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc1.getIdxLocalidadeEmInicio(local));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc2 = new PercursoComposto("PC2", new Percurso[] { ps1, ps2 }, 20);
		pc2.print("> ");
		System.out.println();

		local = "Faro";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc2.getIdxLocalidadeEmInicio(local));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc3 = new PercursoComposto("PC3", new Percurso[] { pc1 }, 20);
		pc3.print("> ");
		System.out.println();

		local = "Faro";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc3.getIdxLocalidadeEmInicio(local));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc4 = new PercursoComposto("PC4", new Percurso[] { pc3 }, 20);
		pc4.print("> ");
		System.out.println();

		local = "Faro";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc4.getIdxLocalidadeEmInicio(local));
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		PercursoComposto pc22 = new PercursoComposto("PC22", new Percurso[] { ps4, ps5 }, 20);

		PercursoComposto pc5 = new PercursoComposto("PC5", new Percurso[] { ps0, pc2, ps3, pc22 }, 20);
		pc5.print("> ");
		System.out.println();

		local = "Faro";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc5.getIdxLocalidadeEmInicio(local));
		System.out.println();

		local = "Lisboa";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc5.getIdxLocalidadeEmInicio(local));
		System.out.println();

		local = "Porto";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc5.getIdxLocalidadeEmInicio(local));
		System.out.println();

		local = "Viana do Castelo";
		System.out.println("getIdxLocalidadeEmInicio " + local + " -> " + pc5.getIdxLocalidadeEmInicio(local));
		System.out.println();
	}

	/**
	 * Teste ao adicionarPercursoNoInicio
	 */
	private static void testAdicionarPercursoNoInicio() {
		System.out.println("=====================================================================================");
		System.out.println("Teste ao adicionarPercursoNoInicio ==================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		System.out.println("Original:");
		PercursoComposto pc1 = new PercursoComposto("PC1", new Percurso[] { ps3, ps4, ps5 }, 20);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no início:");
		ps2.print("> ");
		System.out.println();

		System.out.println("Resultado:");
		pc1.addicionarPercursoNoInicio(ps2);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no início:");
		PercursoComposto pc2 = new PercursoComposto("PC1", new Percurso[] { ps0, ps1 }, 20);
		pc2.print("> ");
		System.out.println();

		System.out.println("Resultado:");
		pc1.addicionarPercursoNoInicio(pc2);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar Percurso No Inicio com erro");
		System.out.println("Adicionar no início:");
		pc2.print("> ");
		System.out.println();

		boolean result = pc1.addicionarPercursoNoInicio(pc2);
		System.out.println("A adição de " + pc2 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		System.out.println();

	}

	/**
	 * Teste ao adicionarPercursoNofinal
	 */
	private static void testAdicionarPercursoNoFinal() {
		System.out.println("=====================================================================================");
		System.out.println("Teste ao adicionarPercursoNoFinal ===================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		System.out.println("Original:");
		PercursoComposto pc1 = new PercursoComposto("PC1", new Percurso[] { ps0 }, 20);
		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no Final:");
		ps1.print("> ");
		System.out.println();

		boolean result = pc1.addicionarPercursoNoFinal(ps1);
		System.out.println("A adição de " + ps1 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Teste ao adicionar Percurso No Final com erro.\nAdicionar:");
		ps3.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoNoFinal(ps3);
		System.out.println("A adição de " + ps3 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no Final:");
		PercursoComposto pc2 = new PercursoComposto("PC2",
				new PercursoComposto("PC2Aux", new Percurso[] { ps2, ps3 }, 20), 20);
		pc2.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoNoFinal(pc2);
		System.out.println("A adição de " + pc2 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no Final:");
		ps4.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoNoFinal(ps4);
		System.out.println("A adição de " + ps4 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no Final:");
		ps5.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoNoFinal(ps5);
		System.out.println("A adição de " + ps5 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Adicionar no Final com erro:");
		ps5.print("> ");
		System.out.println();

		result = pc1.addicionarPercursoNoFinal(ps5);
		System.out.println("A adição de " + ps5 + " deu -> " + result);
		System.out.println();

		pc1.print("> ");
		System.out.println();

	}

	/**
	 * Teste aos métodos de getDeclive, getDistancia e getSubidaAcumulada. Por
	 * fazer...
	 */
	private static void testeDecliveDistanciaSubidaAcumulada() {

		Percurso ps0, ps1, ps2, ps3, ps4;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);


		PercursoComposto pc2 = new PercursoComposto("PC2",new Percurso[]{ps0,ps1,ps2,ps3,ps4},20);

		System.out.println("O declive de PC2 é " + pc2.getDeclive());
		System.out.println("A subida acumulada de PC2 é " + pc2.getSubidaAcumulada());
		System.out.println("A distancia de PC2 é " + pc2.getDistancia());

		System.out.println();

	}

	/**
	 * Teste ao clone
	 */
	private static void testClone() {
		System.out.println("=====================================================================================");
		System.out.println("Teste aos clone =====================================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		// pc2
		PercursoComposto pc2 = new PercursoComposto("PC2", new Percurso[] { ps1, ps2 }, 20);

		// pc22
		PercursoComposto pc22 = new PercursoComposto("PC22", new Percurso[] { ps4, ps5 }, 20);

		// pc5
		System.out.println("Original:");
		PercursoComposto pc5 = new PercursoComposto("PC5", new Percurso[] { ps0, pc2, ps3, pc22 }, 20);
		pc5.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		System.out.println("Clone:");
		PercursoComposto pcClone = (PercursoComposto) (pc5.clone());
		pcClone.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println("Alterar percurso original com adicionar no final de pc22 dentro do original:");
		PercursoSimples psAux0 = new PercursoSimples("ww", "Valença", "Braga", 87_000, 30);
		psAux0.print("> ");
		System.out.println();

		pc22.addicionarPercursoNoFinal(psAux0);

		System.out.println("PC original alterado:");
		pc5.print("> ");
		System.out.println();

		System.out.println("PC clone (não deve estar alterado:");
		pcClone.print("> ");
		System.out.println();

	}

	/**
	 * Teste ao removerPercursoNoFinalDesde
	 */
	private static void testRemoverNoFinalDesde() {
		System.out.println("=====================================================================================");
		System.out.println("Teste ao removerNoFinalDesde ========================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5;

		ps0 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps1 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps2 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps3 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps4 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps5 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		// =====================================================================

		PercursoComposto pc1 = new PercursoComposto("PC1", new Percurso[] { ps0, ps1 }, 20);

		PercursoComposto pc11 = new PercursoComposto("PC11", new Percurso[] { pc1 }, 20);

		PercursoComposto pc111 = new PercursoComposto("PC111", new Percurso[] { pc11 }, 20);

		PercursoComposto pc2 = new PercursoComposto("PC2", new Percurso[] { ps2 }, 20);

		PercursoComposto pc45 = new PercursoComposto("PC45", new Percurso[] { ps4, ps5, }, 20);

		PercursoComposto pca = new PercursoComposto("PCA", new Percurso[] { pc111, pc2, ps3, pc45 }, 20);

		// =====================================================================
		System.out.println("Percurso original:");
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		String local = "Porto";
		System.out.println("Com remoção no final desde -> " + local);
		pca.removerPercursosNoFimDesde(local);
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		local = "Lisboa";
		System.out.println("Com remoção no final desde -> " + local);
		pca.removerPercursosNoFimDesde(local);
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		local = "Faro";
		System.out.println("Com remoção no final desde -> " + local);
		pca.removerPercursosNoFimDesde(local);
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		local = "Sagres";
		System.out.println("Com remoção no final desde -> " + local);
		pca.removerPercursosNoFimDesde(local);
		pca.print("> ");
		System.out.println();
	}

	/**
	 * Teste ao removerPercursoNoInicioAte
	 */
	private static void testRemoverNoInicioAte() {
		System.out.println("=====================================================================================");
		System.out.println("Teste ao removerNoInicioAte =========================================================");
		System.out.println();

		Percurso ps0, ps1, ps2, ps3, ps4, ps5, ps6;

		ps0 = new PercursoSimples("A23", "Aljezur", "Sagres", 42_900, -20);
		ps1 = new PercursoSimples("A23", "Sagres", "Faro", 67_000, -10);
		ps2 = new PercursoSimples("A2", "Faro", "Lisboa", 278_000, 10);
		ps3 = new PercursoSimples("A1 1", "Lisboa", "Coimbra", 204_000, 10);
		ps4 = new PercursoSimples("A1 2", "Coimbra", "Porto", 113_000, 20);
		ps5 = new PercursoSimples("A28", "Porto", "Viana do Castelo", 73_800, 30);
		ps6 = new PercursoSimples("N13", "Viana do Castelo", "Valença", 51_000, 30);

		// =====================================================================
		PercursoComposto pc1 = new PercursoComposto("PC1", new Percurso[] { ps1, ps2 }, 20);

		PercursoComposto pc11 = new PercursoComposto("PC11", new Percurso[] { pc1 }, 20);

		PercursoComposto pc111 = new PercursoComposto("PC111", new Percurso[] { pc11 }, 20);

		PercursoComposto pc2 = new PercursoComposto("PC2", new Percurso[] { ps3 }, 20);

		PercursoComposto pc45 = new PercursoComposto("PC45", new Percurso[] { ps5, ps6, }, 20);

		PercursoComposto pca = new PercursoComposto("PCA", new Percurso[] { ps0, pc111, pc2, ps4, pc45 }, 20);

		// =====================================================================
		System.out.println("Percurso original:");
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		String local = "Sagres";
		System.out.println("Com remoção no início até -> " + local);
		pca.removerPercursosNoInicioAte(local);
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		local = "Faro";
		System.out.println("Com remoção no início até -> " + local);
		pca.removerPercursosNoInicioAte(local);
		pca.print("> ");
		System.out.println();

		// =====================================================================
		System.out.println(strSeparator);
		local = "Lisboa";
		System.out.println("Com remoção no início até -> " + local);
		pca.removerPercursosNoInicioAte(local);
		pca.print("> ");
		System.out.println();

	}

	/**
	 * Espaço para mais testes
	 */
	private static void testExtra() {
		System.out.println("=====================================================================================");
		System.out.println("Testes extra ========================================================================");
		System.out.println();

	}

	/**
	 * Main, para testes
	 * 
	 * @param args
	 *            Argumentos do main
	 */
	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);

		boolean terminar = false;

		do {
			// mostrar opções
			System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #");
			System.out.println("a - Testar os contrutores");
			System.out.println("b - Testar getNumLocalidades");
			System.out.println("c - Testar getLocalidades");
			System.out.println("d - Testar Declive Distancia e SubidaAcumulada");
			System.out.println("e - Testar GetIdxLocalidadeEmInicio");
			System.out.println("f - Testar AdicionarPercursoNoInicio");
			System.out.println("g - Testar AdicionarPercursoNoFinal");
			System.out.println("h - Testar Clone");
			System.out.println("i - Testar RemoverNoFinalDesde");
			System.out.println("j - Testar RemoverNoInicioAte");
			System.out.println("k - Testes extra");
			System.out.println("x - Terminar");

			// ler escolha do utilizador
			char car = teclado.nextLine().trim().charAt(0);

			System.out.println();

			// executar pedido
			switch (Character.toLowerCase(car)) {
			case 'a':
				testConstrutores();
				break;
			case 'b':
				testNumLocalidades();
				break;
			case 'c':
				testGetLocalidades();
				break;
			case 'd':
				testeDecliveDistanciaSubidaAcumulada();
				break;
			case 'e':
				testGetIdxLocalidadeEmInicio();
				break;
			case 'f':
				testAdicionarPercursoNoInicio();
				break;
			case 'g':
				testAdicionarPercursoNoFinal();
				break;
			case 'h':
				testClone();
				break;
			case 'i':
				testRemoverNoFinalDesde();
				break;
			case 'j':
				testRemoverNoInicioAte();
				break;
			case 'k':
				testExtra();
				break;
			case 'x':
				terminar = true;
				System.out.println("Bye...");
				break;
			default:
				System.out.println("Opção inválida -> " + car);
			}
		} while (!terminar);

		teclado.close();
	}
}
