package TCPDatabase;



import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class DatabaseClient extends JFrame {

    public JTextArea content;

    String temp = "";

    Socket socket;
    OutputStream os;
    InputStream is;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public void GUI() {

        setSize(600, 450);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Font fo = new Font("Arial", Font.PLAIN, 15);
        content = new JTextArea();
        content.setFont(fo);
        content.setBackground(Color.white);

        content.setEditable(false);
        JScrollPane sp = new JScrollPane(content);
        sp.setBounds(50, 50, 500, 300);
        add(sp);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatabaseClient obj = new DatabaseClient("Math");
    }

    public DatabaseClient(String str) throws IOException, ClassNotFoundException {
        super(str);
        GUI();
        socket = new Socket("127.0.0.1",7000);
        System.out.println("Da ket noi toi server!");
        os = socket.getOutputStream();
        is = socket.getInputStream();
        oos = new ObjectOutputStream(os);
        ois = new ObjectInputStream(is);

        while(true) {
            String stream = ois.readObject().toString();
//            temp += stream + '\n';
            content.setText(stream);
        }

    }
}
