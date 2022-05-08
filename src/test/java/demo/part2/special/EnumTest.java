package demo.part2.special;

import demo.ReflectionTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EnumTest extends ReflectionTest {

    @ParameterizedTest
    @MethodSource("getClasses")
    public void tesClass(Class<?> clazz) {
        assertEquals(Enum.class, clazz.getSuperclass());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        assertEquals(
            Set.of(
                "public static final demo.part2.synthetic.SomeEnum demo.part2.synthetic.SomeEnum.INSTANCE",
                "/* synthetic */ private static final demo.part2.synthetic.SomeEnum[] demo.part2.synthetic.SomeEnum.$VALUES"
            ),
            toStringSet(fields)
        );

        Field field0 = getByName(fields, "INSTANCE");
        assertEquals("INSTANCE", field0.getName());
        assertEquals("public static final", Modifier.toString(field0.getModifiers()));
        assertFalse(field0.isSynthetic());
        assertEquals(SomeEnum.class, field0.getType());

        Field field1 = getByName(fields, "$VALUES");
        assertEquals("$VALUES", field1.getName());
        assertEquals("private static final", Modifier.toString(field1.getModifiers()));
        assertTrue(field1.isSynthetic());
        assertEquals(SomeEnum[].class, field1.getType());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        assertEquals(
            Set.of(
                "public static demo.part2.synthetic.SomeEnum[] demo.part2.synthetic.SomeEnum.values()",
                "public static demo.part2.synthetic.SomeEnum demo.part2.synthetic.SomeEnum.valueOf(java.lang.String)",
                "/* synthetic */ private static demo.part2.synthetic.SomeEnum[] demo.part2.synthetic.SomeEnum.$values()"
            ),
            toStringSet(methods)
        );

        Method method0 = getByName(methods, "values");
        assertEquals("values", method0.getName());
        assertEquals("public static", Modifier.toString(method0.getModifiers()));
        assertFalse(method0.isSynthetic());
        assertEquals(0, method0.getParameters().length);
        assertEquals(SomeEnum[].class, method0.getReturnType());

        Method method1 = getByName(methods, "valueOf");
        assertEquals("valueOf", method1.getName());
        assertEquals("public static", Modifier.toString(method1.getModifiers()));
        assertFalse(method1.isSynthetic());
        assertEquals(1, method1.getParameters().length);
        assertEquals(SomeEnum.class, method1.getReturnType());

        Parameter parameter = method1.getParameters()[0];
        assertEquals("arg0", parameter.getName());
        assertEquals("", Modifier.toString(parameter.getModifiers()));
        assertEquals(String.class, parameter.getType());
        assertFalse(parameter.isSynthetic());
        assertFalse(parameter.isImplicit());

        Method method2 = getByName(methods, "$values");
        assertEquals("$values", method2.getName());
        assertEquals("private static", Modifier.toString(method2.getModifiers()));
        assertTrue(method2.isSynthetic());
        assertEquals(0, method2.getParameters().length);
        assertEquals(SomeEnum[].class, method2.getReturnType());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        assertEquals(1, constructors.length);

        Constructor<?> constructor = constructors[0];
        assertEquals("private demo.part2.synthetic.SomeEnum(java.lang.String,int)", toString(constructor));

        assertEquals("demo.part2.synthetic.SomeEnum", constructor.getName());
        assertEquals("private", Modifier.toString(constructor.getModifiers()));
        assertFalse(constructor.isSynthetic());

        Parameter[] parameters = constructor.getParameters();
        assertEquals(2, parameters.length);

        Parameter parameter0 = parameters[0];
        assertEquals("arg0", parameter0.getName());
        assertEquals("", Modifier.toString(parameter0.getModifiers()));
        assertEquals(String.class, parameter0.getType());
        assertFalse(parameter0.isSynthetic());
        assertFalse(parameter0.isImplicit());

        Parameter parameter1 = parameters[1];
        assertEquals("arg1", parameter1.getName());
        assertEquals("", Modifier.toString(parameter1.getModifiers()));
        assertEquals(int.class, parameter1.getType());
        assertFalse(parameter1.isSynthetic());
        assertFalse(parameter1.isImplicit());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testNestedClasses(Class<?> clazz) {
        Class<?>[] classes = clazz.getDeclaredClasses();
        assertEquals(0, classes.length);
    }

    private static Stream<Arguments> getClasses() {
        return Stream.of(
            Arguments.of(SomeEnum.class),
            Arguments.of(SomeEnum.INSTANCE.getClass())
        );
    }
}
