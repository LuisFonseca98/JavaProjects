
import java.io.IOException;
import java.util.ArrayList;

public class Coreografo extends Thread {
    private GuiCoreografo gui;
    private CanalComunicacao cc;
    private int numOrdensEnviadas;
    private ArrayList<MyMensagem> ultimasOrdensEnviadas;
    private int numOrdensAEnviar;
    private int estado;

    public static final int COREO_SLEEP = 0, COREO_ESPERA = 1, COREO_GERAR_ENVIAR = 2, COREO_TERMINAR = 3,
            COREO_SLEEP500 = 4, COREO_PARAR = 5, COREO_DESLIGADO = 6;

    public Coreografo(CanalComunicacao cc) {
        try {
            estado = COREO_DESLIGADO;

            this.cc = cc;

            numOrdensEnviadas = 0;

            ultimasOrdensEnviadas = new ArrayList<MyMensagem>();

            gui = new GuiCoreografo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve no canal de comunicacao uma mensagem
     *
     * @param msg a ser escrita
     * @return true se a mensagem tiver sido escrita
     */
    private boolean enviaOrdem(MyMensagem msg) {
        try {
            System.out.println(currentThread().getName() + " Ordem enviada - " + numOrdensEnviadas);
            numOrdensEnviadas++;
            // Envia a mensagem para o buffer

            cc.write(msg);

            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gera 1 comando, atualiza as ultimas ordens enviadas e incrementa o numero de
     * ordens enviadas.
     *
     * @return
     */
    private boolean gerarComando() {
        MyMensagem m = new MyMensagem();

        m.setOrdem(m.gerarOrdem());
        m.setNumero(numOrdensEnviadas);

        if (!enviaOrdem(m)) {
            return false;
        }
        // Shifta o arraylist para a esquerda
        if (ultimasOrdensEnviadas.size() >= 10) {
            ultimasOrdensEnviadas.remove(0);
        }

        ultimasOrdensEnviadas.add(m);
        gui.setUltimosComandos(getUltimasOrdensEnviadas());
        return true;
    }

    /**
     * Envia um parar a false. <br>
     * Util para ser utilizado quando nao forem enviadas mais mensagens
     *
     * @return true, se o parar foi enviado
     */
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

    /**
     * Ativa o Coreografo, permitindo o envio de ordens
     *
     * @return true, se o coreografo ficou ativo
     */
    public boolean toggleAtivar() {
        if (gui.isCoreoAtivo()) {
            desativar();
        } else {
            ativar();
        }
        return gui.isCoreoAtivo();
    }

    public boolean ativar() {
        gui.Ativar();
        estado = COREO_ESPERA;
        return true;
    }

    public boolean desativar() {
        gui.Desativar();
        estado = COREO_DESLIGADO;
        return true;
    }

    private void setNumOrdensAEnviar(int i) {
        numOrdensAEnviar = i;
    }

    public void adicionaOrdensAEnviar(int n) {
        setNumOrdensAEnviar(numOrdensAEnviar + n);
    }

    public ArrayList<MyMensagem> getUltimasOrdensEnviadas() {
        return this.ultimasOrdensEnviadas;
    }

    public String toString() {
        return "Coreografo " + currentThread().getName();
    }

    /**
     * TERMINA O COREOGRAFO, MATANDO A THREAD.<br>
     * NAO É POSSIVEL RECUPERAR O COREOGRAFO DEPOIS DESTE METODO SER CHAMADO
     */
    public void Terminar() {
        estado = COREO_TERMINAR;
    }

    /**
     * Inicia um coreografo, ativando na gui e mostrando a gui. É possivel iniciar o
     * coreografo, mesmo sem chamar este metodo, clicando no butao da GUI
     */
    public void iniciar() {
        gui.setVisible(true);
        start();
    }

    /**
     * Metodo run. Corre enquanto nao for chamado o metodo terminar do coreografo ou
     * o estado alterado para COREO_TERMINAR
     * <p>
     * Existe comutacao de estados para o COREO_SLEEP para permitir comutacao de
     * THREADS
     */

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

                            gerarComando();

                            numOrdensAEnviar--;

                            estado = COREO_SLEEP500;
                        }

                        break;

                    case COREO_SLEEP500:

                        Thread.sleep(500);

                        gui.setUltimosComandos(getUltimasOrdensEnviadas());

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

    /**
     * Altera o estado do coreografo
     *
     * @param estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Indica se a gui está visivel
     *
     * @return
     */
    public boolean isVisible() {
        return gui.isVisible();
    }

    /**
     * Faz display da gui
     *
     * @param b boolean, true para mostrar, false para esconder
     */
    public void setVisible(boolean b) {
        gui.setVisible(b);
    }

    /**
     * Fecha a GUI, e elimina a mesma
     */
    public void closeGUI() {
        gui.setVisible(false);
        gui.dispose();
    }

}
