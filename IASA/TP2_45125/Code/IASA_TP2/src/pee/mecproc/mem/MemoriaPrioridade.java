package pee.mecproc.mem;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import pee.mecproc.No;
import plantraj.modProb.Estado;


public class MemoriaPrioridade extends MemoriaProcura{
	
	protected HashMap<Estado,No> explorados; 
	
	public MemoriaPrioridade(Comparator<No> comparador) {
		super(new PriorityQueue<No>(1,comparador));
		this.explorados = new HashMap<Estado,No>();
	}
	
	@Override
    public void inserir(No no) {
       
        Estado estado = no.getEstado();
        No noMem = explorados.get(estado);
        if(noMem == null || noMem != null && no.getCusto() < noMem.getCusto()) {
            fronteira.add(no);
            explorados.put(estado,no);
        }
    }

}