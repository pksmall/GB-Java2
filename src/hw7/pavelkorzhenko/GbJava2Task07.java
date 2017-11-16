package pavelkorzhenko;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/11/14
 * @task 07
 * @mark
 *
 * 1. Разобраться с кодом, скачать, установить и опробовал в работе pavelkorzhenko.SQLite драйвер.
 *
 * 2. * Разобраться: что происходит с нитью ServerListener клиента, когда клиент завершает работу.
 *      Предложить варианты исправления кода.
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
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer.println(getLoginAndPassword()); // send: auth <login> <passwd>
            writer.flush();
            new Thread(new ServerListener()).start();
            do {
                message = scanner.nextLine();
                writer.println(message);
                writer.flush();
            } while (!message.equals(EXIT_COMMAND));
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
                while ((message = reader.readLine()) != null) {
                    System.out.print(message.equals("\0")?
                            CLIENT_PROMPT : message + "\n");
                    if (message.equals(AUTH_FAIL))
                        System.exit(-1); // terminate client
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}