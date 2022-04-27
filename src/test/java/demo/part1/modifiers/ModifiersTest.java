package demo.part1.modifiers;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ModifiersTest {

    @Test
    public void getModifiers() {
        Class<?> clazz = Integer.class;
        int modifiers = clazz.getModifiers();

        assertEquals(0b00000000_00010001, modifiers);
        assertEquals("public final", Modifier.toString(modifiers));
    }

    @Test
    public void isType() {
        Class<?> clazz = Integer.class;

        assertFalse(clazz.isInterface());
        assertFalse(clazz.isArray());
        assertFalse(clazz.isPrimitive());
        assertFalse(clazz.isAnnotation());
        assertFalse(clazz.isSynthetic());
        assertFalse(clazz.isAnonymousClass());
        assertFalse(clazz.isLocalClass());
        assertFalse(clazz.isMemberClass());
        assertFalse(clazz.isEnum());
        assertFalse(clazz.isRecord());
        assertFalse(clazz.isHidden());
        assertFalse(clazz.isSealed());
    }
}
