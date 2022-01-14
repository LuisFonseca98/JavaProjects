package tps.tp1.pack3Arrays;

import java.util.Random;
import java.util.Scanner;

public class P04Xadrez {
	
	

	/**
	 * Conceba o programa P04Xadrez que, no contexto de um tabuleiro de xadrez,
	 * posicione de forma aleatória uma torre, um bispo e um cavalo, mas de
	 * forma a não haver ataques entre as peças. Assim para cada peça deve-se
	 * guardar as suas coordenadas x e y e comparar se há ataque com as peças já
	 * processadas. A comparação deve ser realizada somente com base nas
	 * coordenadas das peças. Em caso de ataque deve gerar novas coordenadas e
	 * voltar a testar, até não haver qualquer ataque. No final deve-se mostrar
	 * o tabuleiro em que a torre é visualizada com um ‘T’, o bispo com um ‘B’,
	 * o cavalo com um ‘C’, cada posição que não sofra qualquer ataque deve
	 * conter um ‘o’, cada posição com um só ataque deve conter um ‘-‘, e cada
	 * posição com pelo menos dois ataques deve conter um ‘+’. O programa deverá
	 * gerar e mostrar uma nova configuração válida quando, e sempre que, se
	 * premir a tecla de Enter. O código deverá definir a dimensão do tabuleiro
	 * numa variável (final) e estar preparado para funcionar para qualquer
	 * valor (>4) dessa variável. Conceba e utilize os métodos estáticos para a
	 * deteção dos ataques e impressão do tabuleiro.
	 * 
	 * Não é permitido a utilização de variáveis estáticas (variáveis static da
	 * classe)
	 */
	public static void main(String[] args) {
		// TODO ...
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Seja bem vindo ao jogo de xadrez");
		System.out.println("Por favor insira o número de colunas e linhas");
		int linhas = keyboard.nextInt();
		int colunas = keyboard.nextInt();
		while(linhas < 4 || colunas < 4) {
			System.out.println("Erro!! O numero de linhas e colunas tem de ser maior que 4, por favor tente outra vez!");
		}
		
		Random random = new Random();
		
		int yT = random.nextInt(colunas-1);
		int xT = random.nextInt(linhas-1);
		
		int yB = random.nextInt(colunas-1);
		int xB = random.nextInt(linhas-1);
		
		int yC = random.nextInt(colunas-1);
		int xC = random.nextInt(linhas-1);
		
		while(yT == yB && xT == xB) {
			yB = random.nextInt(colunas-1);
			xB = random.nextInt(linhas-1);
			while((yT==yC && xT==xC)||(yB==yC && xB==xC)) {
				yC = random.nextInt(colunas-1);
				xC = random.nextInt(linhas-1);
			}
		}
		
		char[][]board = new char[linhas][colunas];
		for(int i = 0; i < linhas; i++) {
			for(int j = 0; j < colunas; j++) {
				if(existeAtaqueEntreTorreEBispo(xT,yT,xB,yB,i,j) || existeAtaqueEntreCavaloETorre(xC,yC,xT,yT,i,j) || existeAtaqueEntreCavaloEBispo(xC,yC,xB,yB,i,j)) {
					board[i][j] = '+';
				}
				 else if(torreAtacaPeca(xT, yT, i, j) || cavaloAtacaPeca(xC,yC,i,j) || bispoAtacaPeca(xB,yB,i,j)) {
					board[i][j] = '-';
				}

				
				else board[i][j] = 'o';
			}
		}
		
		//Invocar funcoes
		tabuleiroAddCavalo(board,(int)xC,(int)yC);
		
		tabuleiroAddBispo(board, (int)xB, (int)yB);
		
		tabuleiroAddTorre(board, (int)xT, (int)yT);
		
		printTabuleiro(board,linhas,colunas);
		
	}

	/**
	 * Verifica se a torre em xt,yt ataca a peça em xp,yp.
	 * 
	 * Neste caso, se xt = xp há um ataque na vertical e se yt = yp há um ataque
	 * na horizontal
	 * 
	 * @return true, se há ataque
	 */
	private static boolean torreAtacaPeca(int xt, int yt, int xp, int yp) {
		
		return (xt == xp || yt == yp)?true:false;

	}

	/**
	 * Verifica se o bispo em x,yt ataca a peça em xp,yp.
	 */
	private static boolean bispoAtacaPeca(int xb, int yb, int xp, int yp) {
		int posicaoX = Math.abs(xb - xp);
		int posicaoY = Math.abs(yb - yp);
		if(posicaoX == posicaoY) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Verifica se o cavalo em x,yt ataca a peça em xp,yp.
	 */
	private static boolean cavaloAtacaPeca(int xc, int yc, int xp, int yp) {
		
		// Up and Down
		int x1 = xc + 2;
		int x2 = xc - 2;
		// corresponding y
		int y1 = yc + 1;
		int y2 = yc - 1;
		// Left and Right
		int y3 = yc + 2;
		int y4 = yc - 2;
		// corresponding x
		int x3 = xc + 1;
		int x4 = xc - 1;
		// bool expressions
		boolean e1 = (xp == x1 && (yp == y1 || yp == y2));
		boolean e2 = (xp == x2 && (yp == y1 || yp == y2));
		boolean e3 = (yp == y3 && (xp == x3 || xp == x4));
		boolean e4 = (yp == y4 && (xp == x3 || xp == x4));
		
		return (e1||e2||e3||e4)? true:false;
		
	}

	/**
	 * Verifica se há ataque entre a torre em xt,yt e o bispo em xb,yb.
	 * 
	 * Utiliza os métodos anteriores.
	 */
	private static boolean existeAtaqueEntreTorreEBispo(int xt, int yt, int xb, int yb, int i, int j) {
		
		boolean tt = torreAtacaPeca(xt, yt,i,j);
		boolean tb = bispoAtacaPeca(xb,yb,i,j);

		return tb&&tt;
	}

	/**
	 * Verifica se há ataque entre o cavalo em xc,yc e o bispo em xb,yb.
	 * 
	 * Utiliza os métodos anteriores.
	 */
	private static boolean existeAtaqueEntreCavaloEBispo(int xc, int yc, int xb, int yb, int i, int j) {

		boolean tc = cavaloAtacaPeca(xc, yc,i,j);
		boolean tb = bispoAtacaPeca(xb,yb,i,j);

		return tc&&tb;
	}

	/**
	 * Verifica se há ataque entre o cavalo em xc,yc e a torre em xt,yt.
	 * 
	 * Utiliza os métodos anteriores.
	 */
	private static boolean existeAtaqueEntreCavaloETorre(int xc, int yc, int xt, int yt, int i, int j) {

		boolean tt = torreAtacaPeca(xt, yt,i,j);
		boolean tc = cavaloAtacaPeca(xc, yc,i,j);

		return tc&&tt;
	}

	/**
	 * Adiciona a torre ao tabuleiro. Ajusta os ataques às quadrículas
	 */
	private static void tabuleiroAddTorre(char[][] tabuleiro, int xt, int yt) {

		char torreLetra = 'T';
		tabuleiro[xt][yt] = torreLetra;
	}

	/**
	 * Adiciona o bispo ao tabuleiro. Ajusta os ataques às quadrículas
	 */
	private static void tabuleiroAddBispo(char[][] tabuleiro, int xb, int yb) {

		char bispoLetra = 'B';
		tabuleiro[xb][yb] = bispoLetra;
	}

	/**
	 * Adiciona o cavalo ao tabuleiro. Ajusta os ataques às quadrículas
	 */
	private static void tabuleiroAddCavalo(char[][] tabuleiro, int xc, int yc) {
		
		char cavaloLetra = 'C';
		tabuleiro[xc][yc] = cavaloLetra;
		
			
		
	}

	/**
	 * Imprime o tabuleiros
	 */
	private static void printTabuleiro(char[][] tabuleiro, int nRow, int nCol) {
		System.out.println("-------------------------");
		for (int row = 0; row < nRow; row++){
			System.out.println("");

			for (int column = 0; column < nCol; column++){
				System.out.print("| ");
				System.out.print(tabuleiro[row][column]);

			}
			System.out.print("|");
		}
		System.out.println("");
		System.out.println("-------------------------");

	}
}
