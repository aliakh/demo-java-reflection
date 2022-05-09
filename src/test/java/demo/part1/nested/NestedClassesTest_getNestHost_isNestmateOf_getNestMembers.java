package demo.part1.nested;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopLevelClass {
    static class NestedClass1 {}
    static class NestedClass2 {
        static class NestedClass20 {}
    }
}

public class NestedClassesTest_getNestHost_isNestmateOf_getNestMembers extends ReflectionTest {

    @Test
    public void getNestHostTest() {
        assertEquals(TopLevelClass.class, TopLevelClass.class.getNestHost());
        assertEquals(TopLevelClass.class, TopLevelClass.NestedClass1.class.getNestHost());
        assertEquals(TopLevelClass.class, TopLevelClass.NestedClass2.class.getNestHost());
        assertEquals(TopLevelClass.class, TopLevelClass.NestedClass2.NestedClass20.class.getNestHost());
    }

    @Test
    public void isNestmateOfTest() {
        assertTrue(TopLevelClass.class.isNestmateOf(TopLevelClass.class));
        assertTrue(TopLevelClass.class.isNestmateOf(TopLevelClass.NestedClass1.class));
        assertTrue(TopLevelClass.class.isNestmateOf(TopLevelClass.NestedClass2.class));
        assertTrue(TopLevelClass.class.isNestmateOf(TopLevelClass.NestedClass2.NestedClass20.class));

        assertTrue(TopLevelClass.NestedClass1.class.isNestmateOf(TopLevelClass.class));
        assertTrue(TopLevelClass.NestedClass1.class.isNestmateOf(TopLevelClass.NestedClass1.class));
        assertTrue(TopLevelClass.NestedClass1.class.isNestmateOf(TopLevelClass.NestedClass2.class));
        assertTrue(TopLevelClass.NestedClass1.class.isNestmateOf(TopLevelClass.NestedClass2.NestedClass20.class));

        assertTrue(TopLevelClass.NestedClass2.class.isNestmateOf(TopLevelClass.class));
        assertTrue(TopLevelClass.NestedClass2.class.isNestmateOf(TopLevelClass.NestedClass1.class));
        assertTrue(TopLevelClass.NestedClass2.class.isNestmateOf(TopLevelClass.NestedClass2.class));
        assertTrue(TopLevelClass.NestedClass2.class.isNestmateOf(TopLevelClass.NestedClass2.NestedClass20.class));

        assertTrue(TopLevelClass.NestedClass2.NestedClass20.class.isNestmateOf(TopLevelClass.class));
        assertTrue(TopLevelClass.NestedClass2.NestedClass20.class.isNestmateOf(TopLevelClass.NestedClass1.class));
        assertTrue(TopLevelClass.NestedClass2.NestedClass20.class.isNestmateOf(TopLevelClass.NestedClass2.class));
        assertTrue(TopLevelClass.NestedClass2.NestedClass20.class.isNestmateOf(TopLevelClass.NestedClass2.NestedClass20.class));
    }

    @Test
    public void getNestMembersTest() {
        Set<Class<?>> expectedNestMembers = Set.of(
            TopLevelClass.class,
            TopLevelClass.NestedClass1.class,
            TopLevelClass.NestedClass2.class,
            TopLevelClass.NestedClass2.NestedClass20.class
        );

        assertEquals(expectedNestMembers, toSet(TopLevelClass.class.getNestMembers()));
        assertEquals(expectedNestMembers, toSet(TopLevelClass.NestedClass1.class.getNestMembers()));
        assertEquals(expectedNestMembers, toSet(TopLevelClass.NestedClass2.class.getNestMembers()));
        assertEquals(expectedNestMembers, toSet(TopLevelClass.NestedClass2.NestedClass20.class.getNestMembers()));
    }
}
