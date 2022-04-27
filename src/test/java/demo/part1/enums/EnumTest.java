package demo.part1.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnumTest {

    enum SomeEnum {
        INSTANCE
    }

    @Test
    public void enumTest() {
        Class<SomeEnum> clazz = SomeEnum.class;
        assertTrue(clazz.isEnum());
        assertEquals(Enum.class, clazz.getSuperclass());

        SomeEnum[] constants = clazz.getEnumConstants();
        assertEquals(1, constants.length);

        SomeEnum constant = constants[0];
        assertSame(SomeEnum.INSTANCE, constant);
        assertTrue(constant.getClass().isEnum());
    }
}
