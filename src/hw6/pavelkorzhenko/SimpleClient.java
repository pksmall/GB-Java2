package pavelkorzhenko;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/11/11
 * @task 06
 * @mark
 *
 * 1. Разобраться с кодом, показанным на занятии. Модифицировать код SimpleClient так, чтобы клиент мог
 * читать сообщения от сервера.
 * @see this
 *
 * 2. *** Написать приложение, атакующее HelloServer так, чтобы вызвать отказ в обслуживании.
 * @see SocketClient
 */

import java.io.*;
import java.net.*;
import java.util.*;

class SimpleClient {

    final String SERVER_ADDR = "127.0.0.1"; // or "localhost"
    final int SERVER_PORT = 2048;
    final String CLIENT_PROMPT = "$ ";
    final String CONNECT_TO_SERVER = "Connection to server established.";
    final String CONNECT_CLOSED = "Connection closed.";
    final String EXIT_COMMAND = "exit"; // command for exit

    Socket socket;
    Scanner scanner;
    PrintWriter writer;
    BufferedReader reader;
    String message;

    public static void main(String[] args) {
        new SimpleClient();
    }

    SimpleClient() {
        scanner = new Scanner(System.in); // for keyboard input
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            System.out.println(CONNECT_TO_SERVER);
            do {
                System.out.print(CLIENT_PROMPT);
                message = scanner.nextLine();
                writer.println(message);
                writer.flush();
                System.out.println(reader.readLine());
            } while (!message.equals(EXIT_COMMAND));
            writer.close();
            socket.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(CONNECT_CLOSED);
    }
}