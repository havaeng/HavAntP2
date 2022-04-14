package P2;

/*
 * Hej du som ska testa mitt program! För att kunna simulera tre olika klienter/datorer finns det
 * en instruktion jag har förberett. Den ligger i files//readme.
 */

import P1.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is a multithreaded server. Each connected client gets its own thread of this class.
 * When instantiated, this class adds an object of itself to P1.MessageManager's list of Callback objects.
 * An inner class listens for connections from clients, and "gives" each client an own thread.
 * Another inner class writes Message objects from Buffer buffer to the stream.
 * @author Anthon Haväng
 * @version 2.0
 */
public class MessageServer implements Callback {
    private MessageManager mm;
    private ServerSocket serverSocket;
    private Buffer<Message> buffer = new Buffer<>();
    private int counter = 0;

    /**
     * Constructor. Assigns argument parameter MessageManager mm to instance variable of
     * the same class and name. Uses the given int port and creates a ServerSocket with it.
     * After this, the class adds the instantiated object of itself to ArrayList<Callback> callbacks
     * located in P1.MessageManager. Finally, the thread in inner class Connection is started.
     * @param mm MessageManager datatype, to give access to MessageManager's methods.
     * @param port port ID to connect with a client.
     */
    public MessageServer(MessageManager mm, int port){
        this.mm = mm;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }
        mm.addToCallback(this);
        new Connection(/*port*/).start();
    }

    /**
     * Method is implemented from P1.Callback (interface).
     * This method adds Message objects in the Message buffer of MessageServer.
     * The for-loop iterates for an amount of times equal to the number of client connections
     * made to the server. Without this, the Viewer's animations will execute asynchronously.
     * @param m Message object that goes into Buffer<Message> buffer.
     */
    @Override
    public void setMessage(Message m) {
        for (int i = 0; i < counter; i++) { //to synchronize animations. Not "synchronize" in context of Threads.
            buffer.put(m);
        }
    }

    /**
     * This inner class of MessageServer is a thread. When this class is instantiated,
     * it's thread starts.
     * It listens for connections from clients. When a client connects, an object of
     * MessageServer's other inner class MessageHandler is instantiated. Each client gets
     * its own MessageHandler thread. Instance variable counter of MessageServer is incremented
     * in order to synchronise animations later.
     */
    private class Connection extends Thread {
        //private int port;

        /**
         * This method defines the thread of this class. Each time a client connects,
         * they get their own thread of message handler, and int counter increments.
         */
        @Override
        public void run(){
            Socket socket;
            while (true){
                try {
                    socket = serverSocket.accept();
                    new MessageHandler(socket);
                    counter++; //to synchronize animations.
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This inner class of MessageServer is a thread. As soon as the instance of this class is
     * created, it's thread starts running. The instance then communicates with an object of MessageClient
     * via object streams. The thread pulls a Message object out of the buffer and sends it into the client
     * via an object stream and the writeObject()-method. The stream is then flushed and thus closes.
     */
    private class MessageHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;

        /**
         * Constructor for this class. A socket is required and provided, which is assigned to
         * the instance variable socket, and assigned as a socket object. After this the thread starts.
         * @param socket expects a socket with a correct port corresponding to port used by connecting client.
         * @throws IOException is thrown when something is wrong with the socket.
         */
        public MessageHandler(Socket socket) throws IOException {
            this.socket = socket;
            socket = new Socket();
            start();
        }

        /**
         * Thread method of this class. An ObjectOutputStream is created with the socket (which
         * is now an instance variable). A Message is pulled out of the buffer of MessageServer's
         * Buffer<Message> buffer and sent to the connected client via the ObjectOutputStream's
         * writeObject()-method. The stream is flushed and closed. Finally, the socket closes
         * and (consequentially) the client disconnects.
         */
        @Override
        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    Message m = buffer.get();
                    oos.writeObject(m);
                    oos.flush();
                }
            } catch (IOException | InterruptedException e){
                e.printStackTrace();
            } try {
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

