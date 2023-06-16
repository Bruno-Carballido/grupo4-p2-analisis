package uy.edu.um.prog2.adt;

import org.junit.Before;
import org.junit.Test;
import uy.edu.um.prog2.adt.exceptions.EmptyHeapException;

import static org.junit.Assert.*;

public class HeapImplTest {
    private Heap<Integer, String> heap;

    @Before
    public void setUp() {
        heap = new HeapImpl<>();
    }

    @Test
    public void testInsertAndSize() {
        assertEquals(0, heap.size());

        heap.insert(51, "Apio");
        heap.insert(98, "Cebolla");
        heap.insert(66, "Tomate");

        assertEquals(3, heap.size());
    }

    @Test
    public void testDeleteAndMax() {
        heap.insert(51, "Apio");
        heap.insert(98, "Cebolla");
        heap.insert(66, "Tomate");

        try{
            heap.delete();
        }catch (EmptyHeapException e){
            fail();
        }

        assertEquals(2, heap.size());
        assertEquals("Tomate", heap.getMaxValue());
    }

    @Test()
    public void testDeleteEmptyHeap() {
        try{
            heap.delete();
            fail();
        }catch (EmptyHeapException e){
            assertTrue(true);
        }
    }

    @Test
    public void testGetMaxValue() {
        heap.insert(51, "Apio");
        heap.insert(98, "Cebolla");
        heap.insert(66, "Tomate");

        assertEquals("Cebolla", heap.getMaxValue());
    }
}
