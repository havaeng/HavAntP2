package P1;

import javax.swing.*;
import java.io.*;

public class ObjectFileProducer implements MessageProducer {
    private Message[] messages;
    private int size = 0;
    private int delay = 0;
    private int times = 0;
    private int currentIndex = -1;

    public ObjectFileProducer(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        try {
            this.times = ois.readInt();
            this.delay = ois.readInt();
            this.size = ois.readInt();
            this.messages = new Message[times];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = (Message) ois.readObject();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delay() {
        return delay;
    }

    @Override
    public int times() {
        return times;
    }

    @Override
    public int size() {
        return (messages==null) ? 0 : messages.length;
    }

    @Override
    public Message nextMessage() {
        if(size()==0)
            return null;
        currentIndex = (currentIndex+1) % messages.length;
        return messages[currentIndex];
    }
}
