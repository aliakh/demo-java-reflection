package demo.part2.constructor;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorsAccessibilityTest {

    @Test
    public void publicConstructor() throws ReflectiveOperationException {
        Class<SomeClassWithConstructors> clazz = SomeClassWithConstructors.class;
        Constructor<SomeClassWithConstructors> constructor = clazz.getDeclaredConstructor();

        assertTrue(constructor.canAccess(null));

        SomeClassWithConstructors object = constructor.newInstance();
        assertNotNull(object);
    }

    @Test
    public void privateConstructor() throws ReflectiveOperationException {
        Class<SomeClassWithConstructors> clazz = SomeClassWithConstructors.class;
        Constructor<SomeClassWithConstructors> constructor = clazz.getDeclaredConstructor(int.class);

        assertFalse(constructor.canAccess(null));
        constructor.setAccessible(true);
        assertTrue(constructor.canAccess(null));

        SomeClassWithConstructors object = constructor.newInstance(1);
        assertNotNull(object);
    }
}

