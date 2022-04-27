package demo.part1.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimitivesTest {

    @Test
    public void isPrimitive() {
        assertTrue(boolean.class.isPrimitive());
        assertTrue(byte.class.isPrimitive());
        assertTrue(char.class.isPrimitive());
        assertTrue(short.class.isPrimitive());
        assertTrue(int.class.isPrimitive());
        assertTrue(long.class.isPrimitive());
        assertTrue(float.class.isPrimitive());
        assertTrue(double.class.isPrimitive());
        assertTrue(void.class.isPrimitive());

        assertTrue(Boolean.TYPE.isPrimitive());
        assertTrue(Byte.TYPE.isPrimitive());
        assertTrue(Character.TYPE.isPrimitive());
        assertTrue(Short.TYPE.isPrimitive());
        assertTrue(Integer.TYPE.isPrimitive());
        assertTrue(Long.TYPE.isPrimitive());
        assertTrue(Float.TYPE.isPrimitive());
        assertTrue(Double.TYPE.isPrimitive());
        assertTrue(Void.TYPE.isPrimitive());
    }
}
