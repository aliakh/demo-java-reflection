package demo.part2.discovery;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassesTest extends ReflectionTest {

    @Test
    public void getDeclaredClasses() {
        Class<?> clazz = SubClass.class;

        Class<?>[] declaredClasses = clazz.getDeclaredClasses();
        assertEquals(
            Set.of(
                "class demo.part2.members.SubClass$SubPublicNestedClass",
                "class demo.part2.members.SubClass$SubProtectedNestedClass",
                "class demo.part2.members.SubClass$SubDefaultNestedClass",
                "class demo.part2.members.SubClass$SubPrivateNestedClass"
            ),
            toStringSet(declaredClasses)
        );
    }

    @Test
    public void getClasses() {
        Class<?> clazz = SubClass.class;

        Class<?>[] classes = clazz.getClasses();
        assertEquals(
            Set.of(
                "class demo.part2.members.SuperClass$SuperPublicNestedClass",
                "class demo.part2.members.SubClass$SubPublicNestedClass"
            ),
            toStringSet(classes)
        );
    }
}
