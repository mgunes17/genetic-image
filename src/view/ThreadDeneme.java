package view;

/**
 * Created by ercan on 26.12.2016.
 */
public class ThreadDeneme extends Thread {
    @Override
    public void run() {
        for(;;) {
            System.out.println("MUSTAFA");
        }
    }
}
