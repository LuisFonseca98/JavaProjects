package pee.prof;


import pee.Solucao;
import plantraj.modProb.Problema;

public class ProcuraProfIter extends ProcuraProf{

	private int incProf;
	
	public ProcuraProfIter(int incProf) {
		this.incProf = incProf;
	}

	public int getIncProf(){
		
		return incProf;
	}
	
	public void setIncProf(int incProf){
		this.incProf = incProf;
	}
	
	public Solucao resolver(Problema problema, int profMax){
		
		for(int profMaxIt = getIncProf(); profMaxIt<=profMax; profMaxIt+=getIncProf()){
			Solucao solucao = super.resolver(problema,profMax);
			if(solucao != null){
				return solucao;
			}
		}
		return null;
	}
	

}
