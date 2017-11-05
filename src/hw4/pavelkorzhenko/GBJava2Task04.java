package pavelkorzhenko;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/11/01
 * @task 04
 * @mark
 *
 * 1. Создать окно для клиентской части чата: большое текстовое поле для отображения переписки в центре окна.
 *    Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели. Сообщение должно
 *    отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter. При «отсылке» сообщение перекидывается
 *    из нижнего поля в центральное.
 * 2. * Задание повышенной сложности - все сообщения должны логгироваться (добавляться) в текстовый файл.
 */

public class GBJava2Task04 extends JFrame  implements ActionListener{
    final String TITLE_OF_PROGRAM = "Simple Task 04";
    final String BTN_ENTER = "Enter";

    JTextArea dialogue;
    JTextField message; // field for entering messages


    public static void main(String[] args) {
        new GBJava2Task04();
    }

    GBJava2Task04() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,200);
        setLocationRelativeTo(null);
        //setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);

        dialogue = new JTextArea();
        dialogue.setEditable(false);
        dialogue.setFont(new Font("Dialog", Font.PLAIN, 24));
        dialogue.setLineWrap(true);
        dialogue.setWrapStyleWord(true);

        JScrollPane scrollBar = new JScrollPane(dialogue);

        message = new JTextField();
        message.addActionListener(this);

        JButton enter = new JButton(BTN_ENTER);
        enter.addActionListener(this);

        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        bp.add(message);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
    }

    /**
     * Listener of events from message field and enter button
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (message.getText().trim().length() > 0) {
            try {
                /*StyledDocument doc = dialogue.getStyledDocument();
                doc.insertString(doc.getLength(), message.getText() + "\n",
                        new SimpleAttributeSet());
                doc.insertString(doc.getLength(),
                        TITLE_OF_PROGRAM.substring(0, 9) +
                                sbot.sayInReturn(message.getText(), ai.isSelected()) + "\n",
                        botStyle);*/
                dialogue.append(message.getText() + "\n");
            } catch(Exception e) { }
        }
        message.setText("");
        message.requestFocusInWindow();
    }
}

