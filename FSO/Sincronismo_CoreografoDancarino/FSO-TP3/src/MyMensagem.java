public class MyMensagem {
    public static final int TAMANHO = 8;
    private int numero, ordem;

    public MyMensagem() {
        numero = 0;
        ordem = 0;
    }

    public int gerarOrdem() {
        int i = (int) (Math.random() * 5);
        return i;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String toString() {
        final int i = ordem;
        String S = "Mensagem->" + this.numero + " " + this.ordem;

        switch (i) {
            case 0:
                S += " Parar (false)";
                break;
            case 1:
                S += " Frente (10)";
                break;
            case 2:
                S += " Direita (0,45)";
                break;
            case 3:
                S += " Esquerda (0,45)";
                break;
            case 4:
                S += " Tras (10)";
                break;

        }
        return S;
    }

}
