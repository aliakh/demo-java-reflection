package demo.part2.discovery;

import demo.ReflectionTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodsTest extends ReflectionTest {

    @Test
    public void getDeclaredMethods() {
        Class<?> clazz = SubClass.class;

        Method[] declaredMethods = clazz.getDeclaredMethods();
        assertEquals(
            Set.of(
                "public void demo.part2.members.SubClass.subPublicMethod()",
                "protected void demo.part2.members.SubClass.subProtectedMethod()",
                "void demo.part2.members.SubClass.subDefaultMethod()",
                "private void demo.part2.members.SubClass.subPrivateMethod()"
            ),
            toStringSet(declaredMethods)
        );
    }

    @Test
    public void getDeclaredMethod() throws ReflectiveOperationException {
        Class<?> clazz = SubClass.class;

        Method declaredMethod = clazz.getDeclaredMethod("subPrivateMethod");
        assertEquals("private void demo.part2.members.SubClass.subPrivateMethod()", declaredMethod.toString());
    }

    @Test
    public void getMethods() {
        Class<?> clazz = SubClass.class;

        Method[] methods = clazz.getMethods();
        assertEquals(
            Set.of(
                "public void demo.part2.members.SuperClass.superPublicMethod()",
                "public void demo.part2.members.SubClass.subPublicMethod()",

                "public final native java.lang.Class java.lang.Object.getClass()",
                "public native int java.lang.Object.hashCode()",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public java.lang.String java.lang.Object.toString()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
            ),
            toStringSet(methods)
        );
    }

    @Test
    public void getMethod() throws ReflectiveOperationException {
        Class<?> clazz = SubClass.class;

        Method method = clazz.getMethod("superPublicMethod");
        assertEquals("public void demo.part2.members.SuperClass.superPublicMethod()", method.toString());
    }
}
