package tps.tp3;

/**
 * Classe que suporta um percurso simples
 *
 */
public class PercursoSimples extends Percurso {

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
	 * Constructor. Deve validar o nome, inicio e fim com o m�todo de valida��o
	 * validarNomeDelocal. A dist�ncia tem de ser positiva (>0)
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
	public PercursoSimples(String nome, String inicio, String fim,
			int distancia, int declive) {
		
		super(nome);
		
		if(!validarNome(inicio)){
			throw new IllegalArgumentException("Inicio inv�lido");
		}
		
		if(!validarNome(fim)){
			throw new IllegalArgumentException("Fim inv�lido");
		}
		
		if(distancia < 0){
			throw new IllegalArgumentException("Distancia inferior a zero");
		}
		
		this.inicio = inicio;
		this.fim = fim;
		this.distancia = distancia;
		this.declive = declive;
		
	}

	/**
	 * Construtor de c�pia, deve chamar o constructor acima
	 * 
	 * @param p
	 *            O percurso a copiar
	 */
	public PercursoSimples(PercursoSimples p) {
		
		super(p.getNome());

		this.inicio = p.inicio;
		this.fim = p.fim;
		this.distancia = p.distancia;
		this.declive = p.declive;
	}

	/**
	 * Deve criar uma c�pia do percurso recebido
	 */
	public PercursoSimples clone() {

		return new PercursoSimples(this);
	}

	/**
	 * Devolve o in�cio do percurso
	 */
	public String getInicio() {
		
		return inicio; 
	}

	/**
	 * Devolve o fim do percurso
	 */
	public String getFim() {
		
		return fim;
	}

	/**
	 * Devolve a dist�ncia do percurso
	 */
	public int getDistancia() {
		
		return distancia;
	}

	/**
	 * Devolve o declive do percurso
	 */
	public int getDeclive() {
		
		return declive;
	}

	/**
	 * Equals, deve devolver true se o percurso � do tipo PercursoSimples, se
	 * tem o mesmo nome, o mesmo in�cio e o mesmo fim.
	 * 
	 * @param percurso
	 *            Percurso a comparar
	 */
	public boolean equals(Object percurso) {
		if(percurso instanceof PercursoSimples){
			if(getNome().equals(((PercursoSimples) percurso).getNome()) && inicio.equals(((PercursoSimples) percurso).getInicio()) && fim.equals(((PercursoSimples) percurso).getFim())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Main, para realizar testes aos m�todos
	 */
	public static void main(String[] args) {
		PercursoSimples ps1 = new PercursoSimples("A2", "Lisboa", "Faro",278_000, 0);
		ps1.print("ps1 -> ");

		PercursoSimples ps2 = new PercursoSimples("A1", "Lisboa", "Porto",317_000, 0);
		ps2.print("ps2 -> ");

		boolean ps1ps2 = ps1.equals(ps2);
		System.out.println("ps1.equals(ps2) -> " + ps1ps2);

		System.out.println("ps1 toString -> " + ps1);
	}

//	@Override
	public String[] getLocalidades() {
		return new String[] { getInicio(), getFim() };
	}

	public String getDescricao() {
		return "simples";
	}

}
