package Demos;

public class HelloWorldVer03 extends Thread {

    public HelloWorldVer03(String name) {
        super(name);
    }

    public HelloWorldVer03() {
    }

    public void run() {
        System.out.printf("[%s] Running...\n", this.getName());
    }

    public static void main(String[] args) {
        Thread th1, th2, th3;

        th1 = new HelloWorldVer03();
        th1.start();

        th2 = new HelloWorldVer03();
        th2.setName("Hello2");
        th2.start();

        th3 = new HelloWorldVer03("Hello3");
        th3.start();
    }
}
