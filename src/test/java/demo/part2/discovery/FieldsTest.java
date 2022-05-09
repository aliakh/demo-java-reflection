package demo.part2.discovery;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldsTest extends ReflectionTest {

    @Test
    public void getDeclaredFields() {
        Class<?> clazz = SubClass.class;

        Field[] declaredFields = clazz.getDeclaredFields();
        assertEquals(
            Set.of(
                "public int demo.part2.members.SubClass.subPublicField",
                "protected int demo.part2.members.SubClass.subProtectedField",
                "int demo.part2.members.SubClass.subDefaultField",
                "private int demo.part2.members.SubClass.subPrivateField"
            ),
            toStringSet(declaredFields)
        );
    }

    @Test
    public void getDeclaredField() throws ReflectiveOperationException {
        Class<?> clazz = SubClass.class;

        Field declaredField = clazz.getDeclaredField("subPrivateField");
        assertEquals("private int demo.part2.members.SubClass.subPrivateField", declaredField.toString());
    }

    @Test
    public void getFields() {
        Class<?> clazz = SubClass.class;

        Field[] fields = clazz.getFields();
        assertEquals(
            Set.of(
                "public int demo.part2.members.SuperClass.superPublicField",
                "public int demo.part2.members.SubClass.subPublicField"
            ),
            toStringSet(fields)
        );
    }

    @Test
    public void getField() throws ReflectiveOperationException {
        Class<?> clazz = SubClass.class;

        Field field = clazz.getField("superPublicField");
        assertEquals("public int demo.part2.members.SuperClass.superPublicField", field.toString());
    }
}
