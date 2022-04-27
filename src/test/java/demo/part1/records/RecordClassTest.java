package demo.part1.records;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordClassTest extends ReflectionTest {

    @Test
    public void recordClass() {
        Class<?> clazz = SomeRecord.class;
        assertTrue(clazz.isRecord());

        assertEquals(
            Set.of("private final int demo.part1.records.SomeRecord.i"),
            toStringSet(clazz.getDeclaredFields())
        );

        assertEquals(
            Set.of(
                "public int demo.part1.records.SomeRecord.i()",
                "public final boolean demo.part1.records.SomeRecord.equals(java.lang.Object)",
                "public final int demo.part1.records.SomeRecord.hashCode()",
                "public final java.lang.String demo.part1.records.SomeRecord.toString()"
            ),
            toStringSet(clazz.getDeclaredMethods())
        );

        assertEquals(
            Set.of("demo.part1.records.SomeRecord(int)"),
            toStringSet(clazz.getDeclaredConstructors())
        );

        assertEquals("class demo.part1.records.SomeRecord", clazz.toString());
    }

    @Test
    public void recordComponentClass() {
        Class<?> clazz = SomeRecord.class;

        RecordComponent[] components = clazz.getRecordComponents();
        assertEquals(1, components.length);

        RecordComponent component = components[0];
        assertEquals(clazz, component.getDeclaringRecord());
        assertEquals("i", component.getName());
        assertEquals(int.class, component.getType());
        assertEquals("public int demo.part1.records.SomeRecord.i()", component.getAccessor().toString());
        assertEquals("int i", component.toString());
    }
}
