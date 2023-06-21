package uy.edu.um.prog2.adt;

import org.junit.Before;
import org.junit.Test;
import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;

import static org.junit.Assert.*;


public class LinkedListImplTest {
    private LinkedListImpl<String> linkedList;
    private LinkedListImpl<Integer> list;

    @Before
    public void setUp() {
        linkedList = new LinkedListImpl<>();
        list = new LinkedListImpl<>();
    }

    @Test
    public void add() {
        linkedList.add("hola");
        linkedList.add("mundo");
        linkedList.add("!");
        try {
            assertEquals("!", linkedList.get(2));
        } catch (DatosIncorrectos e) {
            fail();
        }

    }

    @Test
    public void get() {
        linkedList.add("hola");
        linkedList.add("mundo");
        try {
            assertEquals("mundo", linkedList.get(1));
        } catch (DatosIncorrectos e) {
            fail();
        }
    }

    @Test
    public void contains() {
        linkedList.add("hola");
        assertTrue(linkedList.contains("hola"));
    }

    @Test
    public void remove() {
        list.add(1);
        list.add(2);
        list.add(3);

        // Remove value 2
        list.remove(2);
        assertEquals(2, list.size());
        assertTrue(list.contains(1));
        assertFalse(list.contains(2));
        assertTrue(list.contains(3));

        list.remove(1);
        assertEquals(1, list.size());
        assertFalse(list.contains(1));
        assertTrue(list.contains(3));

        // Remove value 3 (last element)
        list.remove(3);
        assertEquals(0, list.size());
        assertFalse(list.contains(3));
    }

    @Test
    public void size() {
        assertEquals(0, linkedList.size());
        linkedList.add("hola");
        assertEquals(1, linkedList.size());

    }


    @Test
    public void isEmpty() {
        assertTrue(linkedList.isEmpty());

        linkedList.add("hola");
        assertFalse(linkedList.isEmpty());

        linkedList.remove("hola");
        assertTrue(list.isEmpty());
    }


    @Test
    public void testToString() {
        linkedList.add("Hello");
        linkedList.add("World");
        linkedList.add("!");

        assertEquals("Hello -> World -> !", linkedList.toString());
    }

    @Test
    public void indexOf() {
        linkedList.add("Apple");
        linkedList.add("Banana");
        linkedList.add("Orange");
        linkedList.add("Mango");
        assertTrue(linkedList.contains("Banana"));
        System.out.println(linkedList);
        assertEquals(-1, linkedList.indexOf("Banana"));
        assertEquals(-1, linkedList.indexOf("Grapes"));
    }
}