package demo.part1.inheritance;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

import static org.junit.jupiter.api.Assertions.*;

public class SupertypeTest {
    
    @Test
    public void getSuperclass() {
        assertEquals(AbstractList.class, ArrayList.class.getSuperclass());

        assertNull(Object.class.getSuperclass());
        assertNull(Serializable.class.getSuperclass()); // any interface
        assertNull(int.class.getSuperclass()); // any primitive type or pseudo-type void
        assertEquals(Object.class, int[].class.getSuperclass()); // any array
    }

    @Test
    public void getInterfaces() {
        assertArrayEquals(
            new Class[]{List.class, RandomAccess.class, Cloneable.class, Serializable.class},
            ArrayList.class.getInterfaces()
        );

        assertArrayEquals(new Class[]{}, Object.class.getInterfaces());
        assertArrayEquals(new Class[]{}, int.class.getInterfaces()); // any primitive type or pseudo-type void
        assertArrayEquals(new Class[]{Cloneable.class, Serializable.class}, int[].class.getInterfaces()); // any array
    }    
}
