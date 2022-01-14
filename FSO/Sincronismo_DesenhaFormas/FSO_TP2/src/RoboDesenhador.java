
import java.util.concurrent.Semaphore;


public class RoboDesenhador implements IComandos {
	
	public RobotLegoEV3 robot;
	private RoboDesenhadorGUI desenhaGUI;
	public int comportamento, id, raioOuDistancia, angulo;
	public long timeSleep;
	public GravarFormasGUI gravarGUI;
	public GravarFormas gravar;
	public Semaphore semGravar;
	public String nomeRobot;
	public boolean GUIopen;
	public static final int IDLE = 0,
			RETA = 1,
			CURVAR = 2,
			PARAR = 3,
			INICIAR_GRAVAR = 4,
			OPEN_ROBOT = 5;
					
	
	public RoboDesenhador() {
		comportamento = 0;
		GUIopen = false;
		desenhaGUI = new RoboDesenhadorGUI();
		desenhaGUI.setVisible(true);
		robot = new RobotLegoEV3();
		nomeRobot = "";
	}
	
	public void Desenhador() {
		switch(comportamento) {
			case RETA:
				Reta(raioOuDistancia);
				robot.Reta(raioOuDistancia);
				desenhaGUI.textArea.append("Reta > Distancia: "+raioOuDistancia+"\n");
				
				if(GUIopen) {
					if(gravarGUI.gravarBool) {
						gravar.id = id;
						gravar.tipo = 1;
						gravar.raioOudistancia = raioOuDistancia;
						gravar.angulo = 0;
						gravar.estado = gravar.GRAVAR;
					}
				}
				break;
			case CURVAR:
				CurvarEsq(raioOuDistancia, angulo);
				robot.CurvarEsquerda(raioOuDistancia, angulo);
				desenhaGUI.textArea.append("Curva > Raio: "+raioOuDistancia+" Angulo: "+angulo+"\n");
				if(GUIopen) {
					if(gravarGUI.gravarBool) {
						gravar.id = id;
						gravar.tipo = 2;
						gravar.raioOudistancia = raioOuDistancia;
						gravar.angulo = angulo;
						gravar.estado = gravar.GRAVAR;
					}
				}
				break;
			case PARAR:
				Parar(true);
				boolean parar;
				if(raioOuDistancia == 1) {
					parar = true;
					robot.Parar(parar);
				}else {
					parar = false;
					robot.Parar(parar);
				}
				desenhaGUI.textArea.append("Parar > Raio: "+parar+"\n");
				if(GUIopen) {
					if(gravarGUI.gravarBool) {
						gravar.id = id;
						gravar.tipo = 3;
						gravar.raioOudistancia = raioOuDistancia;
						gravar.angulo = angulo;
						gravar.timeSleep = timeSleep;
						gravar.estado = gravar.GRAVAR;
					}
				}
				break;
			case INICIAR_GRAVAR:
				gravar = new GravarFormas(robot);
				gravarGUI = new GravarFormasGUI(gravar);
				gravarGUI.setVisible(true);
				gravarGUI.playpauseBtn.setEnabled(false);
				gravarGUI.reproduceBtn.setEnabled(false);
				gravarGUI.gravar.start();
				
				GUIopen = true;
				break;
			case OPEN_ROBOT:
				System.out.println(robot.OpenEV3(nomeRobot));
				robot.OpenEV3(nomeRobot);
				break;
			
		}
	
	}

	@Override
	public void CurvarEsq(int raio, int angulo) {
		System.out.println("DESENHA CURVA RAIO = "+raio);
	}

	@Override
	public void Reta(int distancia) {
		System.out.println("DESENHA RETA DISTANCIA = "+distancia);
	}
	
	public void Parar(boolean parar) {
		System.out.println("DESENHA PARAR = "+parar);
	}
}
