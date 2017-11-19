package pavelkorzhenko;

interface TIConstants {
    final String DRIVER_NAME = "org.sqlite.JDBC";
    final String SQLITE_DB = "jdbc:sqlite:chat.db";
    final String SERVER_ADDR = "localhost"; // server net name or "127.0.0.1"
    final int SERVER_PORT = 2048; // servet port
    final String SERVER_START = "Server is started...";
    final String SERVER_STOP = "Server stopped.";
    final String CLIENT_JOINED = " client joined.";
    final String CLIENT_DISCONNECTED = " disconnected.";
    final String CLIENT_PROMPT = "$ "; // client prompt
    final String LOGIN_PROMPT = "Login: ";
    final String PASSWD_PROMPT = "Passwd: ";
    final String AUTH_SIGN = "auth";
    final String AUTH_FAIL = "Authentication failure.";
    final String SQL_SELECT = "SELECT * FROM users WHERE login = '?'";
    final String PASSWD_COL = "passwd";
    final String CONNECT_TO_SERVER = "Connection to server established.";
    final String CONNECT_CLOSED = "Connection closed.";
    final String EXIT_COMMAND = "exit"; // command for exit
    final String NICK_COMMAND = "/nick"; // send message to nickname
    final String MESAY_COMMAND = "/me"; // me says command
    final String LIST_COMMAND = "/list"; // list
    final String HELP_COMMAND = "/help"; // help command
    final String HELP_MESSAGE = "Available commands\n/list - list user into chat now\n" +
            "/nick username message - send private message to username\n" +
            "/me message - says something about\n" +
            "/help - this help message\n"; // help message
}