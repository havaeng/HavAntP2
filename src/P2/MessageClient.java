package P2;

/*
 * Hej du som ska testa mitt program! För att kunna simulera tre olika klienter/datorer finns det
 * en instruktion jag har förberett. Den ligger i files//readme.
 */

import P1.Message;
import P1.Viewer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/*
Klar
 */

/**
 * @author Anthon Haväng
 * @version 2.0
 *
 */
public class MessageClient {
    private ObjectInputStream ois;
    private Socket socket;
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * Constructor for this class. When this class is instantiated, a new socket (assigned to
     * instance variable Socket socket is created with the given String ip and int port from
     * the arguments in the constructor parameter. With this socket-object, an ObjectInputStream
     * is created. After this, the inner class Listener's thread is started.
     * @param ip needs to contain a suitable IP-adress.
     * @param port port ID number that connects client and server.
     */
    public MessageClient(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Listener listener = new Listener();
        new Thread(listener).start();
    }

    /**
     * This method adds the given a listener-object (P2Viewer) to the collection of listeners
     * in the PropertyChangeSupport class.
     * @param listener is going to be a P2Viewer-object.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        changes.addPropertyChangeListener(listener);
    }

    /**
     *  Listens for when a Message-object is read from the stream. Informs listeners (P2Viewer-instance)
     *  that properties of Message m are changed and "sends" the "new" object to P2Viewer for display.
     *  The property isn't really "changed", like the method name implies: it is assigned a value.
     */
    private class Listener implements Runnable {

        public void run(){
            try {
                while (true){
                    Message m = (Message) ois.readObject();
                    changes.firePropertyChange("m", null, m);
                }
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * A main method, implemented simply to simulate 3 running clients. Note: 6th window will appear behind
     * window no. 5.
     * @param args java command line arguments
     */
    public static void main(String[] args) {
        MessageClient messageClient2 = new MessageClient("127.0.0.1", 2343);
        P2Viewer v6 = new P2Viewer(messageClient2, 250, 320);
        Viewer.showPanelInFrame(v6, "Viewer 6", 800, 400);
    }
}
