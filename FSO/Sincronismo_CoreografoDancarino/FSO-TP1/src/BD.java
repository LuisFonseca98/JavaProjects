import java.util.ArrayList;

public class BD {
    String consola;
    String nomeRobot;
    int raio, angulo, distancia;
    boolean onOff, debug;
    private RobotLegoEV3 robot;
    private ArrayList<String> consolaAnterior;

    public BD() {
        consola = "";
        nomeRobot = "Gervasio";
        raio = 0;
        angulo = 0;
        distancia = 0;
        onOff = false;
        debug = true;
        robot = new RobotLegoEV3();

        consolaAnterior = new ArrayList<>();
    }

    public RobotLegoEV3 getRobot() {
        return robot;
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

    public void resetValores() {
        consola = "";
        nomeRobot = "";
        raio = 0;
        angulo = 0;
        distancia = 0;
    }

}
