package TCPChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends JFrame implements ActionListener {

    public JFrame login, main;
    public JButton submit, send;
    public JTextField name, chat;
    public JLabel lb1;
    public JTextArea content;

    String temp = "";

    Socket socket;
    OutputStream os;
    InputStream is;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public void GUI()
    {
        login = new JFrame();
        main = new JFrame();

        login.setTitle("Login");
        login.setSize(500,200);
        login.getContentPane().setLayout(null);
        login.setResizable(false);

        main.setTitle("Client");
        main.setSize(600, 450);
        main.getContentPane().setLayout(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        Font fo = new Font("Arial", Font.PLAIN, 15);

        lb1 = new JLabel("Pls enter your name");
        lb1.setBounds(150,10,120,60);


        submit = new JButton("Enter");
        submit.addActionListener(this);
        submit.setBounds(380, 90, 70, 40);
        name = new JTextField("");
        name.setFont(fo);
        name.setBounds(50, 100, 300, 30);
        name.setBackground(Color.white);
        login.add(lb1);
        login.add(name);
        login.add(submit);
        login.setVisible(true);

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

    }

    public ChatClient(String str) throws IOException, ClassNotFoundException {
        super(str);
        GUI();
        socket = new Socket("127.0.0.1",1234);
        System.out.println("Da ket noi toi server!");
        os = socket.getOutputStream();
        is = socket.getInputStream();
        oos = new ObjectOutputStream(os);
        ois = new ObjectInputStream(is);
        while(true) {
            String stream = ois.readObject().toString();
            temp += stream + '\n';
            content.setText(temp);
        }
    }


    public static void main(String[] args) throws Exception {
        ChatClient obj = new ChatClient("Chat");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit)
        {
            if(name.getText().equals("")){
                lb1.setText("Ban chua nhap ten");
            }
            else {
                login.setVisible(false);
                main.setVisible(true);
            }
        }
        if(e.getSource() == send)
        {
            try
            {

                temp+=name.getText() + ": " + chat.getText() + "\n";
                content.setText(temp);
                oos.writeObject(name.getText() + ": " + chat.getText());
                chat.setText("");
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
