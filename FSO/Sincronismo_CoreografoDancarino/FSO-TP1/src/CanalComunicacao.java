import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class CanalComunicacao {
    private final int MAXMENSAGENS = 32;
    private RandomAccessFile raf;
    private MappedByteBuffer buffer;
    private FileChannel canal;
    private File ficheiro;
    private String path = "Comunicacao.txt";
    private int pointerRead;
    private int pointerWrite;

    public CanalComunicacao() throws IOException {
        ficheiro = new File(path);
        //Caso n�o exista o ficheiro cria um novo ficheiro
        if (!ficheiro.exists())
            ficheiro.createNewFile();
        raf = new RandomAccessFile(new File(path), "rw");
        canal = raf.getChannel();
        buffer = canal.map(FileChannel.MapMode.READ_WRITE, 0, MAXMENSAGENS * 8);
        //limpa o buffer metendo tudo a 0
        limpar();
        //Mete o buffer na posi��o 0 e inicializa os pointers a 0
        buffer.position(0);
        pointerRead = 0;
        pointerWrite = 0;
    }

    //Limpa o buffer
    public void limpar() {
        buffer.position(0);
        for (int i = 0; i < MAXMENSAGENS * MyMensagem.TAMANHO; i++) {
            buffer.put(i, (byte) 0);
        }
    }

    public void write(MyMensagem m) throws IOException {
        FileLock fl = canal.lock(pointerWrite * MyMensagem.TAMANHO, (pointerWrite + 1) * MyMensagem.TAMANHO, false);
        //Mete o buffer na posi��o pointerWrite
        setBufferPos(pointerWrite);
        //Mete um n�mero
        buffer.putInt(m.getNumero());
        //E uma ordem
        buffer.putInt(m.getOrdem());
        //Atualiza o pointerWrite incrementando +1 e fazendo o m�dulo com 32
        pointerWrite = (++pointerWrite) % (MAXMENSAGENS);
        fl.release();
    }

    public MyMensagem readMsg() throws IOException {
        FileLock fl = canal.lock(pointerRead * MyMensagem.TAMANHO, (pointerRead + 1) * MyMensagem.TAMANHO, false);
        //Mete o buffer na posi��o pointerRead
        setBufferPos(pointerRead);
        pointerRead = (++pointerRead) % MAXMENSAGENS;
        fl.release();
        //cria uma nova mensagem com esse numero e ordem e retorna a mensagem
        MyMensagem m = new MyMensagem();
        m.setNumero(buffer.getInt());
        m.setOrdem(buffer.getInt());
        return m;

    }

    private void setBufferPos(int i) {
        //mete o buffer na posi��o n� mensagem * 8
        buffer.position(i * 8);
    }

    public void setPointerRead(int pointerRead) {
        this.pointerRead = pointerRead;
    }

    public int getPointerRead() {
        // TODO Auto-generated method stub
        return pointerRead;
    }
}