package com.company.arrayQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class QueueTest {


    @Test
    void enqueueAndDeque() {
        Queue<Integer> queue = new Queue<>(1000);
        System.out.println("Empty queue: ");
        queue.showQueue();

        IntStream.range(0, 1000).forEach(queue::enqueue);
        Assertions.assertEquals(999, queue.getLast());
        System.out.println("1000 elements added to queue: ");
        queue.showQueue();

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        System.out.println("3 elements excluded from queue: ");
        queue.showQueue();
        Assertions.assertEquals(3, queue.getFirst());

        for (int i = 0; i < 1000 - 4; i++) {
            queue.dequeue();
        }
        System.out.println("All except last excluded from queue: ");
        queue.showQueue();
        Assertions.assertEquals(999, queue.getFirst());
    }

    @Test
    void queueContains() {
        Queue<Integer> queue = new Queue<>(1001);
        IntStream.range(0, 1000).forEach(queue::enqueue);
        queue.enqueue(14122);
        Assertions.assertTrue(queue.contains(14122));
        Assertions.assertFalse(queue.contains(4124));
    }
}