package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    /**
     * увеличивает счетчик и уведомляет об этом все нити
     */
    public void count() {
        synchronized (monitor) {
            count++;
            System.out.println("Count = " + count);
            notifyAll();
        }
    }

    /**
     * если счетчик меньше total - нить продолжает спать
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep. Count = " + count);
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Запускаем нити, и они начинают ждать (метод wait())
     * В главной нити в цикле увеличиваем счетчик, счетчик при увеличении будит нити, те смотрят, что еще рано
     * и спят дальше, или просыпаются и начинают что-то делать
     */
    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(7);
        Thread first = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " wake up and do something");
                }
        );
        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " wake up and do something");
                }
        );
        first.start();
        second.start();
        for (int i = 0; i < 10; i++) {
            countBarrier.count();
            Thread.sleep(200);
        }
    }
}
