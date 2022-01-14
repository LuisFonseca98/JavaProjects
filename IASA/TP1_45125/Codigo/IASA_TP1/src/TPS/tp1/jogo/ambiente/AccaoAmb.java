package TPS.tp1.jogo.ambiente;

import TPS.tp1.jogo.reacao.Accao;

public enum AccaoAmb implements Accao{
	
		PATRULHAR,
		APROXIMAR,
		DEFENDER,
		ATACAR,
		PROCURAR,
		INICIAR;
	
	public void executar() {
		System.out.printf("Accao: %s\n", this);
	}

}
