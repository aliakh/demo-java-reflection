package demo.part2.discovery;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorsTest extends ReflectionTest {

    @Test
    public void getDeclaredConstructors() {
        Class<?> clazz = SubClass.class;

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        assertEquals(
            Set.of(
                "public demo.part2.members.SubClass()",
                "protected demo.part2.members.SubClass(int)",
                "demo.part2.members.SubClass(int,int)",
                "private demo.part2.members.SubClass(int,int,int)"
            ),
            toStringSet(constructors)
        );
    }

    @Test
    public void getDeclaredConstructor() throws ReflectiveOperationException {
        Class<?> clazz = SubClass.class;

        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(int.class, int.class, int.class);
        assertEquals("private demo.part2.members.SubClass(int,int,int)", declaredConstructor.toString());
    }

    @Test
    public void getConstructors() {
        Class<?> clazz = SubClass.class;

        Constructor<?>[] constructors = clazz.getConstructors();
        assertEquals(
            Set.of(
                "public demo.part2.members.SubClass()"
            ),
            toStringSet(constructors)
        );
    }

    @Test
    public void getConstructor() throws ReflectiveOperationException {
        Class<?> clazz = SubClass.class;

        Constructor<?> constructor = clazz.getConstructor();
        assertEquals("public demo.part2.members.SubClass()", constructor.toString());
    }
}
