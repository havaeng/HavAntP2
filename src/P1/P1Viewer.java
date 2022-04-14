package P1;

public class P1Viewer extends Viewer implements Callback {
    private MessageManager mm;
    private int width;
    private int heigth;

    public P1Viewer(MessageManager mm, int width, int heigth){
        super(width, heigth);
        this.mm = mm;
        this.mm.addToCallback(this);
    }

    @Override
    public void setMessage(Message message){
        super.setMessage(message);
    }
}
