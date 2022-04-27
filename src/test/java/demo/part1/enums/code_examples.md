### Enum classes


##### Сode examples

The following class is an enum class _without_ a class body with one enum constant.


```
enum SomeEnum {
   INSTANCE
}
```




The `Class::isEnum` method returns `true` for both the enum class and the enum constant. The `Class::getEnumConstants` method returns an array of one enum constant for this enum class.


```
Class<SomeEnum> clazz = SomeEnum.class;
assertTrue(clazz.isEnum());
assertEquals(Enum.class, clazz.getSuperclass());

SomeEnum[] constants = clazz.getEnumConstants();
assertEquals(1, constants.length);

SomeEnum constant = constants[0];
assertSame(SomeEnum.INSTANCE, constant);
assertTrue(constant.getClass().isEnum());
```


The following class is an enum class _with_ a class body with one enum constant.


```
enum SomeEnum {
   INSTANCE {
   }
}
```


The `Class::isEnum` method returns `true` only the enum class. The enum constant is an anonymous class declared in this enum class.


```
Class<SomeEnum> clazz = SomeEnum.class;
assertTrue(clazz.isEnum());
assertEquals(Enum.class, clazz.getSuperclass());

SomeEnum[] constants = clazz.getEnumConstants();
assertEquals(1, constants.length);

SomeEnum constant = constants[0];
assertSame(SomeEnum.INSTANCE, constant);
assertFalse(constant.getClass().isEnum());
assertTrue(constant.getClass().isAnonymousClass());
assertTrue(constant.getDeclaringClass().isEnum());
