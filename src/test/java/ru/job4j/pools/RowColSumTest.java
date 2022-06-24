package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RowColSumTest {

    @Test
    public void whenSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        assertEquals(6, sums[0].getRowSum());
        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(24, sums[2].getRowSum());
        assertEquals(18, sums[2].getColSum());
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3, 4}, {4, 5, 6, 6}, {7, 8, 9, 9}, {1, 2, 3, 4}};
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        assertEquals(10, sums[0].getRowSum());
        assertEquals(13, sums[0].getColSum());
        assertEquals(33, sums[2].getRowSum());
        assertEquals(21, sums[2].getColSum());
        assertEquals(10, sums[3].getRowSum());
        assertEquals(23, sums[3].getColSum());
    }
}