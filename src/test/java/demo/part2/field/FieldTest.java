package demo.part2.field;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private static class SomeClass {
        public int someField;
    }

    @Test
    public void field() throws ReflectiveOperationException {
        Object object = new SomeClass();
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField("someField");

        // methods inherited from the Object class
        assertEquals("public int demo.part2.fields.FieldTest$SomeClass.someField", field.toString());

        // methods inherited from the AccessibleObject class
        assertTrue(field.canAccess(object));

        // methods inherited from the Member class
        assertEquals(SomeClass.class, field.getDeclaringClass());
        assertEquals("someField", field.getName());
        assertEquals("public", Modifier.toString(field.getModifiers()));
        assertFalse(field.isSynthetic());

        // methods declared in the Field class
        assertEquals(int.class, field.getType());

        assertEquals(0, field.getInt(object));
        field.set(object, 1);
        assertEquals(1, field.getInt(object));
    }
}
