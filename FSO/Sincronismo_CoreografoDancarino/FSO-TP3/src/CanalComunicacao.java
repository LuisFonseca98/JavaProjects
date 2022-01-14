import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class CanalComunicacao extends Thread {

    private final int MAXMENSAGENS = 32;
    private MyMensagem[] buffer;
    private int pointerWrite;
    private Semaphore canWriteSemaforo;
    private Semaphore[] canDeleteSemaforo;
    private int estado;
    private int deleteNumber;
    private ArrayList<Dancarino> dancarinosLigados;
    private ArrayList<Coreografo> coreografosLigados;
    private ArrayList<Semaphore[]> semaforos;
    private ArrayList<Integer> pointerReads;
    private int nextCoreoID;
    private int nextDancID;

    public CanalComunicacao() throws IOException {

        buffer = new MyMensagem[MAXMENSAGENS];

        // Todos tem de ler pelo menos uma mensagem
        canDeleteSemaforo = semaforosDelete();

        // Uma escrita de cada vez
        canWriteSemaforo = new Semaphore(1);

        dancarinosLigados = new ArrayList<Dancarino>();
        coreografosLigados = new ArrayList<Coreografo>();

        // Lista de Arrays de Semaforos dos dancarinos, 32 para cada danc
        semaforos = new ArrayList<Semaphore[]>();
        pointerReads = new ArrayList<Integer>();

        pointerWrite = 0;
        estado = 0;
        deleteNumber = 0;
        nextCoreoID = 0;
        nextDancID = 0;
    }

    /**
     * Escreve uma mensagem no canal de comunicacao para a primeira posicao
     * disponivel
     *
     * @param m, mensagem a ser escrita
     * @throws IOException
     * @throws InterruptedException
     */
    public void write(MyMensagem m) throws IOException, InterruptedException {

        canWriteSemaforo.acquire();

        canDeleteSemaforo[pointerWrite].acquire();

        buffer[pointerWrite] = m;

        // Release aos semaforos na posicao pointerwrite de cada um dos dancarinos
        releaseSemaforos(pointerWrite);

        System.out.println("PointerW - " + pointerWrite);

        pointerWrite = (++pointerWrite) % (MAXMENSAGENS);
        canWriteSemaforo.release();

    }

    /**
     * Le uma posicao do canal de comunicao e retorna a mensagem
     *
     * @param numeroPointerRead POSICAO A SER LIDA
     * @return MyMensagem m, que estava guardada na posicao
     * @throws IOException
     * @throws InterruptedException
     */
    public MyMensagem readMsg(int numeroPointerRead) throws IOException, InterruptedException {

        // Pointer = lista pointers indice pos
        int valorPointer = pointerReads.get(numeroPointerRead);

        semaforos.get(numeroPointerRead)[valorPointer].acquire();

        System.out.println("PointerR " + numeroPointerRead + " - " + valorPointer);

        // Entra no array de pointers usa o parametro para procurar o que quer e
        // incrementa
        pointerReads.set(numeroPointerRead, (valorPointer + 1) % MAXMENSAGENS);

        // cria uma nova mensagem com esse numero e ordem e retorna a mensagem
        MyMensagem m = new MyMensagem();
        m.setNumero(buffer[valorPointer].getNumero());
        m.setOrdem(buffer[valorPointer].getOrdem());

        return m;

    }

    /**
     * Converte um valor inteiro correspondente á posicao no buffer circular, para
     * uma posicao valida para o mappedbytebuffer
     *
     * @param i posicao onde iremos colocar o pointer do buffer
     */

    public int getPointerWrite() {
        return pointerWrite;
    }

    /**
     * Adiciona um coreografo ao canal de comunicacao
     *
     * @param c Coreografo a adicionar ao canal de comunicacao
     * @return id do Coreografo no canal de comunicacao
     */
    public int open(Coreografo c) {
        coreografosLigados.add(c);
        return nextCoreoID++;
    }

    /**
     * Remove um coreografo do canal de comunicacao, Fecha GUI's e termina o estado
     * do coreografo
     *
     * @param c Coreografo a ser removido do cc.
     */
    public void close(Coreografo c) {
        c.closeGUI();
        c.Terminar();
        semaforos.remove(coreografosLigados.indexOf(c));
        coreografosLigados.remove(c);
    }

    /**
     * Abre a ligacao com um dancarino
     *
     * @param d
     * @return
     */
    public int open(Dancarino d) {
        dancarinosLigados.add(d);
        Semaphore[] a = addSemaphore();
        semaforos.add(a);
        nextDancID++;

        // Adiciona um pointerRead a lista que e inicializado a 0 e retorna o indice na
        // lista
        return addPuts();
    }

    public int getNextDancID() {
        return nextDancID;
    }

    /**
     * Este metodo auxilia a sincronizacao dos dancarinos, bloqueando todos os
     * processos, caso uma mensagem nao tenha sido lida por todos os dancarinos, é
     * usado para instanciar um dancarino no canal de comunicacao
     * <p>
     * Caso um dancarino seja instanciado depois de outros dancarinos estarem em
     * execucao, esse dancarino nao conta as mensagens que nao ira receber,
     * iniciando semaforos com 0 permits
     *
     * @return Semaphore [] array de semaforos, que irá controlar um dancarino
     */
    private Semaphore[] addSemaphore() {

        int p = getPointerWrite();

        Semaphore[] s = new Semaphore[MAXMENSAGENS];

        for (int i = 0; i < MAXMENSAGENS; i++) {

            // Caso ja tenham sido enviado mensagens ele vai dar release a esses semaforos
            if (i < p && p != 0)
                s[i] = new Semaphore(1);
            else
                s[i] = new Semaphore(0);
        }

        return s;
    }

    /**
     * Este metodo garante que apenas uma mensagem que tenha sido lida por todos os
     * dancarinos pode ser apagada, garantido o bloqueio de todos os coreografos e
     * dancarinos que estejam mais avancados, criando arrays com 1 dimensao onde
     * sera feito um acquire ao escrever, e um release quando toda a gente ler essa
     * posicao
     *
     * @return Semaphore []
     */
    public Semaphore[] semaforosDelete() {

        Semaphore[] s = new Semaphore[MAXMENSAGENS];

        for (int i = 0; i < MAXMENSAGENS; i++) {
            s[i] = new Semaphore(1);
        }

        return s;
    }

    public void releaseSemaforos(int p) {
        for (int i = 0; i < semaforos.size(); i++) {
            semaforos.get(i)[p].release();
        }

    }

    public int addPuts() {

        pointerReads.add(0);

        return pointerReads.size() - 1;
    }

    /**
     * Metodo auxiliar ao run, que garante permite fazer release ao semaforo
     * canDelete na posicao numMensagem
     *
     * @param numMensagem posicao do array a ser verificada
     * @return true se ja todos os dancarinos tiverem lido a mensagem numa posicao
     */
    public boolean todosJaLeramMensagem(int numMensagem) {

        // Paneleirice minha, acho que fica mais legivel assim.
        // Ambas maneiras funcionam depois escolham a que gostam mais
        for (Semaphore[] semaforo : semaforos) {
            if (semaforo[numMensagem].availablePermits() != 0)
                return false;
        }
        return true;
    }

    /**
     * Metodo RUN: PONTO DE ENTRADA DO PROGRAMA..................................
     * Tem 2 estados, estado 1, repouso, estado 0, ativo. No estado 0, faz-se a
     * verificacao se é possivel apagar uma mensagem ou nao. Existe comutacao de
     * estados para permitir a outras threads, aceder ao CPU
     */

    public void run() {
        while (true) {
            switch (estado) {
                case 0:

                    // todos ja leram, ja foi escrita, a mensagem nao e nula, e existem dancarinos
                    // ligados
                    if (todosJaLeramMensagem(deleteNumber) && canDeleteSemaforo[deleteNumber].availablePermits() == 0
                            && buffer[deleteNumber] != null && dancarinosLigados.size() > 0) {
                        canDeleteSemaforo[deleteNumber].release();
                        deleteNumber = ++deleteNumber % MAXMENSAGENS;
                    }
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
            }

        }
    }
}