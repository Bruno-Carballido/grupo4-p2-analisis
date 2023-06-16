package uy.edu.um.prog2.adt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;
import uy.edu.um.prog2.adt.exceptions.EmptyQueueException;

import static org.junit.Assert.*;


public class QueueImplTest {
    private QueueImpl<String> queueString;
    private QueueImpl<Integer> queueInt;


    @Before
    public void setUp() {
        queueString = new QueueImpl<>();
        queueInt = new QueueImpl<>();
    }

    @Test
    public void enqueue() {
        queueString.enqueue("hola");
        queueString.enqueue("mundo");
        queueString.enqueue("!");
        try {
            assertEquals("hola", queueString.get(2));
        } catch (DatosIncorrectos e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void dequeue() {
        queueInt.enqueue(1);
        queueInt.enqueue(2);
        queueInt.enqueue(3);

        // Remove value 2

        try {
            assertEquals((Integer) 1, queueInt.dequeue());
        } catch (EmptyQueueException e) {
            fail();
        }
        Assert.assertEquals(2, queueInt.size());
        Assert.assertTrue(queueInt.contains(3));
        Assert.assertFalse(queueInt.contains(1));
        Assert.assertTrue(queueInt.contains(2));


        try {
            assertEquals((Integer) 2, queueInt.dequeue());
        } catch (EmptyQueueException e) {
            fail();
        }
        Assert.assertEquals(1, queueInt.size());
        Assert.assertFalse(queueInt.contains(2));
        Assert.assertFalse(queueInt.contains(1));
        Assert.assertTrue(queueInt.contains(3));


        // Remove value 3 (last element)
        try {
            assertEquals((Integer) 3, queueInt.dequeue());
        } catch (EmptyQueueException e) {
            fail();
        }
        Assert.assertEquals(0, queueInt.size());
        Assert.assertFalse(queueInt.contains(2));
        Assert.assertFalse(queueInt.contains(3));
        Assert.assertFalse(queueInt.contains(1));


    }

    @Test
    public void contains() {
        queueString.enqueue("hola");
        assertTrue(queueString.contains("hola"));
    }

    @Test
    public void size() {
        assertEquals(0, queueString.size());
        queueString.enqueue("hola");
        assertEquals(1, queueString.size());
    }

    @Test
    public void get() {
        queueString.enqueue("hola");
        queueString.enqueue("mundo");
        try {
            assertEquals("mundo", queueString.get(0));
        } catch (DatosIncorrectos e) {
            throw new RuntimeException(e);
        }
    }
}