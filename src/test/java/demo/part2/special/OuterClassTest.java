package demo.part2.special;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

public class OuterClassTest extends ReflectionTest {

    @Test
    public void testFields() {
        Class<?> clazz = SomeOuterClass.SomeInnerClass.class;

        Field[] fields = clazz.getDeclaredFields();
        assertEquals(1, fields.length);

        Field field = fields[0];
        assertEquals("/* synthetic */ final demo.part2.synthetic.SomeOuterClass demo.part2.synthetic.SomeOuterClass$SomeInnerClass.this$0", toString(field));

        assertEquals("this$0", field.getName());
        assertEquals("final", Modifier.toString(field.getModifiers()));
        assertTrue(field.isSynthetic());
        assertEquals(SomeOuterClass.class, field.getType());
    }

    @Test
    public void testMethods() {
        Class<?> clazz = SomeOuterClass.SomeInnerClass.class;

        Method[] methods = clazz.getDeclaredMethods();
        assertEquals(0, methods.length);
    }

    @Test
    public void testConstructors() {
        Class<?> clazz = SomeOuterClass.SomeInnerClass.class;

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        assertEquals(1, constructors.length);
        Constructor<?> constructor = constructors[0];
        assertEquals("demo.part2.synthetic.SomeOuterClass$SomeInnerClass(demo.part2.synthetic.SomeOuterClass)", toString(constructor));

        assertEquals("demo.part2.synthetic.SomeOuterClass$SomeInnerClass", constructor.getName());
        assertEquals("", Modifier.toString(constructor.getModifiers()));
        assertFalse(constructor.isSynthetic());

        Parameter[] parameters = constructor.getParameters();
        assertEquals(1, parameters.length);
        Parameter parameter = parameters[0];
        assertEquals("arg0", parameter.getName());
        assertEquals("", Modifier.toString(parameter.getModifiers()));
        assertEquals(SomeOuterClass.class, parameter.getType());
        assertFalse(parameter.isSynthetic());
        assertFalse(parameter.isImplicit());
    }

    @Test
    public void testNestedClasses() {
        Class<?> clazz = SomeOuterClass.SomeInnerClass.class;

        Class<?>[] classes = clazz.getDeclaredClasses();
        assertEquals(0, classes.length);
    }
}
