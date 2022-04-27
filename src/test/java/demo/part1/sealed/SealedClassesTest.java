package demo.part1.sealed;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SealedClassesTest extends ReflectionTest {

    sealed class Polygon permits Triangle, Quadrangle, Pentagon {}

    final class Triangle extends Polygon {}
    sealed class Quadrangle extends Polygon permits Parallelogram, Trapezoid, Kite {}
    non-sealed class Pentagon extends Polygon {}

    non-sealed class Parallelogram extends Quadrangle {}
    non-sealed class Trapezoid extends Quadrangle {}
    non-sealed class Kite extends Quadrangle {}

    @Test
    public void sealedClasses() {
        Class<?> clazz = Polygon.class;
        assertTrue(clazz.isSealed());

        assertEquals(
            Set.of(
                Pentagon.class,
                Triangle.class,
                Quadrangle.class
            ),
            toSet(clazz.getPermittedSubclasses())
        );

        assertFalse(Triangle.class.isSealed());
        assertTrue(Quadrangle.class.isSealed());
        assertFalse(Pentagon.class.isSealed());
    }
}
