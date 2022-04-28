package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    System.out.println("Start loading.");
                    for (int i = 0; i <= 100; i++) {
                        System.out.print("\rLoading : " + i + "%");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println();
                    System.out.println("Finish loaded.");
                }
        );
        thread.start();

    }
}
