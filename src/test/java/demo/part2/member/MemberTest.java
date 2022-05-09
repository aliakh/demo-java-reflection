package demo.part2.member;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberTest {

    private enum SomeEnum {
        INSTANCE
    }

    @Test
    public void memberTest() throws ReflectiveOperationException {
        Class<?> clazz = SomeEnum.class;
        Member member = clazz.getDeclaredField("$VALUES");

        assertEquals(SomeEnum.class, member.getDeclaringClass());
        assertEquals("$VALUES", member.getName());
        assertEquals("private static final", Modifier.toString(member.getModifiers()));
        assertTrue(member.isSynthetic());
    }
}
