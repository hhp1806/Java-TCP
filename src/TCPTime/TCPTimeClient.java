package TCPTime;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class TCPTimeClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 2356);
        DataInputStream din = new DataInputStream(socket.getInputStream());
        String time = din.readUTF();
        System.out.println(time);
        socket.close();

    }
}
