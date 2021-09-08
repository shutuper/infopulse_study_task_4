package com.company.arrayQueue;

public class Queue<T> {

    public int last;
    private final int capacity;
    private final T[] queue;

    public Queue(int c) {
        capacity = c;
        queue = (T[]) new Object[capacity];
    }

    public void enqueue(T value) {
        if (capacity <= last) {
            throw new IndexOutOfBoundsException("Queue is full!");
        } else {
            queue[last] = value;
            last++;
        }
    }

    public T dequeue() {
        T result;
        if (last <= 0) {
            throw new IndexOutOfBoundsException("Queue is empty!");
        } else {
            result = queue[0];
            System.arraycopy(queue, 1, queue, 0, last - 1);
            queue[last - 1] = null;
            last--;
        }
        return result;
    }

    public void showQueue() {
        for (T t : queue) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public boolean contains(T value) {
        for (T t : queue) {
            if (t.equals(value)) return true;
        }
        return false;
    }

    public T getFirst() {
        if (last <= 0) {
            throw new IndexOutOfBoundsException("Queue is empty!");
        }
        return queue[0];
    }

    public T getLast() {
        if (last <= 0) {
            throw new IndexOutOfBoundsException("Queue is empty!");
        }
        return queue[last - 1];
    }

}
