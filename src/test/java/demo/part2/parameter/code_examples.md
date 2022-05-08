### The Parameter class


##### Code examples

The example class contains a method with one parameter.


```
class SomeClass {
   private void someMethod(int someParameter) {}
}
```


The following code uses methods of the `Parameter` class for the method parameter.


```
Class<?> clazz = SomeClass.class;
Method method = clazz.getDeclaredMethod("someMethod", int.class);

Parameter[] parameters = method.getParameters();
assertEquals(1, parameters.length);
Parameter parameter = parameters[0];

// methods declared in the Parameter class
assertEquals(method, parameter.getDeclaringExecutable());
assertFalse(parameter.isNamePresent());
assertEquals("arg0", parameter.getName());
assertEquals("", Modifier.toString(parameter.getModifiers()));
assertEquals(int.class, parameter.getType());
