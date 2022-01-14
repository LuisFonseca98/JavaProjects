package plantraj.modProb;

import java.util.ArrayList;

import pee.PassoSolucao;
import pee.Solucao;
import pee.larg.ProcuraLarg;
import pee.mecproc.MecanismoProcura;
import pee.melhorprim.ProcuraCustoUnif;
import pee.prof.ProcuraProf;
import pee.prof.ProcuraProfIter;

public class PlaneadorTrajeto {
	
	static ProblemaPlanTraj problema = new ProblemaPlanTraj("Loc-0", "Loc-6", definirOperadores());
	static ProcuraProf pprof = new ProcuraProf();
	static ProcuraLarg plarg = new ProcuraLarg();
	static ProcuraProfIter pprofIt = new ProcuraProfIter(1);
	static ProcuraCustoUnif pcustoUnif = new ProcuraCustoUnif();
	
	static ArrayList<MecanismoProcura<Problema>> procuras = new ArrayList<MecanismoProcura<Problema>>(){
		private static final long serialVersionUID = 1L;
	{add(plarg);add(pprof);add(pprofIt);add(pcustoUnif);}};
	
	public static void main(String []args){
		Solucao sol;
		MecanismoProcura<Problema> currentMecanismo;
		String nome = "";
		for(int i=0; i<procuras.size();i++){
			currentMecanismo = procuras.get(i);
			sol = currentMecanismo.resolver(problema);
			nome = currentMecanismo.getClass().toString();
			System.out.println();
			System.out.printf("Procura por %s",(nome.substring(15)));
			System.out.println();
			System.out.printf("Nº transições : %d | ",sol.getDimensao());
			System.out.printf("Custo: %1$.1f",sol.getCusto());
			mostrarTrajeto(sol);
		}
	}
	
	private static OperadorLigacao[] definirOperadores(){
		return new OperadorLigacao[]{
				new OperadorLigacao("Loc-0","Loc-1",5),
				new OperadorLigacao("Loc-0","Loc-2",25),
				new OperadorLigacao("Loc-1","Loc-3",12),
				new OperadorLigacao("Loc-1","Loc-6",5),
				new OperadorLigacao("Loc-2","Loc-4",30),
				new OperadorLigacao("Loc-3","Loc-2",10),
				new OperadorLigacao("Loc-3","Loc-5",5),
				new OperadorLigacao("Loc-4","Loc-3",2),
				new OperadorLigacao("Loc-5","Loc-6",8),
				new OperadorLigacao("Loc-5","Loc-4",10),
				new OperadorLigacao("Loc-6","Loc-3",15),	
		};
	}
	
	private static void mostrarTrajeto(Solucao solucao){
		String trans = "";
		System.out.println();
		int aux=0;
		if(solucao != null){
			for(PassoSolucao passo : solucao){
				aux++;
				trans += passo.getEstado() + (solucao.getDimensao() > aux ? " -> "  : "");
			}
			System.out.printf("Transições: %s",trans);
			System.out.println();
		}
	}
}