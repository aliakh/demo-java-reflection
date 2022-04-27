package demo.part1.synthetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntheticClassTest {

    @Test
    public void syntheticClass() {
        Class<? extends Runnable> clazz = ((Runnable) () -> {}).getClass();
        assertTrue(clazz.isSynthetic());

        System.out.println(clazz.getSimpleName()); // SyntheticClassTest$$Lambda$363/0x0000000800d327b8
    }
}
