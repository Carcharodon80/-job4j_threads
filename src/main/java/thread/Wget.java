package thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.*;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("out.txt")) {
            byte[] array = new byte[speed];
            int bytesRead;
            Instant start = Instant.now();
            while ((bytesRead = in.read(array, 0, speed)) != -1) {
                out.write(array, 0, bytesRead);
                Instant end = Instant.now();
                long time = Duration.between(start, end).toMillis();
                System.out.println(time);
                if (time < 1000) {
                    System.out.println("Pause = " + (1000 - time));
                    Thread.sleep(1000 - time);

                }
                start = Instant.now();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        int speed = 1024;
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
