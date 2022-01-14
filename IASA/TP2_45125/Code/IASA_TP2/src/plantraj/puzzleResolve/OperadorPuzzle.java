package plantraj.puzzleResolve;


import plantraj.modProb.Estado;
import plantraj.modProb.Operador;
import puzzle.Puzzle;
import puzzle.Puzzle.Movimento;
/**
 * 
 * @author luisc
 * CLASSE OPERADOR PUZZLE, QUE REALIZA UMA OPERACAO CONSOANTE UMA JOGADA
 * 
 */

public class OperadorPuzzle implements Operador {
	
	private float custoJogada;
	private Movimento movimento;
	
	public OperadorPuzzle(Movimento movimento) {
		this.movimento = movimento;
		this.custoJogada = 1;
	}

	
	/**
	 * Aplica uma jogada no puzzle
	 * Caso o puzzle nao 
	 */
	@Override
	public Estado aplicar(Estado estado) {
		Puzzle puzzle, newPuzzle;
		if(estado instanceof EstadoPuzzle) {
			puzzle = ((EstadoPuzzle) estado).getPuzzle();
			newPuzzle = puzzle.movimentar(movimento);
			if(newPuzzle != null) {
				return new EstadoPuzzle(newPuzzle);
			}
		}
		return null;
	}

	@Override
	public float custo(Estado estado, Estado estadoSuc) {
		return custoJogada;
	}

}
