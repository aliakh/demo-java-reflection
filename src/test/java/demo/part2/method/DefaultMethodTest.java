package demo.part2.method;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMethodTest {

    private interface SomeInterface {
        default void defaultMethod() {}
    }

    @Test
    public void defaultMethod() throws ReflectiveOperationException {
        Class<?> clazz = SomeInterface.class;
        Method method = clazz.getDeclaredMethod("defaultMethod");
        assertTrue(method.isDefault());
    }
}
