package pavelkorzhenko;

/**
 * @author Pavel Korzhenko / Sergey Iryupin
 * @version 0.2.2 2017/11/19
 * @task 08
 * @mark
 *
 * Java. Level 2. Lesson 7
 * Simple server for chat
 *
 * @run java -classpath .;d:/java/jar/sqlite-jdbc-3.16.1.jar pavelkorzhenko/GbJava2SS
 */
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

class GbJava2SS implements TIConstants {

    ServerSocket server;
    Socket socket;
    List<ClientHandler> clients;
    List<String> clientsName;

    public static void main(String[] args) {
        new GbJava2SS();
    }

    GbJava2SS() {
        System.out.println(SERVER_START);
        new Thread(new CommandHandler()).start();
        clients = new ArrayList<>(); // list of clients
        clientsName = new ArrayList<>(); // list of clients name
        try {
            server = new ServerSocket(SERVER_PORT);
            while (true) {
                socket = server.accept();
                System.out.println("#" + (clients.size() + 1) + CLIENT_JOINED);
                ClientHandler client = new ClientHandler(socket);
                clients.add(client); // new client in list
                new Thread(client).start();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(SERVER_STOP);
    }

    /**
     * checkAuthentication: check login and password
     */
    private boolean checkAuthentication(String login, String passwd) {
        Connection connect;
        boolean result = false;
        try {
            // connect db
            Class.forName(DRIVER_NAME);
            connect = DriverManager.getConnection(SQLITE_DB);
            // looking for login && passwd in db
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SELECT.replace("?", login));
            while (rs.next())
                result = rs.getString(PASSWD_COL).equals(passwd);
            // close all
            rs.close();
            stmt.close();
            connect.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    /**
     * CommandHandler: processing of commands from server console
     */
    class CommandHandler implements Runnable {
        Scanner scanner = new Scanner(System.in);

        @Override
        public void run() {
            String command;
            do
                command = scanner.nextLine();
            while (!command.equals(EXIT_COMMAND));
            try {
                server.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * send message to user by nickname
     *
     * @param srcUser - current username
     * @param dstUser - username
     * @param message - message
     */
    void sendMsgToName(String srcUser, String dstUser, String message) {
        for (ClientHandler client : clients) {
            System.out.println("Name: " + client.name);
            if (client.name.equals(dstUser)) {
                client.sendMsg(srcUser + " send to you message: " + message);
                client.sendMsg("\0");
            }
        }
    }

    /**
     * broadcastMsg: sending a message to all clients
     */
    void broadcastMsg(String msg) {
        for (ClientHandler client : clients)
            client.sendMsg(msg);
    }

    /**
     * ClientHandler: service requests of clients
     */
    class ClientHandler implements Runnable {
        BufferedReader reader;
        PrintWriter writer;
        Socket socket;
        String name;

        ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                name = "Client #" + (clients.size() + 1);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        void sendMsg(String msg) {
            try {
                writer.println(msg);
                writer.flush();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        @Override
        public void run() {
            String message;
            try {
                do {
                    message = reader.readLine();
                    if (message != null) {
                        System.out.println(name + ": " + message);
                        if (message.startsWith(AUTH_SIGN)) {
                            String[] wds = message.split(" ");
                            if (checkAuthentication(wds[1], wds[2])) {
                                name = wds[1];
                                sendMsg("Hello, " + name);
                                sendMsg("\0");
                            } else {
                                System.out.println(name + ": " + AUTH_FAIL);
                                sendMsg(AUTH_FAIL);
                                sendMsg("\0");
                                message = EXIT_COMMAND;
                            }
                            clientsName.add(name);
                        } else if (!message.equalsIgnoreCase(EXIT_COMMAND)) {
                            if (message.startsWith(NICK_COMMAND)) {
                                String[] params = message.split(" ");
                                sendMsgToName(name, params[1], params[2]);
                                sendMsg("user " + params[1] + " receive " + params[2]);
                                sendMsg("\0");
                            } else if (message.equalsIgnoreCase(LIST_COMMAND)) {
                                sendMsg("List of Clients");
                                for(String clientName: clientsName) {
                                    sendMsg("User :" + clientName);
                                }
                                sendMsg("\0");
                            } else if (message.startsWith(MESAY_COMMAND)) {
                                broadcastMsg(name + " says " + message.replaceFirst("/me", ""));
                                broadcastMsg("\0");
                            } else if (message.equalsIgnoreCase(HELP_COMMAND)) {
                                sendMsg(HELP_MESSAGE);
                                sendMsg("\0");
                            } else {
                                broadcastMsg(name + ": " + message);
                                broadcastMsg("\0");
                            }
                        } else {
                            broadcastMsg(name + ": is exiting");
                            broadcastMsg("\0");
                        }
                    }
                } while (!message.equalsIgnoreCase(EXIT_COMMAND));
                clients.remove(this); // delete client from list
                clientsName.remove(name);
                socket.close();
                System.out.println(name + CLIENT_DISCONNECTED);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}