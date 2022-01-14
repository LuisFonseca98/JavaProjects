import java.util.Random;
import java.util.concurrent.Semaphore;

public class Circulo extends DesenharCirculo{

	private int raio,angulo;
	public Semaphore semCirculo;
	public Random r;
	private ClienteDoRobo cli;
	private App app;

	protected Circulo(ClienteDoRobo cli, App app) {
		raio = 0;
		angulo = 360;
		semCirculo = new Semaphore(0);
		r = new Random();
		this.cli = cli;
		this.app = app;
	}
	
	public int randRaio() {
		int random;
		
		random = r.nextInt(30-5)+5;
		
		return random;
	}
	
	public void setRaio(int x) {
		raio = x;
	}
	
	public int getRaio() {
		int dist;
		
		dist = raio;
		
		return dist;
	}
	
	
	public void run() {
		while(true) {
			try {
				semCirculo.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			desenharFormaCirculo();
		}
	}
	
	public void avancarCirculo() {
		app.circulo.semCirculo.release();
	}
	
	public int gerarRandomCirculo() {
		return randRaio();
	}

	@Override
	public void desenharFormaCirculo() {
		
		try {
			app.semSinc.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		if(app.anguloText.getText().equals("")) {
			angulo = 360;
		}else {
			angulo = Integer.parseInt(app.anguloText.getText());
		}
		
		
		cli.CurvarEsq(raio, angulo);
		cli.Parar(false, (long) ((1000.0*(float)raio)/22.0));
		
		try {
			Thread.sleep((long) ((1000.0*(float)raio)/22.0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		app.semSinc.release();
		
	}  
}  

