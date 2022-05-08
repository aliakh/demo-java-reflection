package demo.part2.parameter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

public class MethodParameterTest {

    private static class SomeClass {
        private void someMethod(int someParameter) {}
    }

    @Test
    public void methodParameter() throws ReflectiveOperationException {
        Class<?> clazz = SomeClass.class;
        Method method = clazz.getDeclaredMethod("someMethod", int.class);

        Parameter[] parameters = method.getParameters();
        assertEquals(1, parameters.length);
        Parameter parameter = parameters[0];

        // methods inherited from the Object class
        assertEquals("int arg0", parameter.toString());

        // methods declared in the Parameter class
        assertEquals(method, parameter.getDeclaringExecutable());
        assertFalse(parameter.isNamePresent());
        assertEquals("arg0", parameter.getName());
        assertEquals("", Modifier.toString(parameter.getModifiers()));
        assertEquals(int.class, parameter.getType());
    }
}
