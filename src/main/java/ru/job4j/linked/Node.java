package ru.job4j.linked;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(final Node<T> next, final T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> setNext(Node<T> next) {
        return new Node<>(next, this.value);
    }

    public T getValue() {
        return value;
    }

    public Node<T> setValue(T value) {
        return new Node<>(this.next, value);
    }
}
