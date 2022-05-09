### The Member interface


##### Code examples

The example class is an enum class with one enum constant.


```
enum SomeEnum {
    INSTANCE
}
```


The following code uses the methods of the `Member` interface to read information about the synthetic `$VALUES` field that exists in enums in the described Java implementation.


```
Class<?> clazz = SomeEnum.class;
Member member = clazz.getDeclaredField("$VALUES");

assertEquals(SomeEnum.class, member.getDeclaringClass());
assertEquals("$VALUES", member.getName());
assertEquals("private static final", Modifier.toString(member.getModifiers()));
assertTrue(member.isSynthetic());
