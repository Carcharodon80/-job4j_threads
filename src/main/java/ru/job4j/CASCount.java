package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer current;
        int temp;
        do {
            current = count.get();
            temp = current + 1;
        } while (!count.compareAndSet(current, temp));
    }

    public int get() {
        return count.get();
    }
}
