package pavelkorzhenko;

/**
 * @author Pavel Korzhenko / Sergey Iryupin
 * @version 0.2.2 2017/11/19
 * @task 08
 * @mark
 *
 * 1. Разобраться с кодом, составить список замечаний и улучшений по проекту "Сетевой час".
 *
 * 2. *** Реализовать хотя бы одно из собственных предложений.
 *      +) /help - список команд сервера
 *      +) exiting message from username
 *      +) /me message - вывод в чат сообщние Mike says message
 *      -) /list - список пользователей в чате.
 *      -) /nick username message - передача приватного сообщение пользователю - username
 *
 * Java. Level 2. Lesson 8
 * Simple chat client
 * To make jar file:
 *  create manifest.txt file with 1 line
 *      Main-Class: GbJava2Task08
 *  jar -cvmf manifest.txt GbJava2Task08.jar
 *      ChatClient*.class IConstants.class
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.net.*;
import java.io.*;

class GbJava2Task08 extends JFrame implements ActionListener, TIConstants {

    final String TITLE_OF_PROGRAM = "Client for net.chat";
    final int START_LOCATION = 200;
    final int WINDOW_WIDTH = 350;
    final int WINDOW_HEIGHT = 450;
    final String BTN_ENTER = "Enter";
    final String AUTH_INVITATION = "You must login using command\n"+
        "auth <login> <passwd>";

    JTextArea dialogue; // area for dialog
    JTextField message; // field for entering messages
    boolean isAuthorized; // flag of authorization

    Socket socket;
    PrintWriter writer;
    BufferedReader reader;
    private Thread thListener;

    public static void main(String[] args) {
        new GbJava2Task08();
    }

    /**
     * Constructor:
     * Creating a window and all the necessary elements on it
     */
    GbJava2Task08() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent event) { // if window closed
                try {
                    writer.println(EXIT_COMMAND);
                    writer.flush();
                    socket.close();
                } catch (Exception ex) {}
            }
            public void windowDeactivated(WindowEvent event) {}
            public void windowActivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {}
        });
        // area for dialog
        dialogue = new JTextArea();
        dialogue.setLineWrap(true);
        dialogue.setEditable(false);
        // follow to the cursor
        DefaultCaret caret = (DefaultCaret)dialogue.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollBar = new JScrollPane(dialogue);
        // panel for connamd field and button
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        message = new JTextField();
        message.addActionListener(this);
        JButton enter = new JButton(BTN_ENTER);
        enter.addActionListener(this);
        // adding all elements to the window
        bp.add(message);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
        Connect(); // connect to the Server
    }

    /**
     * Connect to the Server
     */
    void Connect()  {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            // создаем поток для чтения
            thListener = new Thread(new Task08ServerListener());
            thListener.start();
        } catch (Exception ex) { 
            System.out.println(ex.getMessage());
        }
        dialogue.append(AUTH_INVITATION + "\n");
        isAuthorized = false;

        try {
            thListener.join();
        } catch (InterruptedException ex) {
            // прервем поток чтения.
            if (thListener.isAlive()) {
                thListener.interrupt();
            }
            ex.printStackTrace();
        }
    }

    /**
     * ServerListener: get messages from Server
     */
    class Task08ServerListener implements Runnable {
        String message;
        public void run() {
            try {
                while ((message = reader.readLine()) != null && !Thread.currentThread().isInterrupted()) {
                    if (message.startsWith("Hello, ")) // check authorisation
                        isAuthorized = true;
                    if (!message.equals("\0") && isAuthorized)
                            dialogue.append(message + "\n");
                    if (message.equals(AUTH_FAIL))
                        System.exit(-1); // terminate client
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Listener of events from message field and enter button
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (message.getText().trim().length() > 0) {
            writer.println(message.getText());
            writer.flush();
        }
        message.setText("");
        message.requestFocusInWindow();
    }
}