## Inheritance


##### Code examples

The `Class::getSuperclass` method gets the _direct superclass_ of the `ArrayList` class (the `AbstractList` class), the `getInterfaces` method gets _direct superinterfaces_ of this class (the `List`, `RandomAccess`, `Cloneable`, `Serializable` interfaces, but not the `Iterable` and `Collection` interfaces).


```
assertEquals(AbstractList.class, ArrayList.class.getSuperclass());
assertArrayEquals(
   new Class[]{List.class, RandomAccess.class, Cloneable.class, Serializable.class},
   ArrayList.class.getInterfaces()
);
```


The `Class::getSuperclass` method gets the _direct superclass_ of an array type (the `Object` class), the `getInterfaces` method gets _direct superinterfaces_ of an array type (the `Cloneable` and `Serializable` interfaces).


```
assertEquals(Object.class, int[].class.getSuperclass()); // any array
assertArrayEquals(new Class[]{Cloneable.class, Serializable.class}, int[].class.getInterfaces()); // any array
```


The `isInstance` method checks that an object argument is either an instance of the specified class (or of any of its subclasses). For comparison, a similar logic is shown implemented by the `instanceof` operator.


```
assertTrue(ArrayList.class.isInstance(new ArrayList()));
assertTrue(Object.class.isInstance(new ArrayList()));
assertFalse(int.class.isInstance(1)); // always false for primitives

assertTrue(new ArrayList() instanceof ArrayList);
assertTrue(new ArrayList() instanceof Object);
// assertTrue(1 instanceof int.class);
```


The `isAssignableFrom` method checks that a class argument is either the same as the specified class, or is a some of its subclasses.


```
assertTrue(ArrayList.class.isAssignableFrom(ArrayList.class));
assertTrue(Object.class.isAssignableFrom(ArrayList.class));
assertTrue(int.class.isAssignableFrom(int.class));
```


The `Class::cast` method performs a narrowing reference conversion (downcast) of an _object_.


```
Object object = Integer.valueOf(1);
Integer integer = Integer.class.cast(object); 
assertEquals(1, integer);
```


Notice the simplified source code of the `Class::cast` method:


```
public T cast(Object obj) {
   if (obj != null && !isInstance(obj))
       throw new ClassCastException();
   return (T) obj;
}
```


The `Class::asSubclass` method performs a narrowing reference conversion (downcast) of a _class_.


```
Number number = Integer.valueOf(1);
Class<? extends Number> numberClass = number.getClass();
Class<? extends Integer> integerClass = number.getClass().asSubclass(Integer.class);
```


Notice the simplified source code of the `Class::asSubclass` method:


```
public <U> Class<? extends U> asSubclass(Class<U> cls) {
   if (cls.isAssignableFrom(this))
       return (Class<? extends U>) this;
   else
       throw new ClassCastException();
}
