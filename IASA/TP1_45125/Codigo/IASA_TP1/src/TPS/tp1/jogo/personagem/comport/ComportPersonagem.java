package TPS.tp1.jogo.personagem.comport;

import TPS.tp1.jogo.ambiente.EventoAmb;
import TPS.tp1.jogo.maqest.Estado;
import TPS.tp1.jogo.maqest.MaquinaEstados;
import TPS.tp1.jogo.reacao.ComportMaqEst;
import TPS.tp1.jogo.reacao.Estimulo;

public class ComportPersonagem extends ComportMaqEst{
	
	public ComportPersonagem() {
		
	}
	
	protected MaquinaEstados<Estimulo> iniciar(){
		//Definir estados
		Estado<Estimulo> patrulha = new Estado<Estimulo>("Patrulha");
		Estado<Estimulo> inspeccao = new Estado<Estimulo>("Inspecção");
		Estado<Estimulo> defesa = new Estado<Estimulo>("Defesa");
		Estado<Estimulo> combate = new Estado<Estimulo>("Combate");
		
		//Definir transições
		patrulha
			.transicao(EventoAmb.INIMIGO, defesa)
			.transicao(EventoAmb.RUIDO, inspeccao)
			.transicao(EventoAmb.SILENCIO, patrulha);
		
		inspeccao
			.transicao(EventoAmb.INIMIGO, defesa)
			.transicao(EventoAmb.RUIDO, inspeccao)
			.transicao(EventoAmb.SILENCIO, patrulha);
		
		defesa
			.transicao(EventoAmb.INIMIGO, combate)
			.transicao(EventoAmb.FUGA, inspeccao);
		
		combate
			.transicao(EventoAmb.INIMIGO, combate)
			.transicao(EventoAmb.FUGA, patrulha)
			.transicao(EventoAmb.VITORIA, patrulha)
			.transicao(EventoAmb.DERROTA, patrulha);
		
		//Definir comportamentos
		comport(patrulha, new Patrulhar());
		comport(inspeccao, new Inspeccionar());
		comport(defesa, new Defender());
		comport(combate, new Combater());
		
		//Iniciar Máquina de Estados
		return new MaquinaEstados<Estimulo>(patrulha);
	}


}
