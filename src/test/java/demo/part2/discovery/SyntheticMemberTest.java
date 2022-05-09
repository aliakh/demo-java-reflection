package demo.part2.discovery;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

public class SyntheticMemberTest extends ReflectionTest {

    private enum SomeEnum {
        INSTANCE
    }

    @Test
    public void member() throws ReflectiveOperationException {
        Class<?> clazz = SomeEnum.class;
        Member member = clazz.getDeclaredField("$VALUES");

        assertEquals(SomeEnum.class, member.getDeclaringClass());
        assertEquals("$VALUES", member.getName());
        assertEquals("private static final", Modifier.toString(member.getModifiers()));
        assertTrue(member.isSynthetic());
    }
}
