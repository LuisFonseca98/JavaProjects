package TPS.tp1.jogo.personagem.comport;

import TPS.tp1.jogo.ambiente.AccaoAmb;
import TPS.tp1.jogo.ambiente.EventoAmb;
import TPS.tp1.jogo.reacao.ComportHierarq;
import TPS.tp1.jogo.reacao.Comportamento;
import TPS.tp1.jogo.reacao.Reaccao;

public class Combater extends ComportHierarq{

	public Combater() {
		super(new Comportamento[] {
				new Reaccao(EventoAmb.INIMIGO, AccaoAmb.ATACAR),
				new Reaccao(EventoAmb.FUGA, AccaoAmb.PATRULHAR),
				new Reaccao(EventoAmb.VITORIA, AccaoAmb.PATRULHAR),
				new Reaccao(EventoAmb.DERROTA, AccaoAmb.INICIAR)
		});
	}

}
