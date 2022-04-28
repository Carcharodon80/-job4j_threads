package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> {
        });
        Thread second = new Thread(() -> {
        });
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + second.getState());
        System.out.println("Запуск нитей");
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("Минимум одна нить работает");
            }
        System.out.println("Нити закончили работу");
    }
}
