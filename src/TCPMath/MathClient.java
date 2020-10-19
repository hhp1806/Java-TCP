package TCPMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MathClient extends JFrame implements ActionListener {
    public JButton send;
    public JTextField txt;
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
        sp.setBounds(50, 50, 500, 200);
        send = new JButton("Gui");
        send.addActionListener(this);
        send.setBounds(480, 290, 70, 40);
        txt = new JTextField("");
        txt.setFont(fo);
        txt.setBounds(50, 300, 400, 30);
        txt.setBackground(Color.white);
        add(txt);
        add(send);
        add(sp);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MathClient obj = new MathClient("Math");
    }

    public MathClient(String str) throws IOException, ClassNotFoundException {
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
            temp += stream + '\n';
            content.setText(temp);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == send) {
            try {
                oos.writeObject(txt.getText());
                txt.setText("");
                txt.requestFocus();
                content.setVisible(false);
                content.setVisible(true);
            } catch (Exception r) {
                r.printStackTrace();
            }
        }
    }
}
