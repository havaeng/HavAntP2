package P2;

import P1.MessageProducer;
import P1.TextFileProducer;
import java.io.*;
import java.net.Socket;

/*
Klar
 */

public class MessageProducerClient {
    private String ip;
    private Socket socket;
    private ObjectOutputStream oos;
    private int port;

    public MessageProducerClient(String ip, int port) throws IOException{
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException{
        socket = new Socket(ip, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void send(MessageProducer mp) throws IOException {
        connect();
        oos.writeObject(mp);
        oos.flush();
        disconnect();
    }

    public static void main(String[] args) throws IOException {
        MessageProducerClient mpClient1 = new MessageProducerClient("127.0.0.1",3343);
        //mpClient1.send(TestP2Input.getArrayProducer(10,100));
        //mpClient1.send(new ShowGubbe(5000));
        mpClient1.send(new TextFileProducer("files/new.txt"));
    }
}
