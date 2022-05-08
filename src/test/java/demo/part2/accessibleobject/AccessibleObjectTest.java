package demo.part2.accessibleobject;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SomeClass {
    private int privateField;
}

public class AccessibleObjectTest {

    @Test
    public void access_IllegalAccessException() {
        assertThrows(IllegalAccessException.class, () -> {
            Object object = new SomeClass();
            Class<?> clazz = object.getClass();
            Field field = clazz.getDeclaredField("privateField");

            assertFalse(field.canAccess(object));

            field.getInt(object);
        });
    }

    @Test
    public void access_success() throws ReflectiveOperationException {
        Object object = new SomeClass();
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField("privateField");

        assertFalse(field.canAccess(object));
        field.setAccessible(true);
        assertTrue(field.canAccess(object));

        assertEquals(0, field.getInt(object));
        field.setInt(object, 1);
        assertEquals(1, field.getInt(object));
    }
}
