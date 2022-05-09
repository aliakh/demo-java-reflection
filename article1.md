# Java Reflection, part 1: primitives, arrays, classes


## Introduction

Reflection is the ability of a program to introspect and modify its structure and behavior at runtime. The Java language supports some reflection features in its Core Reflection API which consists of classes from the `java.lang.reflect` package, as well as the `Class`, `Package`, and `Module` classes.

The first step in using reflection on a type (primitive type, array type, class or interface) is to obtain a `Class` object and determine the parameters of that type. The next steps may include using the methods of this `Class` object to determine its fields, methods, constructors, member classes and member interfaces, annotations, generic types, and others.

<sub>This article is based on the Java 17 implementation in Oracle OpenJDK.</sub>


## Types

There are two kinds of types in the Java language: primitive types and reference types. Primitive types include types `boolean`, `byte`, `short`, `int`, `long`, `char`, `float`, `double`, and the pseudo-type `void`. Reference types include class types, interface types, array types (and type variables).

<sub>There is also a special <em>null</em> type, the type of the expression `null`, which has no name. </sub>


## The Class class

The entry point for the Core Reflection API is the `Class` class. Among other methods, this class has the following methods for discovering various parameters of the given type:

![methods of the Class class](/images/part1/methods_of_the_Class_class.png)

There are three ways to get a `Class` object at compile-time (respectively for objects, reference and primitive types, primitive types) and one way to do the same at runtime (for reference types only).

For an object (class instance or array), you can use the `Object::getClass` method on that object.


```
Class<? extends Integer> objectClass = Integer.valueOf(1).getClass();
Class<? extends Integer[]> objectArrayClass = new Integer[]{}.getClass();
Class<? extends int[]> primitiveArrayClass = new int[]{}.getClass();
```


For a reference type (class, interface, or array type) or primitive type, you can use a class literal (an expression consisting of the type name, followed by a “.” and the `class` token).


```
Class<Integer> classClass = Integer.class;
Class<Integer[]> objectArrayClass = Integer[].class;
Class<int[]> primitiveArrayClass = int[].class;
Class<Integer> primitiveClass = int.class;
```


For a primitive type, you can use a class constant (a _public static final_ field defined in the corresponding wrapper class).


```
Class<Integer> primitiveClassFromConstant = Integer.TYPE;
Class<Integer> primitiveClassFromLiteral = int.class;
assertSame(primitiveClassFromConstant, primitiveClassFromLiteral);
```


For a reference type (class, interface, or array type) you can use the static `Class::forName` method to get (and to load, link, and initialize if necessary) a class by its fully qualified name.


```
Class<?> classClass = Class.forName("java.lang.Integer");
Class<?> objectArrayClass = Class.forName("[Ljava.lang.Integer;");
Class<?> primitiveArrayClass = Class.forName("[I");
```



## Modifiers

In the Java language, different constructs (classes, interfaces, fields, methods, constructors, and parameters) can have modifiers in their declarations to denote their access permissions and properties:



* `abstract` and `final`
* `native`
* `private`
* `protected`
* `public`
* `static`
* `strictfp`
* `synchronized`
* `transient`
* `volatile`
* `sealed` and `non-sealed`

<sub>In addition, in the Java VM, there is an additional `interface` modifier, which is considered a pseudo-modifier in the Java language.</sub>

The `Class` class contains a method for determining modifiers:



* `int getModifiers()` - returns the Java language modifiers `public`, `protected`, `private`, `final`, `static`, `abstract`, and `interface` for this `Class` object, encoded in an integer


```
Class<?> clazz = Integer.class;
assertEquals(0b00000000_00010001, clazz.getModifiers());
```


In addition to the `getModifiers` method, which returns modifiers encoded as bits in an integer, the `Class` class also contains methods for determining the kind of this type:



* `boolean isInterface()`
* `boolean isArray()`
* `boolean isPrimitive()`
* `boolean isAnnotation()`
* `boolean isSynthetic()`
* `boolean isAnonymousClass()`
* `boolean isLocalClass()`
* `boolean isMemberClass()`
* `boolean isEnum()`
* `boolean isRecord()`
* `boolean isHidden()`
* `boolean isSealed()`


```
Class<?> clazz = Integer.class;
assertFalse(clazz.isPrimitive());
```



### The Modifier class

The `Modifier` class is a utility class with static methods for decoding the Java language modifiers for classes, interfaces, fields, methods, constructors, and parameters.

This class declares the methods to check for the presence of individual bits in the specified modifiers parameter:



* `static boolean isAbstract(int modifiers)`
* `static boolean isFinal(int modifiers)`
* `static boolean isInterface(int modifiers)`
* `static boolean isNative(int modifiers)`
* `static boolean isPrivate(int modifiers)`
* `static boolean isProtected(int modifiers)`
* `static boolean isPublic(int modifiers)`
* `static boolean isStatic(int modifiers)`
* `static boolean isStrict(int modifiers)`
* `static boolean isSynchronized(int modifiers)`
* `static boolean isTransient(int modifiers)`
* `static boolean isVolatile(int modifiers)`


```
Class<?> clazz = Integer.class;
int modifiers = clazz.getModifiers();

assertTrue(Modifier.isPublic(modifiers));
assertTrue(Modifier.isFinal(modifiers));

assertEquals("public final", Modifier.toString(modifiers));
```


Also, this class declares the methods to get all possible modifiers that can be applied to classes, interfaces, fields, methods, constructors and parameters:



* `static int classModifiers()`
* `static int interfaceModifiers()`
* `static int fieldModifiers()`
* `static int methodModifiers()`
* `static int constructorModifiers()`
* `static int parameterModifiers()`


```
assertEquals(0b00001100_00011111, Modifier.classModifiers());
assertEquals("public protected private abstract static final strictfp", Modifier.toString(Modifier.classModifiers()));
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/modifiers/code_examples.md)


## Primitives

The primitive types are the `boolean` type, the numeric types, and the pseudo-type `void`. The numeric types include the integral types `byte`, `short`, `int`, `long`, and `char` and the floating-point types `float` and `double`.

The `Class` class contains a method for determining primitives:



* `boolean isPrimitive()` - returns `true`, if and only if this `Class` object represents a primitive type or the pseudo-type `void`


```
assertTrue(int.class.isPrimitive());
assertTrue(Integer.TYPE.isPrimitive());
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/primitives/code_examples.md)


## Arrays

Arrays are objects that can contain several variables (array components) that are referenced by non-negative integer index values. The array components may themselves be arrays. The array components of the lowest nested array that are not themselves arrays are called array elements.

<sub>For example, for an array of type `int[][][]`, the component type is `int[][]`, and the element type is `int`.</sub>

Every array has an associated `Class` object, shared with all other arrays with the same component type. Although an array type is not a class, the `Class` object of every array acts as if its direct superclass is the `Object` class and the direct superinterfaces are the `Cloneable` and `Serializable` interfaces.

The class `Class` contains methods for determining arrays:



* `boolean isArray()` - returns `true`, if and only if this `Class` object represents an array
* `Class<?> getComponentType()` (since Java 1.1) and its equivalent `Class<?> componentType()` (since Java 12) - return the `Class` representing the component type of an array, if this `Class` object represents an array
* `Class<?> arrayType()` - returns a `Class` for an array type whose component type is represented by this `Class`, if this `Class` object represents an array


```
Class<?> clazz = Integer[].class;
assertTrue(clazz.isArray());

assertEquals(Integer.class, clazz.componentType());
assertEquals(Integer[][].class, clazz.arrayType());
```



### The Array class

The `Array` class is a utility class with static methods for dynamically creating new arrays and getting and setting array components.

This class declares the following methods:



* `static int getLength(Object array)` - returns the length of the specified array object
* `static Object get(Object array, int index)` - returns the value of the indexed component in the specified array object <sup>(*)</sup>
* `static void set(Object array, int index, Object value)` - sets the value of the indexed component of the specified array object to the specified new value <sup>(*)</sup>
* `static Object newInstance(Class<?> componentType, int length)` - creates a new one-dimensional array with the specified component type and length
* `static Object newInstance(Class<?> componentType, int... dimensions)` - creates a new multi-dimentional array with the specified component type, dimensions, and lengths

<sub>(*) The `Array` class also declares similar methods for getting and setting components for `boolean`, `byte`, `short`, `int`, `long`, `char`, `float`, `double` arrays.</sub>


```
Object array = Array.newInstance(int.class, 1);
assertTrue(array.getClass().isArray());

assertEquals(1, Array.getLength(array));

assertEquals(0, Array.getInt(array, 0));
Array.setInt(array, 0, 2);
assertEquals(2, Array.getInt(array, 0));
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/arrays/code_examples.md)


## Inheritance

The `Class` class has three groups of methods for analyzing type inheritance.

The first subgroup of these methods is for determining the _direct supertypes_ (that is, which class and interfaces are mentioned in the `extends` and `implements` clauses of the class or interface declaration):



* `Class<? super T> getSuperclass()` - returns the `Class` representing the direct superclass of the type represented by this `Class` object
* `Class<?>[] getInterfaces()` - returns an array of `Class` objects representing the direct superinterfaces of the type represented by this `Class` object


```
Class<?> clazz = Integer.class;
assertEquals(Number.class, clazz.getSuperclass());
assertArrayEquals(
   new Class[]{Comparable.class, Constable.class, ConstantDesc.class},
   clazz.getInterfaces()
);
```


The second subgroup of these methods is for determining _whether one type can be cast to another type_ (that is, whether one type is the same type or a subtype of another type):



* `boolean isInstance(Object obj)` - returns `true`, if and only if the specified `Object` parameter is non-null and can be cast to the type represented by this `Class` object
* `boolean isAssignableFrom(Class<?> cls)` - returns `true`, if and only if the specified `Class` parameter is either the same type as, or is a subtype represented by this `Class` object

>The `isInstance` method is the runtime equivalent of the compile-time `instanceof` operator.


```
assertTrue(Number.class.isInstance(Integer.valueOf(1)));
assertTrue(Number.class.isAssignableFrom(Integer.class));
```


The third subgroup of these methods is for _casting one type to another type_:



* `T cast(Object obj)` - casts the specified `Object` parameter to the type represented by this `Class` object
* `<U> Class<? extends U> asSubclass(Class<U> cls)` - casts this `Class` object to a subclass of the class represented by the specified `Class` parameter

>The `cast` method is the runtime equivalent of the compile-time _cast_ operator.


```
Number number = Integer.valueOf(1);
Integer integer = Integer.class.cast(number);
assertEquals(1, integer);
```





```
Number number = Integer.valueOf(1);
Class<? extends Number> numberClass = number.getClass();
Class<? extends Integer> integerClass = number.getClass().asSubclass(Integer.class);
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/inheritance/code_examples.md)


## Special classes and interfaces

In addition to the regular types (classes and interfaces) in the Java language, there are special types: types of different enclosing scopes (top-level and nested classes and interfaces), restricted types (enum classes, record classes, sealed classes and interfaces), and types with special implementation (synthetic and hidden classes and interfaces).


### Nested classes and interfaces

There are different kinds of classes depending on which enclosing context they are declared in.

Top-level classes are classes declared directly in compilation units.

Nested classes are classes declared within the body of another class or interface declaration. Nested classes may be static or non-static member classes, local classes, or anonymous classes. Some kinds of nested classes that are not explicitly or implicitly _static_ (and therefore can refer to instances of the enclosing classes) are inner classes.

The `Class` class has four groups of methods for analyzing nested classes and interfaces.


##### Kinds of nested classes

A member class is a class whose declaration is directly enclosed in the body of another class or interface declaration. A local class is a class declared within a block. An anonymous class is a class without a name, also declared within a block, that is implicitly declared by a class instance creation expression _or_ by an enum constant with a class body.

The first subgroup of these methods is for determining the _kind of nested classes_:



* `boolean isMemberClass()` - returns `true`, if and only if this class is a member class
* `boolean isLocalClass()` -  returns `true`, if and only if this class is a local class
* `boolean isAnonymousClass()` - returns `true`, if and only this class is an anonymous class


##### Public vs. declared members

A class inherits _public_, _protected_, _package access_ (both static and non-static) but not _private_ member classes and member interfaces from its superclass and superinterfaces.

The second subgroup of these methods is for determining _member classes and member interfaces_:



* `Class<?>[] getClasses()` - returns an array containing `Class` objects representing all the _public_ class and interface members of the class represented by this `Class` object (this includes _public_ class and interface members declared by the class and inherited _public_ class and interface members)
* `Class<?>[] getDeclaredClasses()` - returns an array of `Class` objects reflecting all the class and interface members of the class represented by this `Class` object (this includes _public_, _protected_, _package access_, and _private_ classes and interfaces declared by the class, but excludes inherited classes and interfaces)


##### Enclosing vs. declaring context

Any nested class (member class, local class, or anonymous class) has an immediately enclosing class. Only a member class has a declaring class - the top-level class of which it is a member.

The third subgroup of these methods is for determining enclosing context (method, constructor, or class) for local and anonymous classes and declaring context (only class) for member classes:



* `Method getEnclosingMethod()` - returns a `Method` object representing the _immediately enclosing method_ of the underlying class, if this `Class` object represents a local or anonymous class within a method
* `Constructor<?> getEnclosingConstructor()` - returns a `Constructor` object representing the _immediately enclosing constructor_ of the underlying class, if this `Class` object represents a local or anonymous class within a constructor
* `Class<?> getEnclosingClass()` - returns the _immediately enclosing class_ of the underlying class, if this `Class` object represents a nested class
* `Class<?> getDeclaringClass()` - if the class or interface represented by this `Class` object is a member of another class, returns the `Class` object representing the class in which it was declared


##### Nest-Based Access Control

Nest-based access control (since Java 11) is a new implementation of nested classes that allows them to access each other's _private_ members without the need for a Java compiler to generate synthetic bridge methods.

A top-level class, plus all classes nested within it, are forming a nest. All classes in a nest allow mutual access to their _private_ members. Every class belongs to exactly one nest. A top-level class that forms the nest is described as a nest host. Two classes in a nest are described as nestmates.

The fourth subgroup of these methods is for determining nests:



* `Class<?> getNestHost()` - returns the nest host of the nest to which this class represented by this `Class` object belongs
* `boolean isNestmateOf(Class<?> class)` - determines if the given `Class` is a nestmate of the class or interface represented by this `Class` object
* `Class<?>[] getNestMembers()` - returns an array containing `Class` objects representing all the classes and interfaces that are members of the nest to which the class represented by this `Class` object belongs (the zeroth element of the returned array is the nest host)

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/nested/code_examples.md)


### Enum classes

Enum classes (since Java 5.0) are restricted kinds of classes that define a fixed set of named class instances (enum constants).

<sub>If an enum constant is declared with a class body, the class of that enum constant is an anonymous class and not the enum class where this enum constant is declared.</sub>

The `Class` class declares the methods for determining enum classes:



* `boolean isEnum()` - returns `true`, if and only if this class is an enum class
* `T[] getEnumConstants()` - returns an array containing the enum constants comprising the enum class represented by this `Class` object (in the order they are declared), if this class is an enum class


```
enum SomeEnum {
   INSTANCE
}
```





```
Class<SomeEnum> clazz = SomeEnum.class;
assertTrue(clazz.isEnum());
assertEquals(Enum.class, clazz.getSuperclass());
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/enums/code_examples.md)


### Synthetic classes and interfaces

Synthetic classes and interfaces (since Java 1.3) are generated by the Java compiler and are not explicitly or implicitly declared.

<sub>A construct emitted by a Java compiler is marked as <em>synthetic</em> if it does not correspond to a construct declared explicitly or implicitly in the source code (unless the emitted construct is a class initialization method).</sub>

The `Class` class declares the method for determining synthetic classes:



* `boolean isSynthetic()` - returns `true`, if and only if this class was generated by the Java compiler


```
Class<? extends Runnable> clazz = ((Runnable) () -> {}).getClass();
assertTrue(clazz.isSynthetic());

System.out.println(clazz.getSimpleName()); // SyntheticClassTest$$Lambda$363/0x0000000800d327b8
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/synthetic/code_examples.md)


### Hidden classes and interfaces

Hidden classes and interfaces (since Java 15) are a new implementation of runtime generated classes that are not visible to other classes either at compile-time or runtime by class loaders (for example, by the `Class::forName` method). The hidden class is only dynamically created by invoking the `java.lang.invoke.Lookup::defineHiddenClass` method with the _class_ file as the byte array argument.

<sub>Hidden classes had replaced the anonymous classes that were created by the `sun.misc.Unsafe::defineAnonymousClass` method (which was deprecated in Java 15 and removed in Java 17).</sub>

The `Class` class declares the method for determining hidden classes:



* `boolean isHidden()` - returns `true`, if and only if this class is a hidden class or interface


```
byte[] classFile = getClassFileAsBytes(SomeClass.class);
Class<?> clazz = MethodHandles.lookup()
   .defineHiddenClass(classFile, true, ClassOption.NESTMATE)
   .lookupClass();
assertTrue(clazz.isHidden());

System.out.println(clazz.getSimpleName()); // SomeClass/0x0000000800d34000
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/hidden/code_examples.md)


### Record classes

Record classes (previewed in Java 14 and released in Java 16) are restricted kinds of classes that define an immutable aggregate of values. From record components in the record header, the Java compiler created the following implicit constructs:



* a _private final_ field and a _public_ accessor method (for each record component in the record header)
* a canonical constructor whose signature is the same as the record header
* _public final_ methods `equals`, `hashCode` and `toString`

The `Class` class declares the methods for determining record classes:



* `boolean isRecord()` - returns `true`, if and only if this class is a record class
* `RecordComponent[] getRecordComponents()` - returns an array of `RecordComponent` objects representing all the record components of this record class (in the order they are declared), if this `Class` object represents a record class


```
record SomeRecord(int i) {
}
```





```
Class<?> clazz = SomeRecord.class;
assertTrue(clazz.isRecord());
assertTrue(Modifier.isFinal(clazz.getModifiers()));
```


The `RecordComponent` class declares the methods for determining record components:



* `Class<?> getDeclaringRecord()` - returns the record class which declares this record component
* `String getName()` - returns the name of this record component
* `Class<?> getType()` - returns a `Class` that identifies the declared type for this record component
* `Method getAccessor()` - returns a `Method` that represents the accessor method for this record component


```
RecordComponent[] components = clazz.getRecordComponents();
assertEquals(1, components.length);

RecordComponent component = components[0];
assertEquals(clazz, component.getDeclaringRecord());
assertEquals("i", component.getName());
assertEquals(int.class, component.getType());
assertEquals("public int demo.SomeRecord.i()", component.getAccessor().toString());
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/records/code_examples.md)


### Sealed classes and interfaces

Sealed classes and interfaces (previewed in Java 15 and released in Java 17) restrict which other classes or interfaces may extend or implement them. A sealed class has the `sealed` modifier to its declaration and the `permits` clause that specifies the classes that may extend the sealed class. Permitted subclasses must directly extend the sealed class and must have exactly one of the following modifiers to describe how it continues the sealing initiated by its superclass:



* `final` - cannot be extended further
* `sealed` - can only be extended by its permitted subclasses
* `non-sealed` - can be extended by unknown subclasses

The `Class` class declares the methods for determining sealed classes and interfaces:



* `boolean isSealed()` - returns `true`, if and only if this class is a sealed class or interface
* `Class<?>[] getPermittedSubclasses()` - returns an array of `Class` objects of the directly permitted subclasses and subinterfaces of this class or interface, if this `Class` object represents a sealed class or interface


```
sealed class Polygon permits Triangle, Quadrangle, Pentagon {}

final class Triangle extends Polygon {}
sealed class Quadrangle extends Polygon permits Parallelogram, Trapezoid, Kite {}
non-sealed class Pentagon extends Polygon {}
```





```
Class<?> clazz = Polygon.class;
assertTrue(clazz.isSealed());

assertEquals(
   Set.of(Triangle.class, Quadrangle.class, Pentagon.class),
   toSet(clazz.getPermittedSubclasses())
);
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/sealed/code_examples.md)


## Conclusion

The `Class` class is the entry point for the Core Reflection API. Once you obtain a `Class` object for a type, you can determine the properties of that type: the kind of type (primitive type, array type, class or interface), modifiers, specification and implementation details.

Structures in the Java source code do not always have a one-to-one correspondence with structures in the generated _class_ file. In some cases, reflection reveals that the generated class file has _implicit_ constructs (absent in the source code but mandated by the Java Language Specification) or _synthetic_ constructs (generated by a Java compiler in a way that is not specified by this specification).

Complete code examples are available in the [GitHub repository](https://github.com/aliakh/demo-java-reflection).
