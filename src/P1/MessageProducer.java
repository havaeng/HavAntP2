package P1;

public interface MessageProducer {
	int delay();
	int times();
	int size();
	Message nextMessage();
	
	default void info() {
		System.out.println("times="+times()+", delay="+delay()+", size="+size()+"]");
	}
}
