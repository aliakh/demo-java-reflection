package demo.part1.hidden;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HiddenClassTest {

    @Test
    void hiddenClassTest() throws ReflectiveOperationException, IOException {
        byte[] classFile = getClassFileAsBytes(SomeClass.class);
        Class<?> clazz = MethodHandles.lookup()
            .defineHiddenClass(classFile, true, ClassOption.NESTMATE)
            .lookupClass();
        assertTrue(clazz.isHidden());

        System.out.println(clazz.getSimpleName()); // SomeClass/0x0000000800d34000

        Object object = clazz.getConstructor().newInstance();
        Method method = object.getClass().getDeclaredMethod("someMethod");
        assertEquals(1, method.invoke(object));
    }

    private byte[] getClassFileAsBytes(Class<?> clazz) throws IOException {
        String className = clazz.getName().replace('.', '/') + ".class";
        InputStream stream = clazz.getClassLoader().getResourceAsStream(className);
        return stream.readAllBytes();
    }
}
