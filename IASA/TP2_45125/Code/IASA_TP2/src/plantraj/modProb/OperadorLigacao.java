package plantraj.modProb;
public class OperadorLigacao implements Operador{
	
	private int custoLigacao;
	private EstadoLocalidade estadoOrigem,estadoDestino;
	
	public OperadorLigacao(String locini, String locfin, int custo){
		estadoDestino = new EstadoLocalidade(locfin);
		estadoOrigem = new EstadoLocalidade(locini);
		custoLigacao = custo;
	}
	
	@Override
	public Estado aplicar(Estado estado){
		if(estado.equals(estadoOrigem)){
			return estadoDestino;
		}
		return null;
	}
	
	@Override
	public float custo(Estado estado, Estado estado_suc){	
		return custoLigacao;
	}

}

