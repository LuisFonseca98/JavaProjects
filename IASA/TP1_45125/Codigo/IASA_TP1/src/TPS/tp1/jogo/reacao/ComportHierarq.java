package TPS.tp1.jogo.reacao;

public class ComportHierarq implements Comportamento{

	private Comportamento[] comportamentos;

	public ComportHierarq(Comportamento[] comportamentos) {
		this.comportamentos = comportamentos;
	}
	
	/**
	 *metodo activar faz o varrimentos dos comportamentos
	 *obter a accao do comprotamento que activa o estimulo
	 *se nenhuma resposta for activada nao foi nada activado retorna nulo
	 */
	@Override
	public Accao activar(Estimulo estimulo) {
		for(Comportamento comportamento : comportamentos) {
			Accao accao = comportamento.activar(estimulo);
			if(accao != null) 
				return accao;
		}
		return null;
	}

}
