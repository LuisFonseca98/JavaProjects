package plantraj.puzzleResolve;


import plantraj.modProb.Estado;
import plantraj.modProb.Operador;
import plantraj.modProb.ProblemaHeur;
import puzzle.Puzzle;

/**
 *  
 *CLASSE QUE APLICA O PROBLEMA DE HEUR AO PUZZLE
 *
 *
 */

public class ProblemaPuzzle extends ProblemaHeur {
	
	private EstadoPuzzle puzzleFinal;

	/**
	 * Construtor que cria um puzzle, passando o seu estado inicial e final
	 * @param puzzleInicial
	 * @param puzzleFinal
	 * @param operadores
	 */
	public ProblemaPuzzle(Puzzle puzzleInicial,Puzzle puzzleFinal,Operador[] operadores) {
		super(new EstadoPuzzle(puzzleInicial), operadores);
		this.puzzleFinal = new EstadoPuzzle(puzzleFinal);
	}

	/**
	 * metodo heuristica que aplica a distancia de manhatan
	 * ao nosso puzzle, lendo o seu estado atual
	 */
	@Override
	public double heuristica(Estado estado) {
		EstadoPuzzle estadoAtualPuzzle = (EstadoPuzzle) estado;
		double distManPuzzle = estadoAtualPuzzle.getPuzzle().distManhattan(puzzleFinal.getPuzzle());
		return distManPuzzle;
	}
	
	/**
	 * Metodo que retorna o objetivo do puzzle, neste caso
	 * quando deixa de existir pecas no puzzle
	 */
	@Override
	public boolean objectivo(Estado estado) {
		//CASO O NUMERO DE PECAS SEJA 0, O PUZZLE ACABA
		return estado.equals(puzzleFinal) ? true : false;
	}
}
