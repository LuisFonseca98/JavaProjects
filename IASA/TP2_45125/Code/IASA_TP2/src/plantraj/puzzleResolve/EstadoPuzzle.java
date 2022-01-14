package plantraj.puzzleResolve;
import plantraj.modProb.Estado;
import puzzle.Puzzle;
/**
 * @author luisc
 * RETORNA UM ESTADO NO PUZZLE
 * Classe semelhante ao estado da localidade
 *
 */
public class EstadoPuzzle extends Estado{
	
	public Puzzle puzzle;
	/**
	 * Construtor que inicia o estado do puzzle
	 * @param puzzle
	 */
	public EstadoPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	@Override
	public int hashCode() {
		return puzzle.hashCode();
	}
	
	public String toString() {
		return puzzle.toString();
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}

}
