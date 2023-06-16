package uy.edu.um.prog2.adt;

import org.junit.Before;
import org.junit.Test;
import uy.edu.um.prog2.adt.exceptions.EmptyTreeException;

import static org.junit.Assert.*;

public class BinarySearchTreeImplTest {

    private BinarySearchTree<Integer, String> tree;

    @Before
    public void setUp() {
        tree = new BinarySearchTreeImpl<>();
    }

    @Test
    public void testInsertAndFind() {
        tree.insert(4, "Papas");
        tree.insert(35, "Boniatos");
        tree.insert(23, "Zanahorias");
        tree.insert(45, "Perejil");

        assertTrue(tree.find(4));
        assertTrue(tree.find(35));
        assertTrue(tree.find(23));
        assertTrue(tree.find(45));
        assertFalse(tree.find(1000));
    }

    @Test
    public void testDelete() {
        tree.insert(4, "Papas");
        tree.insert(35, "Boniatos");
        tree.insert(23, "Zanahorias");
        tree.insert(45, "Perejil");

        assertTrue(tree.find(4));
        assertTrue(tree.find(35));
        assertTrue(tree.find(23));
        assertTrue(tree.find(45));

        try {
            tree.delete(4);
            assertFalse(tree.find(4));

            tree.delete(35);
            assertFalse(tree.find(35));

            tree.delete(23);
            assertFalse(tree.find(23));

            tree.delete(45);
            assertFalse(tree.find(45));
        } catch (EmptyTreeException e) {
            fail();
        }
    }

    @Test
    public void testDeleteEmpty() {
        try {
            tree.delete(77);
            fail();
        } catch (EmptyTreeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindMin() {
        tree.insert(4, "Papas");
        tree.insert(35, "Boniatos");
        tree.insert(23, "Zanahorias");
        tree.insert(45, "Perejil");

        assertEquals("Papas", tree.findMin());
    }

    @Test
    public void testFindMax() {
        tree.insert(4, "Papas");
        tree.insert(35, "Boniatos");
        tree.insert(23, "Zanahorias");
        tree.insert(45, "Perejil");

        assertEquals("Perejil", tree.findMax());
    }
}
