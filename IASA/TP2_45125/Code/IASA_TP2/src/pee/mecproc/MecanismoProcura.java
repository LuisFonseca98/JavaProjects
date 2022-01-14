package pee.mecproc;

import pee.Solucao;
import pee.mecproc.mem.MemoriaProcura;
import plantraj.modProb.Estado;
import plantraj.modProb.Operador;
import plantraj.modProb.Problema;

public abstract class MecanismoProcura <P extends Problema>{
	private MemoriaProcura memoria_procura;
	protected P problema;
	private No no_inicial,no;
	private int complexidadeTemporal;
	
	public MecanismoProcura(){
		this.problema = null;
		this.no_inicial = null;
		this.no = null;
		this.memoria_procura = iniciarMemoria();
		complexidadeTemporal = 0;
	}
	
	public Solucao resolver(P problema){
		//o integer é sempre o maximo possivel
		return resolver(problema,Integer.MAX_VALUE);
	}
	
	public Solucao resolver(P problema, int profMax){
		this.problema = problema;
		memoria_procura.limpar();
		no_inicial = new No(problema.getEstadoInicial());
		memoria_procura.inserir(no_inicial);
		
		while (!memoria_procura.fronteiraVazia()){
			no = memoria_procura.remover();
			if(problema.objectivo(no.getEstado())){
				return gerarSolucao(no);
			}else{
				if(no.getProfundidade() <= profMax){
					expandir(no);
					
				}
			}
		}
		return null;
	}
	
	private void expandir(No no){
		Estado estadoSuc = null;
		No noSuc = null;
		Estado estado = no.getEstado();
		Operador[] operadores = problema.getOperadores();
		complexidadeTemporal++;
		for(Operador operador : operadores){
			estadoSuc = operador.aplicar(estado);
			if(estadoSuc != null){
				noSuc = new No(estadoSuc, operador, no);
				memoria_procura.inserir(noSuc);
				
			}
		}
	}
	
	private Solucao gerarSolucao(No noFinal){
		
		Percurso percurso = new Percurso();
		no = noFinal;
		No antecessor = null;
		
		while(no != null){
			percurso.juntarInicio(no);
			antecessor = no.getAntecessor();
			no = antecessor;
		}
		
		return percurso;
	}
	
	protected abstract MemoriaProcura iniciarMemoria();
	
	public int getComplexidadeTemporal(){
		return complexidadeTemporal;
	}
	
	public int getComplexidadeEspacial(){
		return memoria_procura.getComplexidadeEspacial();
	}
}