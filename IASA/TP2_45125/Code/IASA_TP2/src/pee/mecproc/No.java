package pee.mecproc;

import pee.PassoSolucao;
import plantraj.modProb.Estado;
import plantraj.modProb.Operador;

public class No implements PassoSolucao{
	
	private int profundidade=0;
	private double custo=0.0;
	private Estado estado;
	private Operador operador;
	private No antecessor;
	
	public No(Estado estado){
		this.estado = estado;
	}
	
	public No(Estado estado, Operador operador, No antecessor){
		this(estado);
		this.operador = operador;
		this.antecessor = antecessor;
		custo = operador.custo(antecessor.getEstado(), estado) + antecessor.getCusto();
		profundidade = this.antecessor.getProfundidade()+1;
	}
	
	public Estado getEstado(){
		return estado;
	}
	
	public No getAntecessor(){
		
		return antecessor;
	}
	
	public int getProfundidade(){
		return profundidade;
	}

	@Override
	public Operador getOperador() {
		return operador;
	}

	@Override
	public double getCusto() {
		return custo;
	}
	
}

