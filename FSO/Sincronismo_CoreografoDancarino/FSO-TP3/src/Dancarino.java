import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Dancarino extends Thread {

    private GuiDancarino gui;
    private CanalComunicacao cc;
    private ArrayList<MyMensagem> listaMensagensEmEspera;
    private int estadoCC;
    private int estadoRobot;
    private MyMensagem msg;
    private volatile int estado;
    private BD bd;
    private Semaphore semaforoExclusaoMutua;
    public static final int CC_ESTADO_VALIDAR = 0, CC_ESTADO_GUARDAR = 1;
    public static final int ROBOT_ESTADO_INTERPRETAR_CUMPRIR = 0, ROBOT_ESTADO_POP = 1;
    public static final int ESTADO_ESPERA = 0, ESTADO_SLEEP = 1, ESTADO_EXECUTAR = 2, ESTADO_EVITAR = 3, ESTADO_TERMINAR = 4;
    private int pointerRead;
    private int idDanc;
    private Evitar evitar;

    public Dancarino(CanalComunicacao cc) {

        estado = ESTADO_ESPERA;
        this.cc = cc;
        semaforoExclusaoMutua = new Semaphore(1);
        bd = new BD(semaforoExclusaoMutua);
        msg = null;
        pointerRead = 0;
        listaMensagensEmEspera = new ArrayList<MyMensagem>();
        gui = new GuiDancarino(this, bd);
        setIdDanc(cc.getNextDancID());
        pointerRead = cc.open(this);

        evitar = new Evitar(this, semaforoExclusaoMutua);
        evitar.start();
    }

    // Recebe a mensagem
    public MyMensagem receberMensagem() {
        try {

            System.out.println("MENSAGEM RECEBIDA");
            MyMensagem instrucao = cc.readMsg(pointerRead);

            return instrucao;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void guardarMensagem(MyMensagem m) {
        System.out.println("MENSAGEM GUARDADA");
        listaMensagensEmEspera.add(m);
    }

    public void Evitar() {
        bd.parar(true);
        gui.desativar();

        estado = ESTADO_EVITAR;
    }

    public BD getBD() {
        return this.bd;
    }

    public String toString() {
        return "Dancarino " + currentThread().getName();
    }

    public boolean calcHasNext() {
        // Verifica se h� mensagens em espera
        return listaMensagensEmEspera.size() > 0;
    }

    public void iniciar() {
        try {
            start();
        } catch (IllegalThreadStateException e) {
            System.err.println(this.getName() + " já foi iniciado");
        }
        spyStart();
        gui.setVisible(true);
    }

    public boolean isVisible() {
        return gui.isVisible();
    }

    public void setVisible(boolean b) {
        gui.setVisible(b);
    }

    public int getIdDanc() {
        return idDanc;
    }

    public void setIdDanc(int idDanc) {
        this.idDanc = idDanc;
    }

    public boolean toggleAtivar() {

        gui.toggleAtivar();
        return gui.isAtivar();
    }

    public boolean getOnGUI() {
        return gui.isOnOff();
    }

    public boolean getAtivo() {
        return gui.isAtivar();
    }

    public void spyStart() {
        bd.start();
    }

    public void closeGUI() {
        gui.setVisible(false);
        gui.dispose();
    }

    public void closeEV3() {
        bd.CloseEV3();
    }

    public int CumprirOrdem() {

        System.out.println("MENSAGEM CUMPRIDA");
        MyMensagem m = listaMensagensEmEspera.get(0);

        int tExec = 0;
        switch (m.getOrdem()) {
            case 0:
                bd.parar(false);
                gui.myPrint(m.getNumero() + "- Parar(false)");
                tExec = 100;
                break;
            case 1:
                bd.frente(10);
                gui.myPrint(m.getNumero() + "- Frente(10)");
                tExec = 400;
                break;
            case 2:
                bd.CurvarDireita(0, 45);
                gui.myPrint(m.getNumero() + "- Curva Direita(45)");
                tExec = 200;
                break;
            case 3:
                bd.CurvarEsquerda(0, 45);
                gui.myPrint(m.getNumero() + "- Curva Esquerda(45)");
                tExec = 200;
                break;
            case 4:
                bd.retaguarda(-10);
                gui.myPrint(m.getNumero() + "- Tras(10)");
                tExec = 400;
                break;
            default: // Tambem é o case 5
                bd.parar(true);
                gui.myPrint(m.getNumero() + "- Parar(true)");
                tExec = 100;
                break;
        }
        return tExec;
    }

    public void run() {
        while (estado != ESTADO_TERMINAR) {

            switch (estado) {

                case ESTADO_ESPERA:

                    if ((gui.isAtivar() && gui.isOnOff()) && estado == ESTADO_ESPERA) {

                        estado = ESTADO_EXECUTAR;

                    } else if (estado == ESTADO_ESPERA)
                        estado = ESTADO_SLEEP;
                    break;

                case ESTADO_SLEEP:
                    try {
                        Thread.sleep(10);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (estado == ESTADO_SLEEP)
                        estado = ESTADO_ESPERA;

                    break;

                case ESTADO_EXECUTAR:

                    automatoCC();

                    automatoRobot();

                    if (!gui.isAtivar() && !calcHasNext() && estado == ESTADO_EXECUTAR) {

                        bd.parar(true);
                        estado = ESTADO_ESPERA;
                    }

                    break;

                case ESTADO_EVITAR:

                    estado = ESTADO_ESPERA;
                    break;
            }
        }
    }

    private void automatoCC() {

        switch (estadoCC) {

            case CC_ESTADO_VALIDAR:

                msg = receberMensagem();
                System.out.println(msg);

                if (msg != null) {
                    estadoCC = CC_ESTADO_GUARDAR;
                }
                break;
            case CC_ESTADO_GUARDAR:

                guardarMensagem(msg);
                msg = null;
                estadoCC = CC_ESTADO_VALIDAR;
        }

    }

    private void automatoRobot() {
        switch (estadoRobot) {
            case ROBOT_ESTADO_INTERPRETAR_CUMPRIR:

                int TempoEspera;

                if (calcHasNext()) {
                    try {
                        semaforoExclusaoMutua.acquire();
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    TempoEspera = CumprirOrdem();
                    semaforoExclusaoMutua.release();
                    try {
                        System.out.println(TempoEspera);
                        Thread.sleep(TempoEspera);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    estadoRobot = ROBOT_ESTADO_POP;
                }
                break;
            case ROBOT_ESTADO_POP:

                // Remove a mensagem executada
                listaMensagensEmEspera.remove(0);
                estadoRobot = ROBOT_ESTADO_INTERPRETAR_CUMPRIR;
                break;
        }
    }
}
