package TP1;

/**
 *Classe onde é constituida o corpo da nossa mensagem
 *id:inteiro não negativo. Número sequencial, iniciado com o valor 0 (zero), que
 *permite saber ordenar as mensagens que são envidas pelo canal de comunicação
 * 
 *Tipo:  inteiro não negativo. Número cujo contexto é definido ao nível da aplicação
 *e é utilizado para filtrar as mensagens a receber e/ou enviar. O valor 0 (zero) significa
 *todas as mensagens; 
 *  
 *Texto – String com um máximo de 256 carateres. 
 *
 */
public class Mensagem {

	private int id,tipo;
	private String texto;

	/**
	 * Construtor que é necessario para passar o id, o tipo e o texto, quando o 
	 * outro processo receber a mensagem
	 * @param id
	 * @param tipo
	 * @param texto
	 */
	public Mensagem(int id, int tipo, String texto) {
		this.id = id;
		this.tipo = tipo;
		this.texto = texto;
	}

	/**
	 * Construtor que inicializa os diferentes atributos
	 */
	public Mensagem() {
		id = 0;
		tipo = 0;
		texto = null;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		//System.out.println("TOU A METER O TIPO: "+ tipo);
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
