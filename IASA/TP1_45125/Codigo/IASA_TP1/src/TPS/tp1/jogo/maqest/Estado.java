package TPS.tp1.jogo.maqest;

import java.util.HashMap;
import java.util.Map;

import TPS.tp1.jogo.reacao.Estimulo;

public class Estado<EV>{
	
	private Map<EV, Estado<EV>> transicoes;
	private String nome;
	
	public Estado(String nome) {
		this.nome = nome;
		transicoes = new HashMap<EV,Estado<EV>>();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Estado<EV> transicao(EV evento, Estado<EV> estado) {
		transicoes.put(evento, estado);
		return this;//retornar a instancia do objeto para o encadeamento fazer o encadeamento de metodos
	}
	
	public Estado<EV> processar(EV evento) {
		return transicoes.get(evento);
	}
	
	public String toString() {
		return getNome();
	}

}
