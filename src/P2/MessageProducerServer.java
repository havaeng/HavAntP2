package P2;

import P1.MessageProducer;
import P1.MessageProducerInput;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageProducerServer implements Runnable{
    private ServerSocket serverSocket;
    private ObjectInputStream ois;
    private MessageProducerInput mpi;
    private MessageProducer mp;

    public MessageProducerServer(MessageProducerInput mpi, int port){
        this.mpi = mpi;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startServer(){
        new Thread(this).start();
    }

    @Override
    public void run(){
        while (true){
            try {
                Socket socket = serverSocket.accept();
                ois = new ObjectInputStream(socket.getInputStream());
                mp = (MessageProducer) ois.readObject();
                mpi.addMessageProducer(mp);
                socket.close();
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
