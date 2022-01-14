package TPS.tp1.jogo;

import TPS.tp1.jogo.ambiente.Ambiente;
import TPS.tp1.jogo.ambiente.EventoAmb;
import TPS.tp1.jogo.personagem.Personagem;

public class Jogo {
	
	private static Ambiente ambiente = new Ambiente();
	private static Personagem personagem = new Personagem(ambiente);
	
	private static void executar() {
		do {
			personagem.executar();
			ambiente.evoluir();
		}while(ambiente.getEvento() != EventoAmb.TERMINAR);
	}
	
	public static void main(String[] args) {
		executar();
	}

}
