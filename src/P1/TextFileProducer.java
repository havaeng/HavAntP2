package P1;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;

/**
 * This class translates a provided file from raw data to usable information and creates a P1.Message[] from it.
 * @author Anthon Haväng
 * @version 1.0
 */
public class TextFileProducer implements MessageProducer, Serializable {

    private Message[] messages;
    private int delay = 0;
    private int size = 0;
    private int times = 0;
    private int currentIndex = -1;

    /**
     * @param filename
     * @throws FileNotFoundException
     *
     * Constructor for P1.TextFileProducer. To instantiate, a filename or source is required.
     * Next, three things are executed before the instance variables are assigned.
     * Firstly, an object of FileInputStream is instantiated. This class obtains input bytes from the provided
     * file or source.
     *
     * Secondly, an object of InputStreamReader is instantiated, which requires a FileInputSteam, so we
     * use the one we just created. This class translates the byte streams into characters based on
     * the chosen charset. In my case, no charset is specified and so the platform's default charset is used.
     *
     * Thirdly, an object of BufferedReader is instantiated. Wraps itself around the reader we just created
     * to simplify the process of reading the characters from the file (actually the characters in the reader-object).
     *
     * The instance variables times, delay and size are assigned to the three first rows in the file used. In this
     * case: 4, 200 and 10. Because the characters are String-objects, they need to be parsed to ints in order to
     * be assigned correctly.
     *
     * An array of P1.Message-objects with the size of times (instance variable) is created. The element of each index
     * is assigned with values from the read file.
     * To do this, Messages[] is iterated with a number of times equal to it's size.
     *
     */
    public TextFileProducer(String filename) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        try {
            this.times = Integer.parseInt(br.readLine());
            this.delay = Integer.parseInt(br.readLine());
            this.size = Integer.parseInt(br.readLine());
            this.messages = new Message[size];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = new Message(br.readLine(), new ImageIcon(br.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return returns the value of @param delay instance variable of this class.
     * delay is the amount of time each image is shown, measured in ms.
     * The value is read from new.txt in the constructor.
     */
    @Override
    public int delay() {
        return delay;
    }

    /**
     * @return returns the value of @param times instance variable of this class.
     * times is the amount of times the animation is iterated.
     * The value is read from new.txt in the constructor.
     */
    @Override
    public int times() {
        return times;
    }

    /**
     * @return 0 or messages.length.
     * If messages is equal to null, 0 is returned.
     * Else, messages.length is returned.
     */
    @Override
    public int size() {
        return (messages == null) ? 0 : messages.length;
    }

    /**
     * @return null or messages[currentIndex].
     * If size() is equal to 0, null is returned.
     * Else, messages[currentIndex] is returned.
     */
    @Override
    public Message nextMessage() {
        if(size() == 0)
            return null;
        currentIndex = (currentIndex+1) % messages.length;
        return messages[currentIndex];
    }
}
