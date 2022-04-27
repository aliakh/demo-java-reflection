package demo.part1.nested;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperClass {
    public static class SuperPublicNestedClass {}
    protected static class SuperProtectedNestedClass {}
    static class SuperPackageNestedClass {}
    private static class SuperPrivateNestedClass {}
}

class SubClass extends SuperClass {
    public static class SubPublicNestedClass {}
    protected static class SubProtectedNestedClass {}
    static class SubPackageNestedClass {}
    private static class SubPrivateNestedClass {}
}

public class NestedClassesTest_getClasses_getDeclaredClasses extends ReflectionTest {

    @Test
    public void getClassesTest() {
        assertEquals(
            Set.of(
                "class demo.part1.nested.SuperClass$SuperPublicNestedClass",
                "class demo.part1.nested.SubClass$SubPublicNestedClass"
            ),
            toStringSet(SubClass.class.getClasses())
        );
    }

    @Test
    public void getDeclaredClassesTest() {
        assertEquals(
            Set.of(
                "class demo.part1.nested.SubClass$SubPublicNestedClass",
                "class demo.part1.nested.SubClass$SubProtectedNestedClass",
                "class demo.part1.nested.SubClass$SubPackageNestedClass",
                "class demo.part1.nested.SubClass$SubPrivateNestedClass"
            ),
            toStringSet(SubClass.class.getDeclaredClasses())
        );
    }
}
