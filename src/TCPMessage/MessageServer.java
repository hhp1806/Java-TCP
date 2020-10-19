package TCPMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serversocket = new ServerSocket(1234);
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
                Thread t = new ClientHandler(socket, ois, oos);
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
    public String InHoa(String str)
    {
        String result = "";
        char c;
        if(str.length() > 0)
        {
            for(int i = 0; i < str.length(); i++)
            {
                c = str.charAt(i);
                if(c >= 97 && c <= 122)
                {
                    c-= 32;
                }
                result+= c;
            }
        }
        return result;
    }
    public String InThuong(String str)
    {
        String result = "";
        char c;
        if(str.length() > 0)
        {
            for(int i = 0; i < str.length(); i++)
            {
                c = str.charAt(i);
                if(c >=65 && c <= 90)
                {
                    c+=32;
                }
                result +=c;
            }
        }
        return result;
    }
    public String InHoaThuong(String str)
    {
        String result = "";
        char c;
        if(str.length() > 0)
        {
            for(int i = 0; i < str.length(); i++)
            {
                c = str.charAt(i);
                if(c >= 97 && c <= 122)
                {
                    c-= 32;
                }
                else if(c >=65 && c <= 90)
                {
                    c+=32;
                }
                else if(c == 32)
                {
                    c = c;
                }
                result +=c;
            }
        }
        return result;
    }
    public int CountWord(String str)
    {
        int count = 0;
        char ch[] = new char[str.length()];
        if(str.isEmpty() || str == null)
        {
            return 0;
        }
        if(str.length() > 0)
        {
            for(int i = 0; i < str.length(); i++)
            {
                ch[i] = str.charAt(i);
                if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                {
                    count++;
                }
            }
        }
        return count;
    }

    public int CountVowel(String str)
    {
        int count = 0;
        char ch[] = new char[str.length()];
        if(str.isEmpty() || str == null)
        {
            return 0;
        }
        if(str.length() > 0)
        {
            for(int i = 0; i < str.length(); i++)
            {
                ch[i] = str.charAt(i);
                if(ch[i] == 65||ch[i] == 69||ch[i]==73||ch[i]==79||ch[i]==85||ch[i]==97||ch[i]==101||ch[i]==105||ch[i]==111||ch[i]==117)
                {
                    count++;
                }
            }
        }
        return count;
    }

    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final Socket s;


    // Constructor
    public ClientHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos)
    {
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
                System.out.println(stream);
                oos.writeObject("In hoa: " + InHoa(stream) + "\n"
                              + "In thuong: " + InThuong(stream) + "\n"
                              + "In hoa thuong: " + InHoaThuong(stream) + "\n"
                              + "So tu: " + CountWord(stream) + "\n"
                              + "So nguyen am: " + CountVowel(stream) + "\n");

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
