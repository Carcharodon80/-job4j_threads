package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class  ParallelFindIndex<K> extends RecursiveTask<Integer> {
    private K[] array;
    private K element;
    private int from;
    private int to;

    public ParallelFindIndex() {
    }

    public ParallelFindIndex(K[] array, K element, int from, int to) {
        this.array = array;
        this.element = element;
        this.from = from;
        this.to = to;
    }

    /**
     * Разбивает поиск по массиву на много маленьких поисков
     * (сам массив не разбивается, задается только интервал поиска),
     * если интервал поиска <= 10 - происходит последовательный поиск в simpleFindIndex,
     * Каждый этот маленький поиск возвращает или -1 (элемент не найден), или индекс найденного элемента,
     * из этих значений берется максимальное (-1 - если элемент нигде не найден)
     */
    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return simpleFindIndex();
        }
        int mid = (from + to) / 2;
        ParallelFindIndex<K> leftFind = new ParallelFindIndex<>(array, element, from, mid);
        ParallelFindIndex<K> rightFind = new ParallelFindIndex<>(array, element, mid + 1, to);
        leftFind.fork();
        rightFind.fork();
        int left = leftFind.join();
        int right = rightFind.join();
        return Math.max(left, right);
    }

    /**
     * последовательный поиск на заданном участке массива
     * возвращает номер индекса или -1, если индекс не найден
     */
    private int simpleFindIndex() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(element)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Возвращает индекс найденого элемента
     * Если элемент не найден - возвращает -1
     */
    public int findIndex(K[] array, K element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndex<>(array, element, 0, array.length - 1));
    }
}
