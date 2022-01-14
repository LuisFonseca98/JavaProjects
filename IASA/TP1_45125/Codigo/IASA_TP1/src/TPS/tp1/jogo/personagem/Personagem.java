package TPS.tp1.jogo.personagem;

import TPS.tp1.jogo.ambiente.Ambiente;
import TPS.tp1.jogo.personagem.comport.ComportPersonagem;
import TPS.tp1.jogo.reacao.Accao;
import TPS.tp1.jogo.reacao.Comportamento;
import TPS.tp1.jogo.reacao.Estimulo;

public class Personagem {
	
	private Ambiente ambiente;
	private ComportPersonagem comport;
	
	public Personagem(Ambiente ambiente) {
		this.ambiente = ambiente;
		comport = new ComportPersonagem();
	}
	
	public void executar() {
		if(percepcionar() != null) {
			atuar(processar(ambiente.getEvento()));
			mostrar();
		}
	}
	
	private Estimulo percepcionar() {
		return ambiente.getEvento();
		
	}
	
	private Accao processar(Estimulo estimulo) {
		return comport.activar(estimulo);
		
	}
	
	private void atuar(Accao accao) {
		if(accao != null) {
			accao.executar();
		}
	}
	
	private void mostrar() {
		System.out.println("Estado: " + comport.getEstado().getNome());
	}

}
