package pee.mecproc.mem;

import java.util.HashMap;
import java.util.Queue;

import pee.mecproc.No;
import plantraj.modProb.Estado;

public class MemoriaProcura {

	protected Queue<No> fronteira; 
	protected HashMap<Estado,No> explorados; 

	public MemoriaProcura(Queue<No> fronteira){
		this.fronteira = fronteira;
		this.explorados = new HashMap<Estado,No>();
		
	}

	public void inserir(No no) {

		Estado estado = no.getEstado();
		No noMem = explorados.get(estado);
		if(noMem == null) {
			fronteira.add(no);
			explorados.put(estado,no);
		}
	}

	public void limpar() {
		fronteira.clear();
		explorados.clear();
	}

	public No remover(){
		//remove o primeiro elemento da queue
		return fronteira.poll();
	}

	public boolean fronteiraVazia(){
		return fronteira.isEmpty();
	}

	public int getComplexidadeEspacial(){
		return fronteira.size();
	}
}