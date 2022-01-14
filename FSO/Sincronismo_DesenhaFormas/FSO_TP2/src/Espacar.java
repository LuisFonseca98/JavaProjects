import java.util.concurrent.Semaphore;

public class Espacar extends EspacarFormasGeometricas{

	public Semaphore semEspacar;
	private ClienteDoRobo cli;
	private App app;
	public int tipo;
	public int raioOuDistancia, raioOuDistanciaAnterior;
	
	protected Espacar(ClienteDoRobo cli, App app) {		
		semEspacar = new Semaphore(0);
		this.app = app;
		this.cli = cli;
		tipo = 0;
		raioOuDistancia = 0;
		raioOuDistanciaAnterior = 0;
	}
	
	public void run() {
		while(true) {
			try {
				semEspacar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(app.tipoMensagem == 1) {
				raioOuDistancia = app.quadrado.gerarRandomQuadrado();
				
			}else if(app.tipoMensagem == 2) {
				raioOuDistancia = app.circulo.gerarRandomCirculo();
			}
			
			espacarComportamentos();
			
			if(app.tipoMensagem == 1) {
				app.quadrado.avancarQuadrado();
			}else if(app.tipoMensagem == 2) {
				app.circulo.avancarCirculo();
			}
			
		}
	}
	
	public void avancarEspacar() {
		app.espacar.semEspacar.release();
	}
	
	@Override
	public void espacarComportamentos() {
	
		try {
			app.semSinc.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		if(app.tipoMensagem == 1) {
			app.quadrado.setDist(raioOuDistancia);
			
		}else if(app.tipoMensagem == 2) {
			app.circulo.setRaio(raioOuDistancia);
		}
		
		cli.Reta(raioOuDistancia+app.raioDistAnterior);
		
		app.raioDistAnterior = raioOuDistancia;
		
		app.semSinc.release();

		
	}
}
