package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(1, 1);
        assertTrue(cache.add(base1));
        assertFalse(cache.add(base2));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        assertTrue(cache.delete(base1));
        assertFalse(cache.delete(base1));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        assertTrue(cache.update(base1));
        assertFalse(cache.update(base2));
    }
}