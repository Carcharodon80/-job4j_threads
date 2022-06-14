package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * проверяет очередь, если заполнена - в спячку,
     * если нет - добавить в очередь и оповестить 1 другую нить
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == maxSize) {
            this.wait();
        }
        queue.offer(value);
        this.notify();
    }

    /**
     * проверяет очередь, если пустая - в спячку,
     * если нет - взять из очереди и оповестить 1 другую нить
     */
    public synchronized T poll() throws InterruptedException {
        T rsl;
        while (queue.size() == 0) {
            this.wait();
        }
        rsl = queue.poll();
        this.notify();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }
}