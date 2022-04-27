## Modifiers


##### Сode examples

The `Class::getModifiers` method returns `public` and `final` modifiers for the `Integer` class.


```
Class<?> clazz = Integer.class;
int modifiers = clazz.getModifiers();

assertEquals(0b00000000_00010001, modifiers);
assertEquals("public final", Modifier.toString(modifiers));
```


The code uses methods of the `Class` class to determine the kind of the `Integer` class.


```
Class<?> clazz = Integer.class;

assertFalse(clazz.isInterface());
assertFalse(clazz.isArray());
assertFalse(clazz.isPrimitive());
assertFalse(clazz.isAnnotation());
assertFalse(clazz.isSynthetic());
assertFalse(clazz.isAnonymousClass());
assertFalse(clazz.isLocalClass());
assertFalse(clazz.isMemberClass());
assertFalse(clazz.isEnum());
assertFalse(clazz.isRecord());
assertFalse(clazz.isHidden());
assertFalse(clazz.isSealed());
```



### The Modifier class


##### Сode examples

The code uses methods of the `Modifier` class to check for the existence of individual modifiers in the `Integer` class.


```
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
```


The code uses methods of the `Modifier` class to get all possible modifiers  that can be applied to classes, interfaces, fields, methods, constructors, and parameters. The modifiers are converted to a string in _canonical order_ from the Java Language Specification.


```
assertEquals("public protected private abstract static final strictfp", Modifier.toString(Modifier.classModifiers()));
assertEquals("public protected private abstract static strictfp", Modifier.toString(Modifier.interfaceModifiers()));
assertEquals("public protected private static final transient volatile", Modifier.toString(Modifier.fieldModifiers()));
assertEquals("public protected private abstract static final synchronized native strictfp", Modifier.toString(Modifier.methodModifiers()));
assertEquals("public protected private", Modifier.toString(Modifier.constructorModifiers()));
assertEquals("final", Modifier.toString(Modifier.parameterModifiers()));
