package uy.edu.um.prog2.adt;

import org.junit.Before;
import org.junit.Test;
import uy.edu.um.prog2.adt.exceptions.EmptyHashException;

import static org.junit.Assert.*;

public class HashImplTest {
    private Hash<Integer, String> hash;

    @Before
    public void setUp() {
        hash = new HashImpl<>();
    }

    @Test
    public void testPutAndGet() {
        hash.put(10, "Ajo");
        hash.put(18, "Queso");
        hash.put(67, "Pan");
        try {
            assertEquals("Ajo", hash.get(10));
            assertEquals("Queso", hash.get(18));
            assertEquals("Pan", hash.get(67));
        } catch (EmptyHashException e) {
            fail();
        }
    }

    @Test
    public void testRemove() {
        hash.put(10, "Ajo");
        hash.put(18, "Queso");
        hash.put(67, "Pan");

        hash.remove(18);

        assertEquals(2, hash.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, hash.size());

        hash.put(10, "Ajo");
        hash.put(18, "Queso");

        assertEquals(2, hash.size());
    }

    @Test
    public void testGetEmpty() {
        try {
            hash.get(3);
        } catch (EmptyHashException e) {
            assertTrue(true);
        }

    }
}
