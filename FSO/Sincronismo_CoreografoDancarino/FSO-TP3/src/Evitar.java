import java.util.concurrent.Semaphore;

public class Evitar extends Thread {

    public BD bd;
    private int estado;
    private Dancarino d;
    private Semaphore smEvitar;
    private int resultado;

    public Evitar(Dancarino d, Semaphore s) {
        this.d = d;
        this.bd = d.getBD();
        this.smEvitar = s;
        resultado = 0;
        estado = 0;

    }

    public void run() {
        while (true) {
            switch (estado) {
                case 0:
                    if (d.getOnGUI() && d.getAtivo()) {
                        estado = 2;
                    } else
                        estado = 1;
                    break;
                case 1:

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    estado = 0;

                    break;

                case 2:

                    resultado = bd.getRobot().SensorToque(RobotLegoEV3.S_1);

                    if (resultado == 1) {
                        d.Evitar();
                        estado = 0;
                        resultado = 0;
                    }

                    smEvitar.release();

                    estado = 3;

                case 3:
                    try {
                        Thread.sleep(200);
                        smEvitar.acquire();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    estado = 2;
                    break;
            }
        }
    }
}
