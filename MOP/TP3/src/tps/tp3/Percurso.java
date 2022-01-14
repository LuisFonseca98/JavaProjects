package tps.tp3;

/**
 * Classe abstracta Percurso, � a classe base de todos os percursos.
 * 
 * Devem terminar a classe colocando os m�todos em falta.
 */
public abstract class Percurso implements IPercurso {

	/**
	 * nome do percurso, deve conter s� letras, digitos e espa�os, deve come�ar
	 * por uma letra e ter pelo menos mais uma letra ou d�gito
	 */	
	private String local;

	public Percurso(String nome) {

		if(nome != null){
			if(nome.length() != 0){
				if(nome.substring(0, 1).matches("[A-Za-z]")){
					if(nome.substring(1, 2).matches("[A-Za-z]{1}") || nome.substring(1, 2).matches("[0-9]{1}")){
						this.local = nome;
					}
					else
						throw new IllegalArgumentException("Tem de ter pelo menos mais 1 letra ou digito");
				}
				else
					throw new IllegalArgumentException("N�o come�a por letra");
			}
			else
				throw new IllegalArgumentException("Nome nao tem comprimento");
		}
		else
			throw new IllegalArgumentException("Nome � null");

	}
	/**
	 * Devolve o nome do percurso
	 */
	public String getNome() {
		return this.local;
	}

	public abstract String getInicio();

	public abstract String getFim();

	public abstract int getDistancia();

	public abstract int getDeclive();

	public abstract String[] getLocalidades();

	/**
	 * Deve validar se cont�m s� letras, digitos e espa�os, deve come�ar por uma
	 * letra e ter pelo menos mais uma letra ou d�gito
	 * 
	 * @param local
	 *            Nome a validar
	 */
	protected static boolean validarNome(String local) {
		if(local != null){
			if(local.length() != 0){
				if(local.substring(0, 1).matches("[A-Za-z]")){
					if(local.substring(1, 2).matches("[A-Za-z]{1}") || local.substring(1, 2).matches("[0-9]{1}")){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * ToString, deve devolver uma String tal como: "A2 de Lisboa para Faro, com
	 * 278000 metros e com 0 de declive"
	 */
	public String toString() {
		return "percurso " + getDescricao() + " " + getNome() + " de "
				+ getInicio() + " para " + getFim() + ", com " + getDistancia()
				+ " metros e com " + getDeclive() + " de declive";
	}

	public abstract String getDescricao();

	/**
	 * Print, deve imprimir na consola o prefixo seguido da informa��o que se
	 * obt�m com o toString
	 * 
	 * @param prefix
	 *            Prefixo a colocar antes da informa��o do toString
	 */
	public void print(String prefix) {
		System.out.println(prefix + this);
	}


}