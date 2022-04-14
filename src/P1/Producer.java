package P1;

/**
 * This class is a Thread. Uses the run()-method to put all the values in the collection of P1.Message via
 * nextMessage() into a buffer.
 * @author Anthon Haväng
 * @version 1.0
 */
public class Producer extends Thread{
    Buffer<MessageProducer> producerBuffer;
    Buffer<Message> messageBuffer;

    /**
     * Constructor.
     * Creates instances of P1.Producer.
     * Requires a collection of P1.Buffer with P1.Message-objects and another collection of P1.Buffer with
     * P1.MessageProducer-objects to instantiate.
     * @param prodBuffer
     * @param messageBuffer
     */
    public Producer(Buffer<MessageProducer> prodBuffer,
                    Buffer<Message> messageBuffer){
        this.producerBuffer = prodBuffer;
        this.messageBuffer = messageBuffer;
    }

    /**
     * Starts the thread of this class by overriding the run()-method from its super class (Thread).
     * Assigns the variable "mp" of P1.MessageProducer to the return value of producerBuffer.get().
     * This method in turn returns the first value of the collection P1.Buffer<P1.MessageProducer> and deletes it
     * from the collection.
     * The condition for the first loop, mp.times() is equal to the number of iterations needed to read
     * all the images in the collection.
     * The conditions for the second loop, mp.size() is equal the total number of objects in the
     * collection.
     * The .put()-method from P1.Buffer reads the next object in Messages[] and tucks it in the P1.Buffer at the
     * last index.
     * A delay is inserted in the loop(s), the value of which is read from the second line of new.txt and
     * then stored in an instance variable from whence it was read.
     */
    @Override
    /*
     * Tips till nästa gång enligt Jenny: lägg till en start()-metod i P1.Producer så att klassen
     * som helhet inte extends Thread.
     */
    public void run() {
        while (true){
            try {
                MessageProducer mp = producerBuffer.get();
                for (int i = 0; i < mp.times(); i++) {
                    for (int j = 0; j < mp.size(); j++) {
                        messageBuffer.put(mp.nextMessage());
                        Thread.sleep(mp.delay());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
