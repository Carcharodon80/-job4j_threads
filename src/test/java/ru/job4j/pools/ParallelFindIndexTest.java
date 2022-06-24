package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelFindIndexTest {

    @Test
    public void whenLess10() {
        String[] array = new String[] {"AAA", "BBB", "CCC", "DDD", "EEE"};
        ParallelFindIndex<String> task = new ParallelFindIndex<>();
        assertEquals(3, task.findIndex(array, "DDD"));
    }

    @Test
    public void whenMore10() {
        Integer[] array = new Integer[200];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelFindIndex<Integer> task = new ParallelFindIndex<>();
        assertEquals(150, task.findIndex(array, 150));
    }

    @Test
    public void whenNotFind() {
        String[] array = new String[500];
        for (int i = 0; i < array.length; i++) {
            array[i] = "No " + i;
        }
        ParallelFindIndex<String> task = new ParallelFindIndex<>();
        assertEquals(-1, task.findIndex(array, "150"));
    }

    @Test
    public void whenFindTwoElementsGetMaxIndex() {
        String[] array = new String[500];
        for (int i = 0; i < array.length; i++) {
            array[i] = "No " + i;
        }
        array[400] = "No 300";
        ParallelFindIndex<String> task = new ParallelFindIndex<>();
        assertEquals(400, task.findIndex(array, "No 300"));
    }

}