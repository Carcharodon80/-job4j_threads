package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    /**
     * последовательное вычисление сумм чисел по каждой горизонтали и вертикали в квадратной матрице
     * @param matrix - квадратная матрица чисел
     * @return - массив Sums[], где sums[i] содержит сумму чисел по i-горизонтали и i-вертикали
     */
    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        Sums iSum;
        int iRowSum;
        int iColSum;
        for (int i = 0; i < size; i++) {
            iSum = new Sums();
            iRowSum = 0;
            iColSum = 0;
            for (int j = 0; j < size; j++) {
                iRowSum += matrix[i][j];
                iColSum += matrix[j][i];
            }
            iSum.setRowSum(iRowSum);
            iSum.setColSum(iColSum);
            sums[i] = iSum;
        }
        return sums;
    }

    /**
     * Асинхронное вычисление сумм чисел по каждой горизонтали и вертикали в квадратной матрице.
     * В цикле создаются несколько CompletableFuture (CF), которые параллельно вычисляют суммы по i-горизонтали и верт.
     * Сразу получить Sums из CF с помощью get() вроде нельзя (пропадет параллельность,
     * т.к. get() блокирующий), поэтому сначала сложил все CF в List,
     * а потом уже достал из них Sums и положил в Sums[].
     * Вроде можно было добавить прямо в цикле completableFuture.thenAccept(result -> sums[i] = result),
     * работает параллельно, но основной поток заканчивал работу раньше, пришлось добавлять Thread.sleep(),
     * что наверное не очень правильно.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        List<CompletableFuture<Sums>> tempSums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CompletableFuture<Sums> completableFuture = getSums(matrix, i);
            tempSums.add(completableFuture);
        }
        for (int i = 0; i < size; i++) {
            sums[i] = tempSums.get(i).get();
        }
        return sums;
    }

    /**
     * вычисляет сумму чисел по i-горизонтали и вертикали, кладет их в Sums, а Sums в CompletableFuture
     */
    private static CompletableFuture<Sums> getSums(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Sums iSums = new Sums();
                    int tempRowSum = 0;
                    int tempColSum = 0;
                    for (int j = 0; j < matrix.length; j++) {
                        tempRowSum += matrix[i][j];
                        tempColSum += matrix[j][i];
                    }
                    iSums.setRowSum(tempRowSum);
                    iSums.setColSum(tempColSum);
                    return iSums;
                }
        );
    }

    /**
     * содержит 2 числа - сумма чисел по горизонтали и вертикали
     */
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }
}
