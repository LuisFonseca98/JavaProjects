package plantraj.modProb;


public class ProblemaPlanTraj extends Problema{
	
	private EstadoLocalidade estadoFinal;

	public ProblemaPlanTraj(String locini, String locfin, OperadorLigacao[] operadores) {
		super(new EstadoLocalidade(locini),operadores);
		estadoFinal = new EstadoLocalidade(locfin);
	}
	
	@Override
	public boolean objectivo(Estado estado) {
		return (estado.equals(estadoFinal)? true : false);
	}

}
