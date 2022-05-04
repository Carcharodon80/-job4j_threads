package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
    }

    @Override
    public void run() {
        String[] array = new String[]{"|", "/", "-", "\\"};
        while (!Thread.currentThread().isInterrupted()) {
            for (String dash : array) {
                System.out.print("\rLoading... " + dash);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
