package ru.job4j.thread;

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

    /**
     * метод читает файл из сети и пишет его в "out.txt"
     * размер буффера == скорость скачивания за секунду,
     * если буфер скачался/записался быстрее, чем за секунду - делаем задержку
     */
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
                if (time < 1000) {
                    Thread.sleep(1000 - time);
                }
                start = Instant.now();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param args - 1 аргумент - URL скачиваемого файла,
     *             2 аргумент - максимальное кол-во байт, которое можно скачать за 1 секунду
     */
    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
