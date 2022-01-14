package TPS.tp1.jogo.reacao;

import java.util.HashMap;
import java.util.Map;

import TPS.tp1.jogo.maqest.Estado;
import TPS.tp1.jogo.maqest.MaquinaEstados;

public abstract class ComportMaqEst implements Comportamento{
	
	private MaquinaEstados<Estimulo> maqEst;
	private Map<Estado<Estimulo>, Comportamento> comportamentos;
	private Estado<Estimulo>estado;
	
	public ComportMaqEst() {
		comportamentos = new HashMap<Estado<Estimulo>,Comportamento>();
		maqEst = iniciar();
		estado = maqEst.getEstado();		
	}
	
	public Estado<Estimulo> getEstado(){
		return this.estado;
	}
	
	protected abstract MaquinaEstados<Estimulo> iniciar();
	
	public ComportMaqEst comport(Estado<Estimulo> estado, Comportamento comport) {
		comportamentos.put(estado, comport);
		return this;
	}

	@Override
	public Accao activar(Estimulo estimulo) {
		Comportamento comp = comportamentos.get(maqEst.getEstado());
		maqEst.processar(estimulo);
		estado = maqEst.getEstado();
		return comp.activar(estimulo);
	}

}