package plantraj.puzzleResolve;
import pee.PassoSolucao;
import pee.Solucao;
import pee.larg.ProcuraLarg;
import pee.mecproc.MecanismoProcura;
import pee.melhorprim.ProcuraAA;
import pee.melhorprim.ProcuraCustoUnif;
import pee.melhorprim.ProcuraSofrega;
import pee.prof.ProcuraProf;
import pee.prof.ProcuraProfIter;
import plantraj.modProb.Problema;
import plantraj.modProb.ProblemaHeur;
import puzzle.Puzzle;
import puzzle.Puzzle.Movimento;

public class PlaneadorPuzzle {
	
	private static Puzzle PuzzleInicialA = new Puzzle(new int[][] {
		{1,2,3},{8,4,5},{6,7,0}
	});
	
	private static Puzzle PuzzleInicialB = new Puzzle(new int[][] {
		{8,4,5},{6,1,2},{3,7,0}
	});
	
	private static Puzzle PuzzleFinal = new Puzzle(new int[][] {
		{1,2,3},{4,5,6},{7,8,0}
	});
	
	private static ProcuraProf prof = new ProcuraProf();
	private static ProcuraLarg larg = new ProcuraLarg();
	private static ProcuraProfIter iter = new ProcuraProfIter(1);
	private static ProcuraCustoUnif custoUnif = new ProcuraCustoUnif();
	
	
	//problemas heuristicas
	private static MecanismoProcura<ProblemaHeur> pAA = new ProcuraAA();
	private static MecanismoProcura<ProblemaHeur> pSof = new ProcuraSofrega();

	//problemasIniciais(puzzles A e B)
	private static ProblemaPuzzle problemaA = new ProblemaPuzzle(PuzzleInicialA, PuzzleFinal, definirOperacoesPuzzle());
	//private static ProblemaPuzzle problemaB = new ProblemaPuzzle(PuzzleInicialB, PuzzleFinal, definirOperacoesPuzzle());
	//private static ProblemaPuzzle problemaC = new ProblemaPuzzle(PuzzleInicialA, PuzzleInicialB, definirOperacoesPuzzle());

	
	private static OperadorPuzzle[] definirOperacoesPuzzle() {
		OperadorPuzzle[] operadoresPuzzle = {
			new OperadorPuzzle(Movimento.CIMA),
			new OperadorPuzzle(Movimento.BAIXO),
			new OperadorPuzzle(Movimento.ESQ),
			new OperadorPuzzle(Movimento.DIR),
		};
		return operadoresPuzzle;
	}
	
	public static void main(String[] args) {
		MecanismoProcura<Problema> mecanismoAtual = iter;
		//MecanismoProcura<ProblemaHeur> mecanismoAtual = pSof;
		Solucao sA = mecanismoAtual.resolver(problemaA);
		//Solucao sB = mecanismoAtual.resolver(problemaB);
		//Solucao sC = mecanismoAtual.resolver(problemaC);
		int compEspacial = mecanismoAtual.getComplexidadeEspacial();
		int compTemporal = mecanismoAtual.getComplexidadeTemporal();
		showPuzzle(sA);
		System.out.println();
		String [] procura = mecanismoAtual.getClass().toString().split("\\.");
		System.out.printf("Procura por -> %s",procura[procura.length-1]);
		System.out.println();
		System.out.printf("Complexidade Temporal (expandidos): %d",compTemporal);
		System.out.println();
		System.out.printf("Complexidade Espacial (fronteira): %d",compEspacial);
		System.out.println();
		System.out.printf("Custo (Número Movimentos): %d",(int)sA.getCusto());
		System.out.println();
		
	}
	
	
	private static void showPuzzle(Solucao solucao) {
		System.out.println();
		for(PassoSolucao s: solucao) {
			System.out.println(s.getEstado());
			System.out.println();
		}
	}
		

}
