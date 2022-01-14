package TPS.tp1.jogo.maqest;

public class MaquinaEstados<EV>{
	/**
	 *tipo especifico(EV) da Maquina estados está a ser mapeado no tipo especifico no
	 *EV do estado
	 */
	private Estado<EV> estado;
	
	public MaquinaEstados(Estado<EV> estado) {
		this.estado = estado;
	}
	
	public Estado<EV> getEstado() {
		return this.estado;
	}
	
	/**
	 *para o estado da maquina de estados ir ao evento processar e produz
	 *novo estado se existir novo estado muda de estado senao mantem
	 * @param evento
	 */
	public void processar(EV evento) {
		Estado<EV> novoEstado = estado.processar(evento);
		if(novoEstado != null) 
			estado = novoEstado;
	}
	

}
