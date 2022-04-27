package demo.part1.nested;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class NestedClassesTest_isMemberClass_isLocalClass_isAnonymousClass {

    static class StaticMemberClass {
        void test() {
            Class<?> clazz = this.getClass();
            assertTrue(clazz.isMemberClass());
            assertTrue(Modifier.isStatic(clazz.getModifiers()));
            assertFalse(clazz.isLocalClass());
            assertFalse(clazz.isAnonymousClass());
        }
    }

    @Test
    public void staticMemberClassTest() {
        new StaticMemberClass().test();
    }

    class NonStaticMemberClass {
        void test() {
            Class<?> clazz = this.getClass();
            assertTrue(clazz.isMemberClass());
            assertFalse(Modifier.isStatic(clazz.getModifiers()));
            assertFalse(clazz.isLocalClass());
            assertFalse(clazz.isAnonymousClass());
        }
    }

    @Test
    public void nonStaticMemberClassTest() {
        new NonStaticMemberClass().test();
    }

    @Test
    public void localClassTest() {
        class LocalClass {
            void test() {
                Class<?> clazz = this.getClass();
                assertFalse(clazz.isMemberClass());
                assertTrue(clazz.isLocalClass());
                assertFalse(clazz.isAnonymousClass());
            }
        }
        new LocalClass().test();
    }

    @Test
    public void anonymousClassTest() {
        new Object() {
            void test() {
                Class<?> clazz = this.getClass();
                assertFalse(clazz.isMemberClass());
                assertFalse(clazz.isLocalClass());
                assertTrue(clazz.isAnonymousClass());
            }
        }.test();
    }
}
