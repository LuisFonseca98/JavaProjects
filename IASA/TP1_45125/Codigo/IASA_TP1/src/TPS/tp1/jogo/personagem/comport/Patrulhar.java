package TPS.tp1.jogo.personagem.comport;

import TPS.tp1.jogo.ambiente.AccaoAmb;
import TPS.tp1.jogo.ambiente.EventoAmb;
import TPS.tp1.jogo.reacao.ComportHierarq;
import TPS.tp1.jogo.reacao.Comportamento;
import TPS.tp1.jogo.reacao.Reaccao;

public class Patrulhar extends ComportHierarq{

	public Patrulhar() {
		super(new Comportamento[] {
				new Reaccao(EventoAmb.SILENCIO, AccaoAmb.PATRULHAR),
				new Reaccao(EventoAmb.INIMIGO, AccaoAmb.APROXIMAR),
				new Reaccao(EventoAmb.RUIDO, AccaoAmb.APROXIMAR)
		});
	}

}
