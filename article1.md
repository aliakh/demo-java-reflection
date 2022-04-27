# Java Reflection, part 1: primitives, arrays, classes

## Introduction

Reflection is the ability of a program to introspect and modify its structure and behavior at runtime. The Java language supports some reflection features in its Reflection API. The first step in using reflection for a class is getting a `Class` object and determining its features. The next steps may include reading information about fields, methods, constructors, member classes and member interfaces, annotations, generic types, and others.

The Core Reflection API contains classes from the `java.lang.reflect` package, as well as the `Class`, `Package`, and `Module` classes.

<sub>Some reflection functions depend not only on the version of the Java language but also on its implementation. This article has been written based on the Oracle OpenJDK implementation of Java 17.</sub>

## Types

Types in the Java language are divided into two groups: primitive types and reference types. Primitive types include `boolean`, `byte`, `char`, `short`, `int`, `long`, `float`, `double` and pseudo-type `void`. Reference types include class types, interface types, and array types.

<sub>There is also a special <em>null</em> type, the type of the expression `null`, which has no name. </sub>

An object is a dynamically created instance of a class type or a dynamically created array. All objects, including arrays, support the methods of the `Object` class.

## The Class class

The entry point for the Core Reflection API is the `Class` class.

![methods of the Class class](/images/part1/methods_of_the_Class_class.png)

There are three ways to get a `Class` object statically (respectively for objects, reference and primitive types, primitive types) at compile-time and one way to do the same dynamically at runtime (for reference types only).

For a class instance or an array, it is possible to use the `Object::getClass` method on the object.

```
Class<? extends Integer> objectClass = Integer.valueOf(1).getClass();
Class<? extends Integer[]> objectArrayClass = new Integer[]{}.getClass();
Class<? extends int[]> primitiveArrayClass = new int[]{}.getClass();
```

For a class, interface, array type, or primitive type it is possible to use a _class literal_ (an expression consisting of the type name, followed by a `.` and the token `class`).

```
Class<Integer> classClass = Integer.class;
Class<Runnable> interfaceClass = Runnable.class;
Class<Integer[]> objectArrayClass = Integer[].class;
Class<int[]> primitiveArrayClass = int[].class;
Class<Integer> primitiveClass = int.class;
```

For a primitive type, it is possible to use a class constant (a `public static final` field defined in the corresponding wrapper class).

```
Class<Integer> primitiveClassFromConstant = Integer.TYPE;
Class<Integer> primitiveClassFromLiteral = int.class;
assertSame(primitiveClassFromConstant, primitiveClassFromLiteral);
```

For a class, interface, or array type it is possible to use the static `Class::forName` method to get (and to load, link, and initialize, if necessary) a class by the fully qualified name.

```
Class<?> classClass = Class.forName("java.lang.Integer");
Class<?> interfaceClass = Class.forName("java.lang.Runnable");
Class<?> objectArrayClass = Class.forName("[Ljava.lang.Integer;");
Class<?> primitiveArrayClass = Class.forName("[I");
```

## Modifiers

In the Java language, different constructs (classes, interfaces, fields, methods, constructors, and parameters) can have modifiers that are parts of their declaration:

* `abstract`, `final`
* `native`
* `private`, `protected`, `public`
* `static`
* `strictfp`
* `synchronized`
* `transient`
* `volatile`
* `sealed`, `non-sealed`

<sub>In addition, in the Java VM, there is an additional `interface` modifier, which is considered a pseudo-modifier in the Java language.</sub>

The `Class` class contains a method for determining modifiers:

* `int getModifiers()` - returns the Java language modifiers `public`, `protected`, `private`, `final`, `static`, `abstract`, and `interface` for this `Class` object, encoded in an integer

```
Class<?> clazz = Integer.class;
assertEquals(0b00000000_00010001, clazz.getModifiers());
```

Besides modifiers encoded in an integer, the `Class` class contains methods for determining the kind of type (some of the methods simply reuse the appropriate modifier bits, others use _native_ methods of this class):

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

The `java.lang.reflect.Modifier` class is a utility class with static methods for decoding the Java language modifiers for classes, interfaces, fields, methods, constructors, and parameters.

![methods of the Modifier class](/images/part1/methods_of_the_Modifier_class.png)

The first group of methods of the `Modifier` class is to check for the presence of individual modifiers in the specified modifiers parameter:

* `static boolean isAbstract(int mod)`
* `static boolean isFinal(int mod)`
* `static boolean isInterface(int mod)`
* `static boolean isNative(int mod)`
* `static boolean isPrivate(int mod)`
* `static boolean isProtected(int mod)`
* `static boolean isPublic(int mod)`
* `static boolean isStatic(int mod)`
* `static boolean isStrict(int mod)`
* `static boolean isSynchronized(int mod)`
* `static boolean isTransient(int mod)`
* `static boolean isVolatile(int mod)`

```
Class<?> clazz = Integer.class;
int modifiers = clazz.getModifiers();

assertTrue(Modifier.isPublic(modifiers));
assertTrue(Modifier.isFinal(modifiers));

assertEquals("public final", Modifier.toString(modifiers));
```

The second group of methods of the `Modifier` class is for getting all possible modifiers that can be applied to classes, interfaces, fields, methods, constructors, and parameters:

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

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/modifiers/code_examples.md)

## Primitives

Nine predefined `Class` objects represent the eight primitive types (`boolean`, `byte`, `char`, `short`, `int`, `long`, `float`, `double`) and pseudo-type `void`.

The `Class` class contains a method for determining primitives:

* `boolean isPrimitive()` - returns `true`, if and only if this `Class` object represents a primitive type or pseudo-type `void`

```
assertTrue(int.class.isPrimitive());
assertTrue(Boolean.TYPE.isPrimitive());
```

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/primitives/code_examples.md)

## Arrays

Arrays are objects that can contain several variables (array components) that are referenced by non-negative integer index values. The array components may themselves be arrays. The array components of the lowest nested array that are not themselves arrays are called array elements.

<sub>For example, for an array of type `int[][][]`, the <em>component type</em> is `int[][]`, and the <em>element type</em> is `int`.</sub>

Every array has an associated `Class` object, shared with all other arrays with the same component type. Although an array type is not a class, the `Class` object of every array acts as if:

* the direct superclass of every array type is the `Object` class
* every array type implements the `Cloneable` and `Serializable` interfaces

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

The `java.lang.reflect.Array` class is a utility class with static methods for dynamically creating new arrays and getting and setting array components.

![methods of the Array class](/images/part1/methods_of_the_Array_class.png)

The `Array` class declares the following methods:

* `static int getLength(Object array)` - returns the length of the specified array object
* `static Object get(Object array, int index)` - returns the value of the indexed component in the specified array object <sup>(*)</sup>
* `static void set(Object array, int index, Object value)` - sets the value of the indexed component of the specified array object to the specified new value <sup>(*)</sup>
* `static Object newInstance(Class<?> componentType, int length)` - creates a new one-dimensional array with the specified component type and length
* `static Object newInstance(Class<?> componentType, int... dimensions)` - creates a new multi-dimentional array with the specified component type, dimensions, and lengths

<sub>(*) The `Array` class also declares similar methods for getting and setting components for `boolean`, `byte`, `char`, `short`, `int`, `long`, `float`, `double` arrays.</sub>

```
Object array = Array.newInstance(int.class, 1);
assertTrue(array.getClass().isArray());

assertEquals(1, Array.getLength(array));

assertEquals(0, Array.getInt(array, 0));
Array.setInt(array, 0, 2);
assertEquals(2, Array.getInt(array, 0));
```

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/arrays/code_examples.md)

## Inheritance

The class `Class` has three groups of methods for analyzing inheritance between types.

The first subgroup of these methods is for determining the direct superclass and direct superinterfaces (that is, which class and interfaces are mentioned in the `extends` and `implements` clauses of a class or interface declaration):

* `Class<? super T> getSuperclass()` - returns the `Class` representing the direct superclass of the type represented by this `Class` object
* `Class<?>[] getInterfaces()` - returns an array of `Class` objects representing the direct superinterfaces of the type represented by this `Class` object

>The `getInterfaces` method does not return all interfaces implemented by a class (or extended by an interface), but only the direct superinterfaces mentioned in the `implements` clause of the class declaration (or the `extends` clause of the interface declaration).

```
assertEquals(AbstractList.class, ArrayList.class.getSuperclass());

assertArrayEquals(
   new Class[]{List.class, RandomAccess.class, Cloneable.class, Serializable.class},
   ArrayList.class.getInterfaces()
);
```

The second subgroup of these methods is for determining whether one type can be cast to another type (that is, whether one type is the same or a subtype of another type):

* `boolean isInstance(Object obj)` - returns `true`, if and only if the specified `Object` parameter is non-null and can be cast to the type represented by this `Class` object
* `boolean isAssignableFrom(Class<?> cls)` - returns `true`, if and only if the specified `Class` parameter is either the same type as, or is a subtype represented by this `Class` object

>The `isInstance` method is the runtime equivalent of the compile-time `instanceof` operator.

```
assertTrue(Number.class.isInstance(Integer.valueOf(1)));
```

```
assertTrue(Number.class.isAssignableFrom(Integer.class));
```

The third subgroup of these methods is for casting one type to another type:

* `T cast(Object obj)` - casts the specified `Object` parameter to the type represented by this `Class` object
* `<U> Class<? extends U> asSubclass(Class<U> cls)` - casts this `Class` object to a subclass of the class represented by the specified `Class` parameter

>The `cast` method is the runtime equivalent of the compile-time _cast operator_.

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

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/inheritance/code_examples.md)

## Special classes and interfaces

The `Class` class has methods suitable for analyzing special kinds of types: types of different nesting (_top-level_ and _nested_ classes and interfaces), restricted types (_enums_, _records_, _sealed_ classes and interfaces), and types with special implementation (_synthetic_ and _hidden_ classes and interfaces).

### Nested classes and interfaces

A _top-level class_ is a class declared directly in a compilation unit. A _nested class_ is a class declared within the body of another class or interface declaration.

A _nested class_ may be a member class, a local class, or an anonymous class. Some kinds of nested classes are _inner classes_, which are classes that can refer to enclosing class instances and local variables.

A _member class_ is a class whose declaration is directly enclosed in the body of another class or interface declaration. A member class may be a normal class, an enum class, or a record class. Member classes may be _static_, in which case they have no access to the instance variables of the enclosing class; or they may be _inner classes_.

An _inner class_ is a nested class that is not explicitly or implicitly _static_. Inner classes have access to other members of the enclosing class, even if they are declared _private_. An inner class is one of the following:

* a member class that is not explicitly or implicitly _static_ (a member enum class, a member record class, or a member class of an interface are implicitly _static_, so they are not inner classes)
* a local class that is not implicitly static (a local enum class or a local record class are implicitly _static_, so they are not inner classes)
* an anonymous class

A _local class_ is a class declared within a _block_. A local class may be a normal class, an enum class, or a record class. Every local normal class is an inner class. Every local enum class and local record class is implicitly _static_ and therefore not an inner class.

<sub>A local class is not a member of any class or interface.</sub>

An _anonymous class_ has no name and is implicitly declared by an instance creation expression (the `new` operator) _or_ by an enum constant with a class body. An anonymous class is always an inner class.

<sub>An anonymous class is not a member of any class or interface.</sub>

>The optional class body of an enum constant implicitly declares an anonymous class.

The class `Class` has four groups of methods for analyzing nested classes and interfaces:

##### Kinds of nested classes

The first subgroup of these methods is for determining the kind of nested classes:

* `boolean isMemberClass()` - returns `true`, if and only if this class is a member class
* `boolean isLocalClass()` -  returns `true`, if and only if this class is a local class
* `boolean isAnonymousClass()` - returns `true`, if and only this class is an anonymous class

##### Public vs. declared members

The second subgroup of these methods is for getting member classes and interfaces:

* `Class<?>[] getClasses()` - returns an array containing `Class` objects representing all the public classes and interfaces that are members of the class represented by this `Class` object (this includes public class and interface members inherited from superclasses and public class and interface members declared by the class)
* `Class<?>[] getDeclaredClasses()` - returns an array of `Class` objects reflecting all the classes and interfaces declared as members of the class represented by this `Class` object (this includes public, protected, package, and private classes and interfaces declared by the class, but excludes inherited classes and interfaces)

##### Enclosing vs. declaring context

The third subgroup of these methods is for determining enclosing context (method, constructor, or class) for local and anonymous classes and declaring context for member classes:

* `Method getEnclosingMethod()` - returns a `Method` object representing the _immediately enclosing method_ of the underlying class, if this `Class` object represents a local or anonymous class within a method
* `Constructor<?> getEnclosingConstructor()` - returns a `Constructor` object representing the _immediately enclosing constructor_ of the underlying class, if this `Class` object represents a local or anonymous class within a constructor
* `Class<?> getEnclosingClass()` - returns the _immediately enclosing class_ of the underlying class, if the underlying class is a nested class
* `Class<?> getDeclaringClass()` - if the class represented by this `Class` object is a member of another class, returns the `Class` object representing the class in which it was declared

##### Nests

Nest-based access control (since Java 11) is a new implementation of nested classes that allows them to access other private members without the Java compiler having to insert _synthetic_ accessibility-broadening bridge methods.

A top-level class, plus all classes nested within it, are forming a nest. All classes in a nest allow mutual access to their _private_ members. Every class belongs to exactly one nest.  A top-level class that forms the nest is described as a nest host. Two classes in a nest are described as nestmates.

The fourth subgroup of these methods is for determining nests:

* `Class<?> getNestHost()` - returns the nest host of the nest to which this class represented by this `Class` object belongs
* `boolean isNestmateOf(Class<?> class)` - determines if the given `Class` is a nestmate of the class or interface represented by this Class object
* `Class<?>[] getNestMembers()` - returns an array containing `Class` objects representing all the classes and interfaces that are members of the nest to which the class represented by this `Class` object belongs (the zeroth element of the returned array is the nest host)

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/nested/code_examples.md)

### Enum classes

Enum classes (since Java 5.0) are restricted kinds of classes that define a fixed set of named class instances (enum constants).

<sub>Enum classes implicitly extend the `Enum` class.</sub>

The `Class` class declares the methods for determining enum classes:

* `boolean isEnum()` - returns `true`, if and only if this class is an enum class
* `T[] getEnumConstants()` - returns an array containing the enum constants comprising the enum class represented by this `Class` object (in the order they are declared), or `null` if this class is not an enum class

>Notice that if an enum constant is declared with a class body, the class of that enum constant is an anonymous class and not the class of the declaring enum class.

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/enums/code_examples.md)

### Synthetic classes and interfaces

Synthetic classes and interfaces (since Java 1.3) are generated by the Java compiler and are not explicitly or implicitly declared.

<sub><em>Explicit</em> constructs are explicitly declared in the source code.</sub>

<sub><em>Implicit</em> constructs are not explicitly declared in the source code, but their presence is mandated by the Java language specification.</sub>

<sub><em>Synthetic</em> constructs are neither implicitly nor explicitly declared and are generated by a Java compiler in a manner that is not specified by the Java language specification.</sub>

The `Class` class declares the method for determining synthetic classes:

* `boolean isSynthetic()` - returns `true`, if and only if this class was generated by the Java compiler

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/synthetic/code_examples.md)

### Hidden classes and interfaces

Hidden classes (since Java 15) are a new implementation of runtime generated classes that are not visible to other classes either at compile-time or runtime by class loaders. A hidden class is created by invoking the `java.lang.invoke.Lookup::defineHiddenClass` method with the _class_ file as a byte array argument.

<sub>All kinds of classes, including enum classes and record classes, may be hidden classes; all kinds of interfaces, including annotation interfaces, may be hidden interfaces.</sub>

>Hidden classes replaced the anonymous classes created by the `sun.misc.Unsafe::defineAnonymousClass` method that was deprecated in Java 15 and removed in Java 17.

The `Class` class declares the method for determining hidden classes:

* `boolean isHidden()` - returns `true`, if and only if this class is a hidden class or interface

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/hidden/code_examples.md)

### Record classes

Record classes (previewed in Java 14 and released in Java 16) are restricted kinds of classes that define a simple aggregate of values. From record components in a header of a record class declaration, the Java compiler created the following _implicit_ constructs:

* a `private final` field (for each component in the header)
* a `public` accessor method (for each component in the header)
* a canonical constructor whose signature is the same as the record header
* `public final` methods `equals`, `hashCode` and `toString`

<sub>Record classes are `final` and implicitly extend the `Record` class.</sub>

The `Class` class declares the methods for determining record classes:

* `boolean isRecord()` - returns `true`, if and only if this class is a record class
* `RecordComponent[] getRecordComponents()` - returns an array of `RecordComponent` objects representing all the record components of this record class (in the same order that they are declared in the record header), or `null` if this class is not a record class

The `RecordComponent` class declares the methods for reading information about a record component (along with methods to read annotations and generic type):

* `Class<?> getDeclaringRecord()` - returns the record class which declares this record component
* `String getName()` - returns the name of this record component
* `Class<?> getType()` - returns a `Class` that identifies the declared type for this record component
* `Method getAccessor()` - returns a `Method` that represents the accessor method for this record component

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/records/code_examples.md)

### Sealed classes and interfaces

Sealed classes and interfaces (previewed in Java 15 and released in Java 17) restrict which other classes or interfaces may extend or implement them.

A sealed class has the `sealed` modifier to its declaration and the `permits` clause that specifies the classes that may extend the sealed class. Permitted subclasses must directly extend the sealed class and must have exactly one of the following modifiers to describe how it continues the sealing initiated by its superclass:

* `final` - cannot be extended further
* `sealed` - can only be extended by its permitted subclasses
* `non-sealed` - can be extended by unknown subclasses

The `Class` class declares the methods to work with sealed classes and interfaces:

* `boolean isSealed()` - returns `true`, if and only if this class is a sealed class or interface
* `Class<?>[] getPermittedSubclasses()` - returns an array of `Class` objects of the directly permitted subclasses and subinterfaces of this class or interface, or `null` if this class or interface is not sealed

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part1/sealed/code_examples.md)

## Conclusion

The `Class` class is the entry point of the Core Reflection API. Once a `Class` object is obtained, you can determine many of the internals of the type: the kind of type (primitive type, array type, class or interface), modifiers, specification and implementation details.

Not always, the structures in the source code have a one-to-one correspondence with the structures in the generated _class_ file. In some cases reflection reveals that the generated _class_ file has _implicit_ constructs (absent in the source code but mandated by the Java language specification) or _synthetic_ constructs (generated by a Java compiler in a manner that is not specified by the Java language specification).

Complete code examples are available in the [GitHub repository](https://github.com/aliakh/demo-java-reflection).
