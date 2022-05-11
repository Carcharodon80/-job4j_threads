package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.*;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String outFile;

    public Wget(String url, int speed, String outFile) {
        this.url = url;
        this.speed = speed;
        this.outFile = outFile;
    }

    /**
     * метод читает файл из сети и пишет его в outFile,
     * считаем кол-во прочитанных/записанных байт, если превышает speed,
     * и затраченное на это время меньше секунды - делаем задержку.
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(outFile)) {
            byte[] array = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            Instant start = Instant.now();
            while ((bytesRead = in.read(array, 0, 1024)) != -1) {
                out.write(array, 0, bytesRead);
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    long deltaTime = Duration.between(start, Instant.now()).toMillis();
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                    bytesWrited = 0;
                    start = Instant.now();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * метод проверяет кол-во аргументов
     */
    private static void isValidArgs(int argsNum) {
        if (argsNum != 3) {
            System.out.println("Проверьте параметры:");
            System.out.println("1 - URL скачиваемого файла");
            System.out.println("2 - максимальная скорость скачивания (байт/сек");
            System.out.println("3 - имя файла для записи");
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param args - 1 аргумент - URL скачиваемого файла,
     *             2 аргумент - максимальное кол-во байт, которое можно скачать за 1 секунду
     *             3 аргумент - имя выходного файла
     */
    public static void main(String[] args) throws InterruptedException {
        isValidArgs(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String outFile = args[2];
        Thread wget = new Thread(new Wget(url, speed, outFile));
        wget.start();
        wget.join();
    }
}
