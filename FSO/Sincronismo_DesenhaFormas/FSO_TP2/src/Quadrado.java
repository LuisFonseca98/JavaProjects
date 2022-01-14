import java.util.Random;
import java.util.concurrent.Semaphore;

public class Quadrado extends DesenharQuadrado{

	private int distancia,angulo;
	private boolean pararComportamento;
	public Semaphore semQuadrado;
	private Random r;
	private ClienteDoRobo cli;
	private App app;
	
	public Quadrado(ClienteDoRobo cli, App app){
		distancia = 0;
		angulo = 90;
		pararComportamento = false;
		semQuadrado = new Semaphore(0);
		r = new Random();
		this.cli = cli;
		this.app = app;
	} 
	
	public int randDist() {
		int random;
		
		random = r.nextInt(30-15)+15;
		
		return random;
	}
	
	public void setDist(int x) {
		distancia = x;
	}
	
	public int getDist() {
		int dist;
		
		dist = distancia;
		
		return dist;
	}
	
	public void run() {
		while(true) {
			try {
				semQuadrado.acquire();
				desenhaFormaQuadrado();
			} catch (InterruptedException e) {e.printStackTrace();}
			
		}
	}
	
	public void avancarQuadrado() {
		app.quadrado.semQuadrado.release();
	}
	
	public int gerarRandomQuadrado() {
		return randDist();
	}

	@Override
	public void desenhaFormaQuadrado() throws InterruptedException{
		
		app.semSinc.acquire();
		
		cli.Reta(distancia);
		
		cli.CurvarEsq(10, angulo);
		cli.Parar(pararComportamento, (long) ((1000.0*(float)distancia)/22.0));
		Thread.sleep((long) ((1000.0*(float)distancia)/26.0));		
		
		cli.Reta(distancia);
		cli.CurvarEsq(10, angulo);
		cli.Parar(pararComportamento, (long) ((1000.0*(float)distancia)/22.0));
		Thread.sleep((long) ((1000.0*(float)distancia)/26.0));
		
		
		cli.Reta(distancia);
		cli.CurvarEsq(10, angulo);
		cli.Parar(pararComportamento, (long) ((1000.0*(float)distancia)/22.0));
		Thread.sleep((long) ((1000.0*(float)distancia)/26.0));
		
		
		cli.Reta(distancia);
		cli.CurvarEsq(10, angulo);
		cli.Parar(pararComportamento, (long) ((1000.0*(float)distancia)/22.0));
		Thread.sleep((long) ((1000.0*(float)distancia)/26.0));

		app.semSinc.release();
		
		
	}  

}  
