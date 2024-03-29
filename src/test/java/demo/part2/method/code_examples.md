### The Method class


##### Code examples

The following code uses the `Method::isVarArgs` method to distinguish between a method with an _array_ parameter and a method with a _variable arguments_ parameter (as these methods return the same array type as a result of the `getParameterTypes` method).


```
class SomeClass {
    public void methodWithArrayParameter(int[] someParameter) {}
    public void methodWithVarArgsParameter(int... someParameter) {}
}
```





```
Class<?> clazz = SomeClass.class;

Method methodWithArrayParameter = clazz.getDeclaredMethod("methodWithArrayParameter", int[].class);
assertArrayEquals(new Class[]{int[].class}, methodWithArrayParameter.getParameterTypes());
assertFalse(methodWithArrayParameter.isVarArgs());

Method methodWithVarArgsParameter = clazz.getDeclaredMethod("methodWithVarArgsParameter", int[].class);
assertArrayEquals(new Class[]{int[].class}, methodWithVarArgsParameter.getParameterTypes());
assertTrue(methodWithVarArgsParameter.isVarArgs());
```


The following code uses the `Method::isBridge` method to determine _bridge_ methods (synthetic methods that are generated by the Java compiler to handle type erasure in generics).


```
class SomeClass implements Comparator<Integer> {

    @Override
    public int compare(Integer i1, Integer i2) {
        return 0;
    }
/*
    public int compare(Object o1, Object o2) {
        return compare((Integer) o1, (Integer) o2);
    }
*/
}
```





```
Class<?> clazz = SomeClass.class;

Method explicitMethod = clazz.getDeclaredMethod("compare", Integer.class, Integer.class);
assertFalse(explicitMethod.isBridge());
assertFalse(explicitMethod.isSynthetic());

Method bridgeMethod = clazz.getDeclaredMethod("compare", Object.class, Object.class);
assertTrue(bridgeMethod.isBridge());
assertTrue(bridgeMethod.isSynthetic());
```


The following code uses the `Method::isDefault` method to determine _default_ methods (public non-abstract instance methods declared in interfaces).


```
interface SomeInterface {
    default void defaultMethod() {}
}
```





```
Class<?> clazz = SomeInterface.class;
Method method = clazz.getDeclaredMethod("defaultMethod");
assertTrue(method.isDefault());
