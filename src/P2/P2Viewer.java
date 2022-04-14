package P2;

import P1.Message;
import P1.Viewer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/*
Klar
 */

public class P2Viewer extends Viewer implements PropertyChangeListener {
    private MessageClient mc;

    public P2Viewer(MessageClient mc, int width, int height) {
        super(width, height);
        this.mc = mc;
        mc.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("m") && evt.getNewValue() instanceof Message)
        super.setMessage((Message) evt.getNewValue());
    }
}
