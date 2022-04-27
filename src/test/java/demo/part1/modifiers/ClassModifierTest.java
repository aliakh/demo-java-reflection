package demo.part1.modifiers;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class ClassModifierTest {

    @Test
    public void decodeModifiers() {
        Class<?> clazz = Integer.class;
        int modifiers = clazz.getModifiers();

        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isPrivate(modifiers));
        assertFalse(Modifier.isProtected(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertTrue(Modifier.isFinal(modifiers));
        assertFalse(Modifier.isSynchronized(modifiers));
        assertFalse(Modifier.isVolatile(modifiers));
        assertFalse(Modifier.isTransient(modifiers));
        assertFalse(Modifier.isNative(modifiers));
        assertFalse(Modifier.isInterface(modifiers));
        assertFalse(Modifier.isAbstract(modifiers));
        assertFalse(Modifier.isStrict(modifiers));

        assertEquals(0b00000000_00010001, modifiers);
        assertEquals("public final", Modifier.toString(modifiers));
    }

    @Test
    public void availableModifiers() {
        assertEquals("public protected private abstract static final strictfp", Modifier.toString(Modifier.classModifiers()));
        assertEquals("public protected private abstract static strictfp", Modifier.toString(Modifier.interfaceModifiers()));
        assertEquals("public protected private static final transient volatile", Modifier.toString(Modifier.fieldModifiers()));
        assertEquals("public protected private abstract static final synchronized native strictfp", Modifier.toString(Modifier.methodModifiers()));
        assertEquals("public protected private", Modifier.toString(Modifier.constructorModifiers()));
        assertEquals("final", Modifier.toString(Modifier.parameterModifiers()));
    }
}
