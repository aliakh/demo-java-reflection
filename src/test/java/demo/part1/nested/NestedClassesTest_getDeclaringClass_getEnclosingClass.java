package demo.part1.nested;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NestedClassesTest_getDeclaringClass_getEnclosingClass {

    static class StaticMemberClass {
        void test() {
            Class<?> clazz = this.getClass();
            assertEquals(NestedClassesTest_getDeclaringClass_getEnclosingClass.class, clazz.getDeclaringClass());
            assertEquals(NestedClassesTest_getDeclaringClass_getEnclosingClass.class, clazz.getEnclosingClass());
        }
    }

    @Test
    public void testStaticMemberClass() {
        new StaticMemberClass().test();
    }

    class NonStaticMemberClass {
        void test() {
            Class<?> clazz = this.getClass();
            assertEquals(NestedClassesTest_getDeclaringClass_getEnclosingClass.class, clazz.getDeclaringClass());
            assertEquals(NestedClassesTest_getDeclaringClass_getEnclosingClass.class, clazz.getEnclosingClass());
        }
    }

    @Test
    public void testNonStaticMemberClass() {
        new NonStaticMemberClass().test();
    }

    @Test
    public void testLocalClass() {
        class LocalClass {
            void test() {
                Class<?> clazz = this.getClass();
                assertNull(clazz.getDeclaringClass());
                assertEquals(NestedClassesTest_getDeclaringClass_getEnclosingClass.class, clazz.getEnclosingClass());
            }
        }
        new LocalClass().test();
    }

    @Test
    public void testAnonymousClass() {
        new Object() {
            void test() {
                Class<?> clazz = this.getClass();
                assertNull(clazz.getDeclaringClass());
                assertEquals(NestedClassesTest_getDeclaringClass_getEnclosingClass.class, clazz.getEnclosingClass());
            }
        }.test();
    }
}
