package TCPChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    public ChatServer(String str) throws IOException {
        ServerSocket serversocket = new ServerSocket(1234);
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
                Thread t = new ClientHandler("Chat", socket, ois, oos);
                t.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) throws Exception {
        ChatServer obj = new ChatServer("Chat");
    }
}

class ClientHandler extends Thread implements ActionListener
{
    public JFrame main;
    public JButton send;
    public JTextField chat;
    public JTextArea content;

    String temp = "";

    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final Socket s;

    public void GUI()
    {
        main = new JFrame();
        main.setTitle("Server");
        main.setSize(600, 450);
        main.getContentPane().setLayout(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font fo = new Font("Arial", Font.PLAIN, 15);

        content = new JTextArea();
        content.setFont(fo);
        content.setBackground(Color.white);
        content.setEditable(false);

        JScrollPane sp = new JScrollPane(content);
        sp.setBounds(50, 50, 500, 200);
        send = new JButton("Gui");
        send.addActionListener(this);
        send.setBounds(480, 290, 70, 40);
        chat = new JTextField("");
        chat.setFont(fo);
        chat.setBounds(50, 300, 400, 30);
        chat.setBackground(Color.white);
        main.add(chat);
        main.add(send);
        main.add(sp);
        main.setVisible(true);
    }


    // Constructor
    public ClientHandler(String str, Socket s, ObjectInputStream ois, ObjectOutputStream oos)
    {
        super(str);
        GUI();
        this.s = s;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                String stream = ois.readObject().toString();
                temp += stream + '\n';
                content.setText(temp);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == send) {
            try
            {

                temp+="Server: " + chat.getText() + "\n";
                content.setText(temp);
                oos.writeObject("Server: " + chat.getText());
                chat.setText("");
                //temp = "";
                chat.requestFocus();
                content.setVisible(false);
                content.setVisible(true);

            }
            catch (Exception r)
            {
                r.printStackTrace();
            }
        }
    }
}
