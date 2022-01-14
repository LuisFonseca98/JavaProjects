import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
        canDeleteSemaforo = addDelSemaphore();
        canWriteSemaforo = new Semaphore(1);
        // Mete o buffer na posiï¿½ï¿½o 0 e inicializa os pointers a 0
        pointerWrite = 0;
        estado = 0;
        deleteNumber = 0;
        dancarinosLigados = new ArrayList<Dancarino>();
        coreografosLigados = new ArrayList<Coreografo>();
        semaforos = new ArrayList<Semaphore[]>();
        pointerReads = new ArrayList<Integer>();
        nextCoreoID = 0;
        nextDancID = 0;
    }

    public void write(MyMensagem m) throws IOException, InterruptedException {
        canWriteSemaforo.acquire();
        canDeleteSemaforo[pointerWrite].acquire();
        // Mete o buffer na posiï¿½ï¿½o pointerWrite
        buffer[pointerWrite] = m;
        releaseSems(pointerWrite);
        //Atualiza o pointerWrite incrementando +1 e fazendo o mï¿½dulo com 32
        System.out.println("PointerW - " + pointerWrite);
        pointerWrite = (++pointerWrite) % (MAXMENSAGENS);
        canWriteSemaforo.release();
    }

    /**
     * @param numeroPointerRead POSICAO A SER LIDA
     * @return MyMensagem m, guardada na posicao
     * @throws IOException          TODO
     * @throws InterruptedException TODO
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

    public int open(Coreografo c) {
        coreografosLigados.add(c);
        return nextCoreoID++;
    }

    public void close(Coreografo c) {
        Coreografo cor = coreografosLigados.get(coreografosLigados.indexOf(c));
        cor.closeGUI();
        cor.Terminar();
        semaforos.remove(coreografosLigados.indexOf(c));
        coreografosLigados.remove(c);
    }

    public int open(Dancarino d) {
        dancarinosLigados.add(d);
        Semaphore[] a = addSemaphore();
        semaforos.add(a);
        nextDancID++;
        return addPuts();
    }

    public void close(Dancarino d) {
        Dancarino danc = dancarinosLigados.get(dancarinosLigados.indexOf(d));
        danc.closeGUI();
        danc.Terminar();
        semaforos.remove(dancarinosLigados.indexOf(d));
        dancarinosLigados.remove(d);
    }

    public int getNextDancID() {
        return nextDancID;
    }

    public Semaphore[] addSemaphore() {
        int p = getPointerWrite();
        Semaphore[] s = new Semaphore[MAXMENSAGENS];
        for (int i = 0; i < MAXMENSAGENS; i++) {
            if (i < p && p != 0)
                s[i] = new Semaphore(1);
            else
                s[i] = new Semaphore(0);
        }
        return s;
    }

    public Semaphore[] addDelSemaphore() {
        Semaphore[] s = new Semaphore[MAXMENSAGENS];
        for (int i = 0; i < MAXMENSAGENS; i++) {
            s[i] = new Semaphore(1);
        }
        return s;
    }

    public void releaseSems(int p) {
        for (int i = 0; i < semaforos.size(); i++) {
            semaforos.get(i)[p].release();
        }
    }

    public int addPuts() {
        pointerReads.add(0);
        return pointerReads.size() - 1;
    }

    public boolean todosJaLeramMensagem(int numMensagem) {
        int sum = 0;
        for (int i = 0; i < semaforos.size(); i++) {
            sum += semaforos.get(i)[numMensagem].availablePermits();
        }
        return sum == 0;
    }

    public void run() {
        while (true) {
            switch (estado) {
                case 0:
                    if (todosJaLeramMensagem(deleteNumber) && canDeleteSemaforo[deleteNumber].availablePermits() == 0 &&
                            buffer[deleteNumber] != null && dancarinosLigados.size() > 0) {
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