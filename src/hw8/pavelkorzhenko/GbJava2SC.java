package pavelkorzhenko;

/**
 * @author Pavel Korzhenko / Sergey Iryupin
 * @version 0.2.2 2017/11/19
 * @task 08
 * @mark

 * Java. Level 2. Lesson 7
 * Simple chat client
 */
import java.io.*;
import java.net.*;
import java.util.*;

class  GbJava2SC implements TIConstants {

    Socket socket;
    PrintWriter writer;
    BufferedReader reader;
    Scanner scanner;
    String message;
    Thread thListener;

    public static void main(String[] args) {
        new GbJava2SC();
    }

    GbJava2SC() {
        scanner = new Scanner(System.in);
        System.out.println(CONNECT_TO_SERVER);
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            writer.println(getLoginAndPassword()); // send: auth <login> <passwd>
            writer.flush();

            // создаем поток для чтения
            thListener = new Thread(new ClientServerListener());
            thListener.start();

            do {
                message = scanner.nextLine();
                writer.println(message);
                writer.flush();
            } while (!message.equals(EXIT_COMMAND));

            // прервем поток чтения.
            if (thListener.isAlive()) {
              thListener.interrupt();
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
    class ClientServerListener implements Runnable {
        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null && !Thread.currentThread().isInterrupted()) {
                    if (!message.equals("\0")) {
                        System.out.print(message + "\n");
                    } else {
                        System.out.print(CLIENT_PROMPT);
                    }
                    if (message.equals(AUTH_FAIL)) {
                        System.exit(-1); // terminate client
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}