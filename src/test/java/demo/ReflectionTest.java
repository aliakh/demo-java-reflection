package demo;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionTest {

    protected String toString(Class<?> clazz) {
        return (clazz.isSynthetic() ? "/* synthetic */ " : "") + clazz.toString();
    }

    protected Set<String> toStringSet(Class<?>[] array) {
        return Arrays
                .stream(array)
                .map(this::toString)
                .collect(Collectors.toSet());
    }

    protected <T extends Member> String toString(T member) {
        return (member.isSynthetic() ? "/* synthetic */ " : "") + member.toString();
    }

    protected <T extends Member> Set<String> toStringSet(T[] array) {
        return Arrays
                .stream(array)
                .map(this::toString)
                .collect(Collectors.toSet());
    }

    protected <T> Set<T> toSet(T[] array) {
        return Arrays
                .stream(array)
                .collect(Collectors.toSet());
    }

    protected <T extends Member> T getByName(T[] array, String name) {
        return Arrays
            .stream(array)
            .filter(field -> field.getName().equals(name))
            .findFirst()
            .orElseThrow();
    }
}
