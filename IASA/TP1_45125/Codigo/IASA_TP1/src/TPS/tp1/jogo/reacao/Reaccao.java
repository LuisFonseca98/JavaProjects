package TPS.tp1.jogo.reacao;

public class Reaccao implements Comportamento {
	
	private Estimulo estimulo;
	private Accao resposta;
	
	public Reaccao(Estimulo estimulo, Accao resposta) {
		this.estimulo = estimulo;
		this.resposta = resposta;
	}
	
	@Override
	public Accao activar(Estimulo estimulo) {
		return (this.estimulo == estimulo)?this.resposta:null;
	}

}
