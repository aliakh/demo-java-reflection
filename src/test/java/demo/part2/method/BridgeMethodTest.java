package demo.part2.method;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeMethodTest {

    private static class SomeClass implements Comparator<Integer> {

        @Override
        public int compare(Integer i1, Integer i2) {
            return 0;
        }
/*
        public int compare(Object o1, Object o2) {
            return compare((Integer) o1, (Integer) o2);
        }
*/
    }

    @Test
    public void bridgeMethod() throws ReflectiveOperationException {
        Class<?> clazz = SomeClass.class;

        Method explicitMethod = clazz.getDeclaredMethod("compare", Integer.class, Integer.class);
        assertFalse(explicitMethod.isBridge());
        assertFalse(explicitMethod.isSynthetic());

        Method bridgeMethod = clazz.getDeclaredMethod("compare", Object.class, Object.class);
        assertTrue(bridgeMethod.isBridge());
        assertTrue(bridgeMethod.isSynthetic());
    }
}
