package plantraj.modProb;

public abstract class ProblemaHeur extends Problema{
	
	public ProblemaHeur(Estado estadoInicial, Operador[] operadores){
		super(estadoInicial,operadores);
	}
	
	public abstract double heuristica(Estado estado);

}
