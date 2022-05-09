# Java Reflection, part 2: fields, methods, constructors


## Introduction

Reflection is the ability of an application to examine and modify its structure and behavior at runtime. The ability to _introspect structure_ consists in the presence of the Core Reflection API for reading classes and their fields, methods, constructors, member classes and member interfaces. The ability to _modify behavior_ consists in the presence of this API for getting and setting field values, invoking methods, and creating new instances using constructors.

The `Member` interface and its implementations - the `Field`, `Method`, and `Constructor` classes represent reflected fields and methods (which are members of classes and interfaces, according to the Java Language Specification) and constructors (which are not members).

<sub>This article is based on the Java 17 implementation in Oracle OpenJDK.</sub>


## Discovery members and constructors

According to the Java Language Specification, class members include fields, methods, member classes and member interfaces. A class can declare members in its body or inherit them from its superclass and superinterfaces. Notice that constructors are not class members and therefore are not inherited.

The entry point for the Core Reflection API is the `Class` class. Among other methods, this class has the following methods for discovering fields, methods, constructors, member classes and member interfaces of the given type:

![methods of the Class class](/images/part2/methods_of_the_Class_class.png)

Methods grouped by one category, return either an array of all constructs (members and constructors) or a single construct by its name and/or parameter types.

Methods grouped by another category, return constructs by their accessibility and declaration. Methods that contain the fragment "declared" in their name, return all the declared constructs of the class or interface, including _public_, _protected_, _package access_, and _private_ constructs, but excluding inherited constructs. Methods that do not contain this fragment, return all the _public_ constructs of the class or interface, including those declared by the class or interface and those inherited (except constructors) from superclasses and superinterfaces.

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/discovery/code_examples.md)


## Using members and constructors

The `Member` interface and its implementations have the following type hierarchy, which represents reflected fields, methods, and constructors.

![reflection members hierarchy](/images/part2/reflection_members_hierarchy.png)

Additionally there is the `Parameter` class that represents reflected parameters of methods and constructors.


### The Member interface

The `Member` interface is a superinterface for the `Field`, `Method`, and `Constructor` classes.

This interface declares the following methods:



* `Class&lt;?> getDeclaringClass()` - returns the `Class` object representing the class or interface that declares the member or constructor
* `String getName()` - returns the simple name of the reflected member or constructor
* `int getModifiers()` - returns the Java language modifiers for the member or constructor
* `boolean isSynthetic()` - returns `true` if and only if this member or constructor was introduced by the Java compiler

[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/member/code_examples.md)


### The AccessibleObject class

The `AccessibleObject` class is an abstract superclass of the `Field`, `Method`, and `Constructor` classes that allows suppressing checks for Java language access control.

Java language access control is tightly connected with two concepts of the Java Platform Module System (introduced in Java 9): _readability_ and _accessibility_. Readability is the basis of _reliable configuration_ and specifies that the caller module can be guaranteed to read types in the target module. Readability is established using the `requires` directive in the caller module. Accessibility is the basis of _strong encapsulation_ and specifies what packages (and _public_ types in them) the target module exposes to the caller module. Accessibility is established using the `exports` directive in the target module.


```
module caller.module {
    requires target.module; // for readability
}
```





```
module target.module {
    exports target.package to caller.module; // for accessibility
}
```


Here are some methods that this class declares:



* `boolean canAccess(Object obj)` - returns `true` if and only if the caller can access this reflected object
* `void setAccessible(boolean flag)` - sets the `accessible` flag for this reflected object to the given boolean value and throws `InaccessibleObjectException` if the access control cannot be suppressed
* `boolean trySetAccessible()` - sets the `accessible` flag for this reflected object to `true` and returns the `true` if the access control is suppressed and `false` otherwise

By default, Java language access control allows the use of:



* _private_ members only inside their top-level class
* _package_ members only inside their package
* _protected_ members only inside their package or subclasses
* _public_ members outside their module only if they are declared in an _exported_ package, and the caller module _requires_ their module

Java language access control can be always suppressed by setting the `accessible` flag to `true` if the caller class and the target class are in the same module. If they are in different modules, the access control can be suppressed only if any of the following conditions are met:



* the target class is _public_, the member is _public_, and the target module use the `exports` directive from the target package to the caller module
* the target class is _public_, the member is _protected static_, the caller class is a subclass of the target class, and the target module use the `exports` directive from the target package to the caller module
* the target module use the `opens` directive from the target package to the caller module
* the target class is in an _unnamed_ or _open_ module

_Shallow reflection_ is access without using the `accessible` flag. Only _public_ members of _public_ classes in exported packages can be accessed. The `exports` directive grants the package access at compile-time and for shallow reflection at runtime. _Deep reflection_ is access with setting the `accessible` flag to `true`. Any members of any classes in any package (whether exported or not) can be accessed. The `opens` directive grants the package access for deep reflection at runtime.


```
module target.module {
    exports target.package to caller.module; // to access public members of public classes
    opens target.package to caller.module; // to access any members of any classes
}
```


[code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/accessibleobject/code_examples.md)


### The Field class

The `Field` class represents a reflected static or an instance field. The class has methods for reading information about the field (name, modifiers, and type) and for getting and setting field values for a given object.

Here are some methods that this class declares:



* `Class&lt;?> getType()` - returns a `Class` object that identifies the declared type for the field
* `Object get(Object obj)` - returns the value of the field on the specified object <sup><code>(*)</code></sup>
* `void set(Object obj, Object value)` - sets the field to the specified new value on the specified object <sup><code>(*)</code></sup>

<sub>(*) The `Field` class also declares similar methods for getting and setting values for `boolean`, `byte`, `char`, `short`, `int`, `long`, `float`, `double` fields.</sub>


##### Code examples

The example class contains a field with a known name, modifiers, and a type.


```
class SomeClass {
    public int someField;
}
```


The following code uses methods of the `Field` class for a _public_ instance field. To access a _private_ field, you should preliminarily execute the statement `field.setAccessible(true)`. To access a _static_ field, you should use `null` instead of an instance object.


```
Object object = new SomeClass();
Class<?> clazz = object.getClass();
Field field = clazz.getDeclaredField("someField");

// methods inherited from the AccessibleObject class
assertTrue(field.canAccess(object));

// methods inherited from the Member class
assertEquals(SomeClass.class, field.getDeclaringClass());
assertEquals("someField", field.getName());
assertEquals("public", Modifier.toString(field.getModifiers()));
assertFalse(field.isSynthetic());

// methods declared in the Field class
assertEquals(int.class, field.getType());

assertEquals(0, field.getInt(object));
field.set(object, 1);
assertEquals(1, field.getInt(object));
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/field/code_examples.md)


### The Executable class

The `Executable` class is an abstract superclass of the `Method` and `Constructor` classes that declares their shared functionality: parameter types and thrown exception types.

Here are some methods that this class declares:



* `int getParameterCount()` - returns the number of formal parameters (whether explicitly declared or implicitly declared or neither)
* `Parameter[] getParameters()` - returns an array of `Parameter` objects representing all the parameters
* `abstract Class&lt;?>[] getParameterTypes()` - returns an array of `Class` objects that represent the formal parameter types
* `abstract Class&lt;?>[] getExceptionTypes()` - returns an array of `Class` objects that represent the types of thrown exceptions
* `boolean isVarArgs()` - returns `true` if and only if this method is declared to take a variable number of arguments


### The Method class

The `Method` class represents a reflected static or an instance method. The class has methods for reading information about the method (name, modifiers, parameter types, return type, and thrown exception types) and invoking the method for a given object.

Here are some methods that this class declares:



* `Class&lt;?> getReturnType()` - returns a `Class` object that represents the formal return type of the method
* `Object invoke(Object obj, Object... args)` - invokes the reflected method represented by this `Method` object on the specified object with the specified parameters
* `boolean isBridge()` - returns `true` if and only if this method is a bridge method
* `boolean isDefault()` - returns `true` if and only if this method is a _default_ method

<sub>If a reflected method call via the `invoke` method throws an exception, it will be wrapped in an `InvocationTargetException`.</sub>


##### Code examples

The example class contains a method with a known name, modifiers, a parameter, a return type, and a thrown exception.


```
class SomeClass {
    public int someMethod(int someParameter) throws RuntimeException {
        return someParameter;
    }
}
```


The following code uses methods of the `Method` class for a _public_ instance method. To access a _private_ method, you should preliminarily execute the statement `method.setAccessible(true)`. To access a _static_ method, you should use `null` instead of an instance object.


```
Object object = new SomeClass();
Class<?> clazz = object.getClass();
Method method = clazz.getDeclaredMethod("someMethod", int.class);

// methods inherited from the AccessibleObject class
assertTrue(method.canAccess(object));

// methods inherited from the Member class
assertEquals(SomeClass.class, method.getDeclaringClass());
assertEquals("someMethod", method.getName());
assertEquals("public", Modifier.toString(method.getModifiers()));
assertFalse(method.isSynthetic());

// methods inherited from the Executable class
assertEquals(1, method.getParameterCount());
assertArrayEquals(new Class[]{int.class}, method.getParameterTypes());
assertArrayEquals(new Class[]{RuntimeException.class}, method.getExceptionTypes());
assertFalse(method.isVarArgs());

// methods declared in the Method class
assertEquals(int.class, method.getReturnType());
assertFalse(method.isBridge());
assertFalse(method.isDefault());

assertEquals(1, method.invoke(object, 1));
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/method/code_examples.md)


### The Constructor class

The `Constructor&lt;T>` class (where `T` is the class in which the constructor is declared) represents a reflected constructor. The class has methods for reading information about the constructor (name, modifiers, parameter types, and thrown exception types) and creating new instances.

Here is a method that this class declares:



* `T newInstance(Object... args)` - creates and initializes a new instance of the declaring class using the reflected constructor with the specified parameters

<sub>If a reflected constructor call via the `newInstance` method throws an exception, it will be wrapped in an `InvocationTargetException`.</sub>

To create new instances, in addition to the `Constructor::newInstance` method, there is also the `Class::newInstance()` method. However, the latter has several limitations (can only invoke a no-argument constructor, can propagate _checked_ exceptions thrown by the reflected constructor, can not bypass the access control) and has been deprecated since Java 9.


##### Code examples

The example class contains a constructor with a known name, modifiers, a parameter, and a thrown exception.


```
class SomeClass {
    public SomeClass(int someParameter) throws RuntimeException {
    }
}
```


The following code uses methods of the `Constructor` class on a _public_ constructor. To access a _private_ constructor, you should preliminarily execute the statement `constructor.setAccessible(true)`.


```
Class<SomeClass> clazz = SomeClass.class;
Constructor<SomeClass> constructor = clazz.getDeclaredConstructor(int.class);

// methods inherited from the AccessibleObject class
assertTrue(constructor.canAccess(null));

// methods inherited from the Member class
assertEquals(SomeClass.class, constructor.getDeclaringClass());
assertEquals("demo.SomeClass", constructor.getName());
assertEquals("public", Modifier.toString(constructor.getModifiers()));
assertFalse(constructor.isSynthetic());

// methods inherited from the Executable class
assertEquals(1, constructor.getParameterCount());
assertArrayEquals(new Class[]{int.class}, constructor.getParameterTypes());
assertArrayEquals(new Class[]{RuntimeException.class}, constructor.getExceptionTypes());
assertFalse(constructor.isVarArgs());

// methods declared in the Constructor class
SomeClass object = constructor.newInstance(1);
assertNotNull(object);
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/constructor/code_examples.md)


### The Parameter class

The `Parameter` class represents a reflected parameter of a method or constructor. The class has methods to read information about the parameter (name, modifiers, and type).

Here are some methods that this class declares:



* `Executable getDeclaringExecutable()` - returns the `Executable` declaring this parameter
* `boolean isNamePresent()` - returns `true` if and only if the parameter has a name according to the _class_ file
* `String getName()` - returns the name of the parameter
* `int getModifiers()` - returns the Java language modifiers for the parameter
* `Class&lt;?> getType()` - returns a `Class` object that identifies the declared type for the parameter
* `boolean isSynthetic()` - returns `true` if and only if this parameter is neither explicitly nor implicitly declared in the source code
* `boolean isVarArgs()` - returns `true` if and only if this method is declared to take a variable number of arguments
* `boolean isImplicit()` - returns `true` if and only if this parameter is implicitly declared in the source code

If the parameter name is present in the _class_ file, then the `getName` method returns the actual parameter name. Otherwise, this method synthesizes the name in the form _argN_, where _N_ is the index of the parameter. Notice that _class_ files do not store actual parameter names by default (mainly because larger _class_ files can use more memory in the Java VM). To store parameter names in _class_ files, the source Java files should be compiled with the _-parameters_ option.


##### Code examples

The example class contains a constructor with one parameter with a known name, modifiers, and type.


```
class SomeClass {
    private SomeClass(int someParameter) {
    }
}
```


The following code uses methods of the `Parameter` class for the constructor parameter.


```
Class<?> clazz = SomeClass.class;
Constructor<?> constructor = clazz.getDeclaredConstructor(int.class);

Parameter[] parameters = constructor.getParameters();
assertEquals(1, parameters.length);
Parameter parameter = parameters[0];

// methods declared in the Parameter class
assertEquals(constructor, parameter.getDeclaringExecutable());
assertFalse(parameter.isNamePresent());
assertEquals("arg0", parameter.getName());
assertEquals("", Modifier.toString(parameter.getModifiers()));
assertEquals(int.class, parameter.getType());
```


[more code examples](https://github.com/aliakh/demo-java-reflection/blob/master/src/test/java/demo/part2/parameter/code_examples.md)


## Special classes

Sometimes reflection reveals constructs that are not explicitly declared in the source code. Some of these are _implicit_ constructs whose presence is mandated by the Java Language Specification (for example, default no-argument constructors). Others are _synthetic_ constructs that are neither explicitly nor implicitly declared in the source code but generated by the Java compiler (for example, bridge methods). This is because some features of the Java platform are implemented in the overlying Java language layer to make the underlying Java VM layer more simple and versatile. Examples of such features include (but are not limited to) nested classes and interfaces, record classes, and enum classes.


### Nested classes and interfaces

Nested classes are classes declared within the body of another class or interface declaration. Nested classes may be static or non-static member classes, local classes, or anonymous classes. Some kinds of nested classes that are not explicitly or implicitly _static_ (and therefore can refer to instances of the enclosing classes) are inner classes.


##### Code examples

The example classes are a top-level class with a non-static member class.


```
class OuterClass {
    class InnerClass {
    }
}
```


The following code shows which synthetic field (a reference to an instance of the enclosing class) and constructor (to set that field) the Java compiler generates for that inner class.


```
Class<?> clazz = SomeOuterClass.SomeInnerClass.class;

Field[] fields = clazz.getDeclaredFields();
assertEquals(1, fields.length);
Field field = fields[0];
assertEquals("/* synthetic */ final demo.SomeOuterClass demo.SomeOuterClass$SomeInnerClass.this$0", toString(field));

Constructor<?>[] constructors = clazz.getDeclaredConstructors();
assertEquals(1, constructors.length);
Constructor<?> constructor = constructors[0];
assertEquals("demo.SomeOuterClass$SomeInnerClass(demo.SomeOuterClass)", toString(constructor));
```


So these are the actual classes that the Java compiler generates from that non-static member class:


```
class SomeOuterClass {

    class SomeInnerClass {
        /* synthetic */ final SomeOuterClass this$0;

        SomeInnerClass(SomeOuterClass this$0) {
            this.this$0 = this$0;
        }
    }
}
```



### Record classes

Record classes are restricted kinds of classes that define an immutable aggregate of values. From record components in the record header, the Java compiler created the following implicit constructs:



* a _private final_ field and a _public_ accessor method (for each record component in the record header)
* a canonical constructor whose signature is the same as the record header
* _public final_ methods `equals`, `hashCode` and `toString`


##### Code examples

The example class is a record class with one record component.


```
record SomeRecord(int i) {
}
```


The following code shows which implicit fields, methods, and constructors the Java compiler generates for the record class.


```
Class<?> clazz = SomeRecord.class;
assertTrue(Modifier.isFinal(clazz.getModifiers()));
assertEquals(Record.class, clazz.getSuperclass());

Field[] fields = clazz.getDeclaredFields();
assertEquals(1, fields.length);
Field field = fields[0];
assertEquals("private final int demo.SomeRecord.i", toString(field));

Method[] methods = clazz.getDeclaredMethods();
assertEquals(
    Set.of(
        "public int demo.SomeRecord.i()",
        "public final boolean demo.SomeRecord.equals(java.lang.Object)",
        "public final int demo.SomeRecord.hashCode()",
        "public final java.lang.String demo.SomeRecord.toString()"
    ),
    toStringSet(methods)
);

Constructor<?>[] constructors = clazz.getDeclaredConstructors();
assertEquals(1, constructors.length);
Constructor<?> constructor = constructors[0];
assertEquals("demo.SomeRecord(int)", toString(constructor));
```


So this is the actual class that the Java compiler generates from this record class:


```
final class SomeRecord extends Record {

    private final int i;

    SomeRecord(int i) {
        this.i = i;
    }

    public int i() {
        return this.i;
    }

    @Override
    public final boolean equals(Object obj) {...}

    @Override
    public final int hashCode() {...}

    @Override
    public final String toString() {...}
}
```



### Enum classes

Enum classes are restricted kinds of classes that define a fixed set of named class instances (enum constants).


##### Code examples

The example class is an enum class with one enum constant without a class body.


```
enum SomeEnum {
    INSTANCE
}
```


The following code shows which implicit and synthetic fields, methods, and constructors the Java compiler generates for the enum class.


```
Class<?> clazz = SomeEnum.class;
assertEquals(Enum.class, clazz.getSuperclass());

Field[] fields = clazz.getDeclaredFields();
assertEquals(
    Set.of(
        "public static final demo.SomeEnum demo.SomeEnum.INSTANCE",
        "/* synthetic */ private static final demo.SomeEnum[] demo.SomeEnum.$VALUES"
    ),
    toStringSet(fields)
);


Method[] methods = clazz.getDeclaredMethods();
assertEquals(
    Set.of(
        "public static demo.SomeEnum[] demo.SomeEnum.values()",
        "public static demo.SomeEnum demo.SomeEnum.valueOf(java.lang.String)",
        "/* synthetic */ private static demo.SomeEnum[] demo.SomeEnum.$values()"
    ),
    toStringSet(methods)
);


Constructor<?>[] constructors = clazz.getDeclaredConstructors();
assertEquals(1, constructors.length);
Constructor<?> constructor = constructors[0];
assertEquals("private demo.SomeEnum(java.lang.String,int)", toString(constructor));
```


So this is the actual class that the Java compiler generates from this enum class:


```
final class SomeEnum extends Enum<SomeEnum> {

    public static final SomeEnum INSTANCE = new SomeEnum("INSTANCE", 0);
    /* synthetic */ private static final SomeEnum[] $VALUES;

    public static SomeEnum[] values() {
        return (SomeEnum[])$VALUES.clone();
    }

    public static SomeEnum valueOf(String name) {
        return Enum.valueOf(SomeEnum.class, name);
    }

    private SomeEnum(String string, int n) {
        super(string, n);
    }

    /* synthetic */ private static SomeEnum[] $values() {
        return new SomeEnum[]{INSTANCE};
    }

    static {
        $VALUES = SomeEnum.$values();
    }
}
```



## Conclusion

The `Class` class is the entry point of the Core Reflection API. Once you get the `Class` object for a class or interface, you can discover and use its fields, methods, constructors, member classes and member interfaces.

In typical cases, reflective access for members and constructors consists of getting and setting field values in various serializing/deserializing operations, bypassing access control for fields and methods in legacy code, and creating new instances at runtime when the exact class is unknown at compile time.

Complete code examples are available in the [GitHub repository](https://github.com/aliakh/demo-java-reflection).
