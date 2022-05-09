package demo.part1.inheritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CastTest {

    @Test
    public void cast() {
        Number number = Integer.valueOf(1);
        Integer integer = Integer.class.cast(number); // (Integer) number
        assertEquals(1, integer);
    }

    @Test
    public void cast_null() {
        assertNull(String.class.cast(null));
    }

    @Test
    public void cast_ClassCastException() {
        assertThrows(ClassCastException.class, () -> {
            String.class.cast(1);
        });
    }

    @Test
    public void asSubclass() {
        Number number = Integer.valueOf(1);
        Class<? extends Number> numberClass = number.getClass();
        Class<? extends Integer> integerClass = number.getClass().asSubclass(Integer.class); // (Class<? extends Integer>) number
    }


    @Test
    public void asSubclass_null() {
        assertThrows(NullPointerException.class, () -> {
            Number number = Integer.valueOf(1);
            Class<? extends Integer> integerClass = number.getClass().asSubclass(null);
        });
    }

    @Test
    public void asSubclass_ClassCastException() {
        assertThrows(ClassCastException.class, () -> {
            Number number = Integer.valueOf(1);
            Class<? extends String> stringClass = number.getClass().asSubclass(String.class);
        });
    }
}
