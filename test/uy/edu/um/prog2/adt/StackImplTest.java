package uy.edu.um.prog2.adt;

import org.junit.Before;
import org.junit.Test;
import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;
import uy.edu.um.prog2.adt.exceptions.EmptyStackException;

import static org.junit.Assert.*;


public class StackImplTest {
    private StackImpl<String> stack;
    private StackImpl<Integer> stackInt;


    @Before
    public void setUp() {
        stack = new StackImpl<>();
        stackInt = new StackImpl<>();
    }


    @Test
    public void push() {
        stack.push("hola");
        stack.push("mundo");
        stack.push("!");

        try {
            assertEquals("!", stack.get(2));
        } catch (DatosIncorrectos e) {
            fail();
        }
    }

    @Test
    public void pop() {
        stackInt.push(1);
        stackInt.push(2);
        stackInt.push(3);

        // Remove value 2

        try {
            assertEquals((Integer) 3, stackInt.pop());
        } catch (EmptyStackException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, stackInt.size());
        assertTrue(stackInt.contains(1));
        assertFalse(stackInt.contains(3));
        assertTrue(stackInt.contains(2));


        try {
            assertEquals((Integer) 2, stackInt.pop());
        } catch (EmptyStackException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, stackInt.size());
        assertFalse(stackInt.contains(2));
        assertFalse(stackInt.contains(3));
        assertTrue(stackInt.contains(1));

        // Remove value 3 (last element)
        try {
            assertEquals((Integer) 1, stackInt.pop());
        } catch (EmptyStackException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, stackInt.size());
        assertFalse(stackInt.contains(2));
        assertFalse(stackInt.contains(3));
        assertFalse(stackInt.contains(1));
    }

    @Test
    public void peek() {
        assertNull(stack.peek());

        // Caso 2: Lista con un elemento, se espera que el resultado sea el valor del elemento
        stackInt.push(5);

        //assertEquals(5, stack.peek());

        // Caso 3: Lista con varios elementos, se espera que el resultado sea el valor del último elemento
        stackInt.push(10);
        stackInt.push(15);
        assertEquals((Integer) 15, stackInt.peek());

        // Verificar que el tamaño de la lista no ha cambiado
        assertEquals( 3, stackInt.size());
    }

    @Test
    public void size() {
        assertEquals(0, stack.size());
        stack.push("hola");
        assertEquals(1, stack.size());
    }

    @Test
    public void get() {
        stack.push("hola");
        stack.push("mundo");
        try {
            assertEquals("mundo", stack.get(1));
        } catch (DatosIncorrectos e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void contains() {
        stack.push("hola");
        assertTrue(stack.contains("hola"));
    }
}