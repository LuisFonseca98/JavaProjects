package pee.mecproc;

import java.util.Iterator;
import java.util.LinkedList;

import pee.PassoSolucao;
import pee.Solucao;

public class Percurso implements Solucao{
	
	private LinkedList<PassoSolucao> percurso = new LinkedList<PassoSolucao>();
	
	@Override
	public int getDimensao(){
		//sendo a profundidade de cada No, incrementado em 1, podemos deduzir que
		//o comprimento da lista é o tamanho da dimensao da lista.
		if(percurso.isEmpty()){
			return 0;
		}
		return percurso.size();
	}
	
	@Override
	public double getCusto(){
		//visto que o ultimo No tem o custo do caminho todo, apenas se vê o custo desse mesmo
		if(percurso != null){
			return percurso.getLast().getCusto();
		}
		return -1.0;
	}
	
	@Override
	public Iterator<PassoSolucao> iterator(){
		return percurso.iterator();
	}
	
	public void juntarInicio(No no){
		percurso.addFirst(no);
	}



}

