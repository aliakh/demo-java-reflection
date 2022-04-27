package demo.part1.nested;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NestedClassesTest_getEnclosingConstructor_getEnclosingMethod {

    // enclosing constructor
    NestedClassesTest_getEnclosingConstructor_getEnclosingMethod() {
        class LocalClass {
            void test() {
                Class<?> clazz = this.getClass();
                Constructor<?> enclosingConstructor = clazz.getEnclosingConstructor();
                assertEquals("demo.part1.nested.NestedClassesTest_getEnclosingConstructor_getEnclosingMethod()",
                    enclosingConstructor.toString());
            }
        }
        new LocalClass().test();

        new Object() {
            void test() {
                Class<?> clazz = this.getClass();
                Constructor<?> enclosingConstructor = clazz.getEnclosingConstructor();
                assertEquals("demo.part1.nested.NestedClassesTest_getEnclosingConstructor_getEnclosingMethod()",
                    enclosingConstructor.toString());
            }
        }.test();
    }

    @Test
    // enclosing method
    public void getEnclosingMethodTest() {
        class LocalClass {
            void test() {
                Class<?> clazz = this.getClass();
                Method enclosingMethod = clazz.getEnclosingMethod();
                assertEquals("public void demo.part1.nested.NestedClassesTest_getEnclosingConstructor_getEnclosingMethod.getEnclosingMethodTest()",
                    enclosingMethod.toString());
            }
        }
        new LocalClass().test();

        new Object() {
            void test() {
                Class<?> clazz = this.getClass();
                Method enclosingMethod = clazz.getEnclosingMethod();
                assertEquals("public void demo.part1.nested.NestedClassesTest_getEnclosingConstructor_getEnclosingMethod.getEnclosingMethodTest()",
                    enclosingMethod.toString());
            }
        }.test();
    }
}
