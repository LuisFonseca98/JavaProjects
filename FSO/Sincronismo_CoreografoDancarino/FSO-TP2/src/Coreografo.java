import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Coreografo extends Thread {
    private GuiCoreografo gui;
    private CanalComunicacao cc;
    private int numOrdensEnviadas;
    private ArrayList<MyMensagem> ultimasOrdensEnviadas;
    private int numOrdensAEnviar;
    private int idCoreo;
    private Semaphore s;
    private int estado;

    public static final int COREO_SLEEP = 0, COREO_ESPERA = 1, COREO_GERAR_ENVIAR = 2, COREO_TERMINAR = 3,
            COREO_SLEEP500 = 4, COREO_PARAR = 5, COREO_DESLIGADO = 6;

    public Coreografo(CanalComunicacao cc, Semaphore semaforo) {
        try {
            estado = COREO_DESLIGADO;
            // Cria inst�ncias de canal comunica��o e gui
            this.cc = cc;
            // Mete o n�mero de ordens enviadas a 0
            numOrdensEnviadas = 0;
            // Arraylist auxiliar para armazenar as mensagens enviadas e exp�-las na GUI
            ultimasOrdensEnviadas = new ArrayList<MyMensagem>();
            gui = new GuiCoreografo(this);
            s = semaforo;
            // Mete o n�mero de ordens a enviar a 1 porque vamos enviar a ordem 0,0 logo a
            // seguir

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean enviaOrdem(MyMensagem msg) {
        try {
            System.out.println(currentThread().getName() + " Ordem enviada - " + numOrdensEnviadas);
            numOrdensEnviadas++;
            // Envia a mensagem para o buffer

            cc.write(msg);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean gerarComando() {
        // Cria uma mensagem e d� set no n�mero e na ordem
        try {
            s.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MyMensagem m = new MyMensagem();
        // Gera uma ordem aleat�ria
        m.setOrdem(m.gerarOrdem());
        m.setNumero(numOrdensEnviadas);
        // Se n�o conseguir enviar a ordem retorna falso
        if (!enviaOrdem(m)) {
            return false;
        }
        // Shifta o arraylist para a esquerda
        if (ultimasOrdensEnviadas.size() >= 10) {
            ultimasOrdensEnviadas.remove(0);
        }
        // Adiciona � mem�ria e exp�e
        ultimasOrdensEnviadas.add(m);
        gui.setUltimosComandos(getUltimasOrdensEnviadas());
        return true;
    }

    public boolean enviarParar() {
        MyMensagem m = new MyMensagem();
        m.setNumero(numOrdensEnviadas);
        m.setOrdem(0);

        if (!enviaOrdem(m)) {
            return false;
        }

        if (ultimasOrdensEnviadas.size() >= 10) {
            ultimasOrdensEnviadas.remove(0);
        }
        ultimasOrdensEnviadas.add(m);
        return true;
    }

    public boolean Ativar() {

        gui.setAtivar();

        return gui.isCoreoAtivo();
    }

    private void setNumOrdensAEnviar(int i) {
        numOrdensAEnviar = i;
    }

    public void adicionaOrdensAEnviar(int n) {
        setNumOrdensAEnviar(numOrdensAEnviar + n);
    }

    // Gera NComandos e depois envia um parar(false)
    public boolean gerarNComandos(int n) {
        if (n != 0) {
            for (int i = 0; i < n - 1; i++) {
                if (!gerarComando()) {
                    return false;
                }
            }
            enviarParar();
            return true;
        }
        return false;
    }

    public ArrayList<MyMensagem> getUltimasOrdensEnviadas() {
        return this.ultimasOrdensEnviadas;
    }

    public String toString() {
        return currentThread().getName();
    }

    public void Terminar() {

        estado = COREO_TERMINAR;
    }

    public JPanel toDisplay() {
        JPanel panel = new JPanel();
        panel.setBounds(114, 47, 157, 47);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JCheckBox chckbxOnoff = new JCheckBox("ON/OFF");
        panel.add(chckbxOnoff);

        JLabel lblNome = new JLabel(this.toString());
        panel.add(lblNome);
        return panel;
    }

    public void iniciar() {
        gui.setCoreoAtivo(true);
        gui.setVisible(true);
    }

    public void run() {

        while (estado != COREO_TERMINAR) {
            try {

                switch (estado) {

                    case COREO_DESLIGADO:
                        Thread.sleep(100);

                        if (gui.isCoreoAtivo() && estado == COREO_DESLIGADO)
                            estado = COREO_ESPERA;

                        break;

                    case COREO_SLEEP:

                        Thread.sleep(20);

                        estado = COREO_ESPERA;
                        break;
                    case COREO_ESPERA:

                        if (!gui.isCoreoAtivo())
                            estado = COREO_DESLIGADO;

                        if (gui.isInfinito())
                            numOrdensAEnviar = 1;

                        if (estado == COREO_ESPERA && numOrdensAEnviar > 0)
                            estado = COREO_GERAR_ENVIAR;

                        else if (estado == COREO_ESPERA)
                            estado = COREO_SLEEP;

                        break;

                    case COREO_GERAR_ENVIAR:

                        if (!gui.isCoreoAtivo())
                            estado = COREO_DESLIGADO;

                        else {
                            System.out.println(currentThread().getName() + " Waiting for permits");

                            // System.out.println(currentThread().getName() + " Got the permits");
                            gerarComando();

                            numOrdensAEnviar--;

                            estado = COREO_SLEEP500;
                        }

                        break;

                    case COREO_SLEEP500:

                        Thread.sleep(500);
                        estado = COREO_GERAR_ENVIAR;
                        gui.setUltimosComandos(getUltimasOrdensEnviadas());
                        s.release();

                        if (numOrdensAEnviar > 0 || gui.isInfinito())
                            estado = COREO_ESPERA;
                        else
                            estado = COREO_PARAR;
                        break;

                    case COREO_PARAR:

                        enviarParar();
                        gui.setUltimosComandos(getUltimasOrdensEnviadas());
                        estado = COREO_ESPERA;
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isVisible() {
        return gui.isVisible();
    }

    public void setVisible(boolean b) {
        gui.setVisible(b);
    }

    public void closeGUI() {
        gui.setVisible(false);
        gui.dispose();
    }

}
