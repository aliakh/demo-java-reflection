package demo.part2.field;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldsAccessibilityTest {

    @Test
    public void publicField() throws ReflectiveOperationException {
        Object object = new SomeClassWithFields();
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField("publicField");

        assertTrue(field.canAccess(object));

        assertEquals(0, field.getInt(object));
        field.setInt(object, 1);
        assertEquals(1, field.getInt(object));
    }

    @Test
    public void privateField() throws ReflectiveOperationException {
        Object object = new SomeClassWithFields();
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField("privateField");

        assertFalse(field.canAccess(object));
        field.setAccessible(true);
        assertTrue(field.canAccess(object));

        assertEquals(0, field.getInt(object));
        field.setInt(object, 1);
        assertEquals(1, field.getInt(object));
    }

    @Test
    public void publicStaticField() throws ReflectiveOperationException {
        Object object = new SomeClassWithFields();
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField("publicStaticField");

        assertTrue(field.canAccess(null));

        assertEquals(0, field.getInt(null));
        field.setInt(object, 1);
        assertEquals(1, field.getInt(null));
    }
}

