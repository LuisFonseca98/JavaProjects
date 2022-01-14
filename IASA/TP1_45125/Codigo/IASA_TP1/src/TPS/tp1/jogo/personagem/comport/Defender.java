package TPS.tp1.jogo.personagem.comport;

import TPS.tp1.jogo.ambiente.AccaoAmb;
import TPS.tp1.jogo.ambiente.EventoAmb;
import TPS.tp1.jogo.reacao.ComportHierarq;
import TPS.tp1.jogo.reacao.Comportamento;
import TPS.tp1.jogo.reacao.Reaccao;

public class Defender extends ComportHierarq{

	public Defender() {
		super(new Comportamento[] {
				new Reaccao(EventoAmb.FUGA, AccaoAmb.PROCURAR),
				new Reaccao(EventoAmb.INIMIGO, AccaoAmb.DEFENDER),
		});
	}

}
