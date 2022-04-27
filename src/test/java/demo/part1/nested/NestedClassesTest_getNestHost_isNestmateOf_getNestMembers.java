package demo.part1.nested;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static demo.part1.nested.TopLevelClass.*;
import static demo.part1.nested.TopLevelClass.NestedClass2.*;

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
        assertEquals(TopLevelClass.class, NestedClass1.class.getNestHost());
        assertEquals(TopLevelClass.class, NestedClass2.class.getNestHost());
        assertEquals(TopLevelClass.class, NestedClass20.class.getNestHost());
    }

    @Test
    public void isNestmateOfTest() {
        assertTrue(TopLevelClass.class.isNestmateOf(TopLevelClass.class));
        assertTrue(TopLevelClass.class.isNestmateOf(NestedClass1.class));
        assertTrue(TopLevelClass.class.isNestmateOf(NestedClass2.class));
        assertTrue(TopLevelClass.class.isNestmateOf(NestedClass20.class));

        assertTrue(NestedClass1.class.isNestmateOf(TopLevelClass.class));
        assertTrue(NestedClass1.class.isNestmateOf(NestedClass1.class));
        assertTrue(NestedClass1.class.isNestmateOf(NestedClass2.class));
        assertTrue(NestedClass1.class.isNestmateOf(NestedClass20.class));

        assertTrue(NestedClass2.class.isNestmateOf(TopLevelClass.class));
        assertTrue(NestedClass2.class.isNestmateOf(NestedClass1.class));
        assertTrue(NestedClass2.class.isNestmateOf(NestedClass2.class));
        assertTrue(NestedClass2.class.isNestmateOf(NestedClass20.class));

        assertTrue(NestedClass20.class.isNestmateOf(TopLevelClass.class));
        assertTrue(NestedClass20.class.isNestmateOf(NestedClass1.class));
        assertTrue(NestedClass20.class.isNestmateOf(NestedClass2.class));
        assertTrue(NestedClass20.class.isNestmateOf(NestedClass20.class));
    }

    @Test
    public void getNestMembersTest() {
        Set<Class<?>> expectedNestMembers = Set.of(
            TopLevelClass.class,
            NestedClass1.class,
            NestedClass2.class,
            NestedClass20.class
        );

        assertEquals(expectedNestMembers, toSet(TopLevelClass.class.getNestMembers()));
        assertEquals(expectedNestMembers, toSet(NestedClass1.class.getNestMembers()));
        assertEquals(expectedNestMembers, toSet(NestedClass2.class.getNestMembers()));
        assertEquals(expectedNestMembers, toSet(NestedClass20.class.getNestMembers()));
    }
}
