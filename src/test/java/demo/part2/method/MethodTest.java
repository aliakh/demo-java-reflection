package demo.part2.method;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

public class MethodTest {

    private static class SomeClass {
        public int someMethod(int someParameter) throws RuntimeException {
            return someParameter;
        }
    }

    @Test
    public void method() throws ReflectiveOperationException {
        Object object = new SomeClass();
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod("someMethod", int.class);

        // methods inherited from the Object class
        assertEquals("public int demo.part2.methods.MethodTest$SomeClass.someMethod(int) throws java.lang.RuntimeException", method.toString());

        // methods inherited from the AccessibleObject class
        assertTrue(method.canAccess(object));

        // methods inherited from the Member class
        assertEquals(SomeClass.class, method.getDeclaringClass());
        assertEquals("someMethod", method.getName());
        assertEquals("public", Modifier.toString(method.getModifiers()));
        assertFalse(method.isSynthetic());

        // methods inherited from the Executable class
        assertEquals(1, method.getParameterCount());
        assertArrayEquals(new Class[]{int.class}, method.getParameterTypes());
        assertArrayEquals(new Class[]{RuntimeException.class}, method.getExceptionTypes());
        assertFalse(method.isVarArgs());

        // methods declared in the Method class
        assertEquals(int.class, method.getReturnType());
        assertFalse(method.isBridge());
        assertFalse(method.isDefault());

        assertEquals(1, method.invoke(object, 1));
    }
}

