package demo.part1.inheritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstanceTest {

    @Test
    public void isInstance() {
        assertTrue(Integer.valueOf(1) instanceof Integer);
        assertTrue(Integer.valueOf(1) instanceof Number);
        // assertTrue(1 instanceof int.class);

        assertTrue(Integer.class.isInstance(Integer.valueOf(1)));
        assertTrue(Number.class.isInstance(Integer.valueOf(1)));

        assertTrue(Integer[].class.isInstance(new Integer[]{}));
        assertTrue(Number[].class.isInstance(new Integer[]{}));

        assertFalse(int.class.isInstance(1)); // always false for primitives
    }

    @Test
    public void isAssignableFrom() {
        assertTrue(Integer.class.isAssignableFrom(Integer.class));
        assertTrue(Number.class.isAssignableFrom(Integer.class));

        assertTrue(Integer[].class.isAssignableFrom(Integer[].class));
        assertTrue(Number[].class.isAssignableFrom(Integer[].class));

        assertTrue(int.class.isAssignableFrom(int.class));
        assertFalse(long.class.isAssignableFrom(int.class));
    }
}
