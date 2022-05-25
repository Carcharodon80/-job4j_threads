package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int k = 0; k < 5000; k++) {
                        count.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 5000; i++) {
                        count.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(10000, count.get());
    }
}