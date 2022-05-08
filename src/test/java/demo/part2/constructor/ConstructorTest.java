package demo.part2.constructor;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTest {

    private static class SomeClass {
        public SomeClass(int someParameter) throws RuntimeException {}
    }

    @Test
    public void constructor() throws ReflectiveOperationException {
        Class<SomeClass> clazz = SomeClass.class;
        Constructor<SomeClass> constructor = clazz.getDeclaredConstructor(int.class);

        // methods inherited from the Object class
        assertEquals("public demo.part2.constructors.ConstructorTest$SomeClass(int) throws java.lang.RuntimeException", constructor.toString());

        // methods inherited from the AccessibleObject class
        assertTrue(constructor.canAccess(null));

        // methods inherited from the Member class
        assertEquals(SomeClass.class, constructor.getDeclaringClass());
        assertEquals("demo.part2.constructors.ConstructorTest$SomeClass", constructor.getName());
        assertEquals("public", Modifier.toString(constructor.getModifiers()));
        assertFalse(constructor.isSynthetic());

        // methods inherited from the Executable class
        assertEquals(1, constructor.getParameterCount());
        assertArrayEquals(new Class[]{int.class}, constructor.getParameterTypes());
        assertArrayEquals(new Class[]{RuntimeException.class}, constructor.getExceptionTypes());
        assertFalse(constructor.isVarArgs());

        // methods declared in the Constructor class
        SomeClass object = constructor.newInstance(1);
        assertNotNull(object);
    }
}
