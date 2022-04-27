## Arrays


##### Code examples

The `Class::isArray` method determines an array, the `Class::componentType` method returns the array’s _component type_, and the `Class::arrayType` method returns the array’s _array type_.


```
Class<?> clazz = Integer[].class;
assertTrue(clazz.isArray());

assertEquals(Integer.class, clazz.componentType());
assertEquals(Integer[][].class, clazz.arrayType());
```



### The Array class


##### Code examples

The `Array::getLength` method returns the length of the array, the `Array::get` and `Array::set` methods read and write the _array components_.


```
Integer[] array = new Integer[]{2};
assertEquals(1, Array.getLength(array));

assertEquals(2, Array.getInt(array, 0));
Array.setInt(array, 0, 3);
assertEquals(3, Array.getInt(array, 0));
```


The `Array::newInstance(Class&lt;?> componentType, int length)` method creates a one-dimensional array of length 2.


```
Object array = Array.newInstance(int.class, 2);
assertTrue(array.getClass().isArray());

assertArrayEquals(new int[]{0, 0}, (int[]) array);
```


The `Array::newInstance(Class&lt;?> componentType, int... dimensions)` method creates a three-dimensional array with dimension lengths 1, 2, 3.


```
Object array = Array.newInstance(int.class, 1, 2, 3);
assertTrue(array.getClass().isArray());

assertArrayEquals(
   new int[][][]{
       new int[][]{
           new int[]{0, 0, 0},
           new int[]{0, 0, 0}
       },
   },
   (int[][][]) array
);
