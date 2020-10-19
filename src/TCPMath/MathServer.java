package TCPMath;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MathServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(7000);
        while (true)
        {
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
            }catch (Exception e)
            {
                socket.close();
                e.printStackTrace();
            }

        }
    }
}

class ClientHandler extends Thread
{
    final ObjectOutputStream oos;
    final ObjectInputStream ois;
    final Socket s;

    // Constructor
    public ClientHandler(Socket s, ObjectOutputStream oos, ObjectInputStream ois)
    {
        this.s = s;
        this.oos = oos;
        this.ois = ois;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                String stream = ois.readObject().toString();
                double result = StringHandler.calculate(stream);
                System.out.println(result);
                oos.writeObject("Ket qua cua phep tinh " + stream + " = " + result);


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
