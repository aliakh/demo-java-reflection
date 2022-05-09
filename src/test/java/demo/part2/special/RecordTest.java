package demo.part2.special;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RecordTest extends ReflectionTest {

    @Test
    public void testClass() {
        Class<?> clazz = SomeRecord.class;
        assertTrue(Modifier.isFinal(clazz.getModifiers()));
        assertEquals(Record.class, clazz.getSuperclass());
    }

    @Test
    public void testFields() {
        Class<?> clazz = SomeRecord.class;

        Field[] fields = clazz.getDeclaredFields();
        assertEquals(1, fields.length);

        Field field = fields[0];
        assertEquals("private final int demo.part2.synthetic.SomeRecord.i", toString(field));

        assertEquals("i", field.getName());
        assertEquals("private final", Modifier.toString(field.getModifiers()));
        assertFalse(field.isSynthetic());
        assertEquals(int.class, field.getType());
    }

    @Test
    public void testMethods() {
        Class<?> clazz = SomeRecord.class;

        Method[] methods = clazz.getDeclaredMethods();
        assertEquals(
            Set.of(
                "public int demo.part2.synthetic.SomeRecord.i()",
                "public final boolean demo.part2.synthetic.SomeRecord.equals(java.lang.Object)",
                "public final int demo.part2.synthetic.SomeRecord.hashCode()",
                "public final java.lang.String demo.part2.synthetic.SomeRecord.toString()"
            ),
            toStringSet(methods)
        );
    }

    @Test
    public void testConstructors() {
        Class<?> clazz = SomeRecord.class;

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        assertEquals(1, constructors.length);

        Constructor<?> constructor = constructors[0];
        assertEquals("demo.part2.synthetic.SomeRecord(int)", toString(constructor));

        assertEquals("demo.part2.synthetic.SomeRecord", constructor.getName());
        assertEquals("", Modifier.toString(constructor.getModifiers()));
        assertFalse(constructor.isSynthetic());

        Parameter[] parameters = constructor.getParameters();
        assertEquals(1, parameters.length);
        Parameter parameter = parameters[0];
        assertEquals("i", parameter.getName());
        assertEquals("", Modifier.toString(parameter.getModifiers()));
        assertEquals(int.class, parameter.getType());
        assertFalse(parameter.isSynthetic());
        assertFalse(parameter.isImplicit());
    }
}
