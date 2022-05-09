### Hidden classes and interfaces


##### Code examples

The `java.lang.invoke.MethodHandles.Lookup::defineHiddenClass` method creates a hidden class from a _class_ file as a byte array argument. The `Class::isHidden` method determines a hidden class.


```
byte[] classFile = getClassFileAsBytes(SomeClass.class);
Class<?> clazz = MethodHandles.lookup()
   .defineHiddenClass(classFile, true, ClassOption.NESTMATE)
   .lookupClass();
assertTrue(clazz.isHidden());
