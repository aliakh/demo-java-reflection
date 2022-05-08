package demo.part2.method;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodsAccessibilityTest {

    @Test
    public void testPublicMethod() throws ReflectiveOperationException {
        Object object = new SomeClassWithMethods();
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod("publicMethod", int.class);

        assertTrue(method.canAccess(object));

        assertEquals(1, method.invoke(object, 1));
    }

    @Test
    public void testPrivateMethod() throws ReflectiveOperationException {
        Object object = new SomeClassWithMethods();
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod("privateMethod", int.class);

        assertFalse(method.canAccess(object));
        method.setAccessible(true);
        assertTrue(method.canAccess(object));

        assertTrue(method.canAccess(object));
        assertEquals(1, method.invoke(object, 1));
    }

    @Test
    public void testPublicStaticMethod() throws ReflectiveOperationException {
        Object object = new SomeClassWithMethods();
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod("publicStaticMethod", int.class);

        assertTrue(method.canAccess(null));

        assertEquals(1, method.invoke(null, 1));
    }
}

