import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BD {
    String consola;
    String nomeRobot;
    int raio, angulo, distancia;
    boolean onOff, debug;
    private SpyRobot spyRobot;
    private ArrayList<String> consolaAnterior;
    private Semaphore semaforoExclusaoMutua;

    public BD(Semaphore s) {
        semaforoExclusaoMutua = s;
        consola = "";
        nomeRobot = "EV";
        raio = 0;
        angulo = 0;
        distancia = 0;
        onOff = false;
        debug = true;
        spyRobot = new SpyRobot(this);
        consolaAnterior = new ArrayList<>();
    }

    public RobotLegoEV3 getRobot() {
        return spyRobot.getRobot();
    }

    public void CloseEV3() {
        try {
            semaforoExclusaoMutua.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        spyRobot.CloseEV3();
        semaforoExclusaoMutua.release();
    }

    public boolean OpenEV3(String nome) {
        try {
            semaforoExclusaoMutua.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean a = spyRobot.OpenEV3(nome);
        semaforoExclusaoMutua.release();
        return a;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        // gravar log anterior
        consolaAnterior.add(this.consola);
        // mudar o valor
        this.consola = consola;
    }

    public String printRegistos() {
        System.out.println(consolaAnterior.toString());
        return consolaAnterior.toString();
    }

    public String getNomeRobot() {
        return nomeRobot;
    }

    public Semaphore getSem() {
        return semaforoExclusaoMutua;
    }

    public void setNomeRobot(String nomeRobot) {
        this.nomeRobot = nomeRobot;
    }

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public int getAngulo() {
        return angulo;
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void start() {
        spyRobot.iniciar();
    }

    public void frente(int cm) {
        spyRobot.frente(cm);
    }

    public void CurvarDireita(int raio, int angulo) {
        spyRobot.CurvarDireita(raio, angulo);

    }

    public void CurvarEsquerda(int raio, int angulo) {
        spyRobot.CurvarEsquerda(raio, angulo);
    }

    public void retaguarda(int cm) {
        spyRobot.retaguarda(-1 * cm);
    }

    public void parar(boolean imediato) {
        spyRobot.Parar(imediato);
    }

    public void resetValores() {
        consola = "";
        nomeRobot = "";
        raio = 0;
        angulo = 0;
        distancia = 0;
    }

}
