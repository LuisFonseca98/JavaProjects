package plantraj.modProb;

public abstract class Problema {
	
	private Operador[] operadores;
	private Estado estadoInicial;
	
	public Problema(Estado estadoinicial, Operador[] operadores){
		this.operadores = operadores;
		this.estadoInicial = estadoinicial;
	}
	
	public Estado getEstadoInicial(){
		return estadoInicial;	
	}
	
	public Operador[] getOperadores(){
		return operadores;
	}
	
	public abstract boolean objectivo (Estado estado);

}
