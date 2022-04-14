package P1;

import java.util.ArrayList;

public class MessageManager {
    private Buffer<Message> messageBuffer;
    private ArrayList<Callback> callbacks = new ArrayList<>();

    public MessageManager (Buffer<Message> messageBuffer){
        this.messageBuffer = messageBuffer;
    }

    public void addToCallback(Callback callback){
        this.callbacks.add(callback);
    }

    public ArrayList<Callback> getCallbacks(){
        return callbacks;
    }

    public void start(){
        Running thread = new Running();
        thread.start();
    }

    class Running extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Message m = messageBuffer.get();
                    for (Callback callback : callbacks){
                        callback.setMessage(m);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


