package Demos;

public class HelloWorldVer04 implements Runnable {


    public HelloWorldVer04() {
    }


    @Override
    public void run() {
        System.out.printf("[%s]Running...\n",
                Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Runnable r1, r2, r3;
        Thread th1, th2, th3;

        r1 = new HelloWorldVer04();
        r2 = new HelloWorldVer04();
        r3 = new HelloWorldVer04();

        th1 = new Thread(r1);
        th1.start();

        th2 = new Thread(r2);
        th2.setName("Hello2");
        th2.start();

        th3 = new Thread(r3, "Hello3");
        th3.start();

    }


}
