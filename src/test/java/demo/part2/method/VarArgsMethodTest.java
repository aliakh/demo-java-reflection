package demo.part2.method;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class VarArgsMethodTest {

    private static class SomeClass {
        public void methodWithArrayParameter(int[] someParameter) {}
        public void methodWithVarArgsParameter(int... someParameter) {}
    }

    @Test
    public void varArgsMethod() throws ReflectiveOperationException {
        Class<?> clazz = SomeClass.class;

        Method methodWithArrayParameter = clazz.getDeclaredMethod("methodWithArrayParameter", int[].class);
        assertArrayEquals(new Class[]{int[].class}, methodWithArrayParameter.getParameterTypes());
        assertFalse(methodWithArrayParameter.isVarArgs());

        Method methodWithVarArgsParameter = clazz.getDeclaredMethod("methodWithVarArgsParameter", int[].class);
        assertArrayEquals(new Class[]{int[].class}, methodWithVarArgsParameter.getParameterTypes());
        assertTrue(methodWithVarArgsParameter.isVarArgs());
    }
}
