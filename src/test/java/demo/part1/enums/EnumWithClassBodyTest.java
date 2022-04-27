package demo.part1.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnumWithClassBodyTest {

    enum SomeEnum {
        INSTANCE {
        }
    }

    @Test
    public void enumWithClassBodyTest() {
        Class<SomeEnum> clazz = SomeEnum.class;
        assertTrue(clazz.isEnum());
        assertEquals(Enum.class, clazz.getSuperclass());

        SomeEnum[] constants = clazz.getEnumConstants();
        assertEquals(1, constants.length);

        SomeEnum constant = constants[0];
        assertSame(SomeEnum.INSTANCE, constant);
        assertFalse(constant.getClass().isEnum());
        assertTrue(constant.getClass().isAnonymousClass());
        assertTrue(constant.getDeclaringClass().isEnum());
    }
}
