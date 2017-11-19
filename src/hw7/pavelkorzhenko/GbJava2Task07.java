package pavelkorzhenko;

/**
 * @author Pavel Korzhenko
 * @version 0.2 2017/11/16
 * @task 07
 * @mark
 *
 * 1. Разобраться с кодом, скачать, установить и опробовал в работе SQLite драйвер.
 *
 * 2. * Разобраться: что происходит с нитью ServerListener клиента, когда клиент завершает работу.
 *      Предложить варианты исправления кода.
 * @see lines 42-56
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

class GbJava2Task07 implements IConstants {
    Socket socket;
    PrintWriter writer;
    BufferedReader reader;
    Scanner scanner;
    String message;

    public static void main(String[] args) {
        new GbJava2Task07();
    }

    GbJava2Task07() {
        scanner = new Scanner(System.in);
        System.out.println(CONNECT_TO_SERVER);
        Thread thListener;
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer.println(getLoginAndPassword()); // send: auth <login> <passwd>
            writer.flush();

            // создаем поток для чтения
            thListener = new Thread(new ServerListener());
            thListener.start();

            do {
                message = scanner.nextLine();
                writer.println(message);
                writer.flush();
            } while (!message.equals(EXIT_COMMAND));

            // заканчиваем поток для чтения
            if (thListener.isAlive()) {
                System.out.println("thread is alive");
                thListener.interrupt();
            } else {
                System.out.println("thread dead");
            }

            socket.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(CONNECT_CLOSED);
    }

    /**
     * getLoginAndPassword: read login and password from keyboard
     */
    String getLoginAndPassword() {
        System.out.print(LOGIN_PROMPT);
        String login = scanner.nextLine();
        System.out.print(PASSWD_PROMPT);
        return AUTH_SIGN + " " + login + " " + scanner.nextLine();
    }

    /**
     * ServerListener: get messages from Server
     */
    class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    if (Thread.currentThread().interrupted()) {
                        System.out.println("e..");
                        System.exit(-1); // terminate client
                    }
                    if ((message = reader.readLine()) != null) {
                        System.out.print(message.equals("\0") ?
                                CLIENT_PROMPT : message + "\n");
                        if (message.equals(AUTH_FAIL)) {
                            System.exit(-1); // terminate client
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}