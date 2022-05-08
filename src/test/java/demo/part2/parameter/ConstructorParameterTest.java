package demo.part2.parameter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConstructorParameterTest {

    private static class SomeClass {
        private SomeClass(int someParameter) {}
    }

    @Test
    public void constructorParameter() throws ReflectiveOperationException {
        Class<?> clazz = SomeClass.class;
        Constructor<?> constructor = clazz.getDeclaredConstructor(int.class);

        Parameter[] parameters = constructor.getParameters();
        assertEquals(1, parameters.length);
        Parameter parameter = parameters[0];

        // methods inherited from the Object class
        assertEquals("int arg0", parameter.toString());

        // methods declared in the Parameter class
        assertEquals(constructor, parameter.getDeclaringExecutable());
        assertFalse(parameter.isNamePresent());
        assertEquals("arg0", parameter.getName());
        assertEquals("", Modifier.toString(parameter.getModifiers()));
        assertEquals(int.class, parameter.getType());
    }
}
