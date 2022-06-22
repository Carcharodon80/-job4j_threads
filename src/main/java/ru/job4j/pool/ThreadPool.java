package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;


public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks =
            new SimpleBlockingQueue<>(Runtime.getRuntime().availableProcessors());

    /**
     * создает список нитей (размер - кол-во ядер),
     * каждая нить постоянно пытается получить и запустить задачу из tasks (пока не будет прервана)
     */
    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        for (Thread t : threads) {
            t.start();
        }
    }

    /**
     * добавляет задачи в tasks (а оттуда их постоянно забирают в работу нити из threads)
     */
    public synchronized void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    /**
     * прерывает все нити (несмотря на наличие задач в очереди)
     */
    public void shutdown() {
            for (Thread thread : threads) {
                thread.interrupt();
            }
    }

    /**
     * пример работы (для себя)
     * ! - pool.shutdown - экстренная остановка (к этому времени некоторые задачи еще в очереди)
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            pool.work(new Thread(
                    () -> System.out.println(Thread.currentThread().getName() + " Task " + finalI)
            ));
        }
        pool.shutdown();
    }
}
