package TCPDatabase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class DatabaseServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(7000);
        while (true) {
            Socket socket = null;
            try {
                System.out.println("Dang doi client...");
                socket = serversocket.accept();
                System.out.println("Client da ket noi!");
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                ObjectInputStream ois = new ObjectInputStream(is);

                Thread t = new ClientHandler(socket, oos, ois);
                t.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }

        }
    }
}

class ClientHandler extends Thread {
    final ObjectOutputStream oos;
    final ObjectInputStream ois;
    final Socket s;

    String label = "";
    String data = "";
    String output = "";

    // Constructor
    public ClientHandler(Socket s, ObjectOutputStream oos, ObjectInputStream ois) {
        this.s = s;
        this.oos = oos;
        this.ois = ois;
    }

    @Override
    public void run() {
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/hhp18/OneDrive/Documents/DATA.accdb");

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Table1");
            ResultSetMetaData rsmd = rs.getMetaData();


            int col_num = rsmd.getColumnCount();
            for (int j = 1; j <= col_num; j++) {
                label += rsmd.getColumnLabel(j) + " ";
            }
            while (rs.next()) {
                data += "\n";
                for (int i = 1; i <= col_num; i++) {
                    data += rs.getObject(i) + " ";
                }
            }
            rs.close();
            stm.close();
            output = label + "\n" + data;
            oos.writeObject(output);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
