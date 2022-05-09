package demo.part1.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class GetClassTest {

    @Test
    public void objectGetClass() {
        Class<? extends Integer> objectClass = Integer.valueOf(1).getClass();

        Class<? extends Integer[]> objectArrayClass = new Integer[]{}.getClass();
        Class<? extends int[]> primitiveArrayClass = new int[]{}.getClass();
    }

    @Test
    public void classLiteral() {
        Class<Integer> classClass = Integer.class;
        Class<Runnable> interfaceClass = Runnable.class;

        Class<Integer[]> objectArrayClass = Integer[].class;
        Class<int[]> primitiveArrayClass = int[].class;

        Class<Integer> primitiveClass = int.class;
    }

    @Test
    public void classConstant() {
        Class<Integer> primitiveClassFromConstant = Integer.TYPE;
        Class<Integer> primitiveClassFromLiteral = int.class;
        assertSame(primitiveClassFromConstant, primitiveClassFromLiteral);
    }

    @Test
    public void ClassForName() throws ClassNotFoundException {
        Class<?> classClass = Class.forName("java.lang.Integer");
        Class<?> interfaceClass = Class.forName("java.lang.Runnable");

        Class<?> objectArrayClass = Class.forName("[Ljava.lang.Integer;");
        Class<?> primitiveArrayClass = Class.forName("[I");
    }
}