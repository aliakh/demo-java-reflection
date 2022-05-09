## Discovery members and constructors


##### Code examples


###### Fields discovery

The example of a class hierarchy includes a superclass and a subclass, both of which declare fields with different accessibility.


```
class SuperClass {
   public int superPublicField;
   protected int superProtectedField;
   int superDefaultField;
   private int superPrivateField;
}

class SubClass extends SuperClass {
   public int subPublicField;
   protected int subProtectedField;
   int subDefaultField;
   private int subPrivateField;
}
```


The ‘Class::getDeclaredFields‘ method returns an array of all_ _fields _declared_ in the subclass (including _public_, _protected_, _package_, and _private_ fields), but excluding any fields inherited from the superclass.


```
Class<?> clazz = SubClass.class;

Field[] declaredFields = clazz.getDeclaredFields();
assertEquals(
   Set.of(
       "public int demo.SubClass.subPublicField",
       "protected int demo.SubClass.subProtectedField",
       "int demo.SubClass.subDefaultField",
       "private int demo.SubClass.subPrivateField"
   ),
   toStringSet(declaredFields)
);
```


The ‘Class::getDeclaredField‘ method returns the specified_ _field of any accessibility _declared_ in the subclass by its name.


```
Field declaredField = clazz.getDeclaredField("subPrivateField");
assertEquals("private int demo.SubClass.subPrivateField", declaredField.toString());
```


The ‘Class::getFields’ method returns an array of all _public_ fields, including those declared in the subclass and those inherited from the superclass.


```
Field[] fields = clazz.getFields();
assertEquals(
   Set.of(
       "public int demo.SuperClass.superPublicField",
       "public int demo.SubClass.subPublicField"
   ),
   toStringSet(fields)
);
```


The ‘Class::getField‘ method returns the specified _public_ field, whether declared in the subclass or inherited from the superclass by its name.


```
Field field = clazz.getField("superPublicField");
assertEquals("public int demo.SuperClass.superPublicField", field.toString());
```



###### Methods discovery

The example of a class hierarchy includes a superclass and a subclass, both of which declare methods with different accessibility.


```
class SuperClass {
   public void superPublicMethod() {}
   protected void superProtectedMethod() {}
   void superDefaultMethod() {}
   private void superPrivateMethod() {}
}

class SubClass extends SuperClass {
   public void subPublicMethod() {}
   protected void subProtectedMethod() {}
   void subDefaultMethod() {}
   private void subPrivateMethod() {}
}
```


The ‘Class::getDeclaredMethods‘ method returns an array of all_ _methods _declared_ in the subclass (including _public_, _protected_, _package_, and _private_ methods), but excluding any methods inherited from the superclass.


```
Class<?> clazz = SubClass.class;

Method[] declaredMethods = clazz.getDeclaredMethods();
assertEquals(
   Set.of(
       "public void demo.SubClass.subPublicMethod()",
       "protected void demo.SubClass.subProtectedMethod()",
       "void demo.SubClass.subDefaultMethod()",
       "private void demo.SubClass.subPrivateMethod()"
   ),
   toStringSet(declaredMethods)
);
```


The ‘Class::getDeclaredMethod‘ method returns the specified_ _method of any accessibility _declared_ in the subclass by its name.


```
Method declaredMethod = clazz.getDeclaredMethod("subPrivateMethod");
assertEquals("private void demo.SubClass.subPrivateMethod()", declaredMethod.toString());
```


The ‘Class::getMethods’ method returns an array of all _public_ methods, including those declared in the subclass and those inherited from the superclass. Notice the _public_ methods than the subclass inherited from the ’Object’ class.


```
Method[] methods = clazz.getMethods();
assertEquals(
   Set.of(
       "public void demo.SuperClass.superPublicMethod()",
       "public void demo.SubClass.subPublicMethod()",

       "public final native java.lang.Class java.lang.Object.getClass()",
       "public native int java.lang.Object.hashCode()",
       "public boolean java.lang.Object.equals(java.lang.Object)",
       "public java.lang.String java.lang.Object.toString()",
       "public final native void java.lang.Object.notify()",
       "public final native void java.lang.Object.notifyAll()",
       "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
       "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
       "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
   ),
   toStringSet(methods)
);
```


The ‘Class::getMethod‘ method returns the specified _public_ method, whether declared in the subclass or inherited from the superclass by its name.


```
Method method = clazz.getMethod("superPublicMethod");
assertEquals("public void demo.SuperClass.superPublicMethod()", method.toString());
```



###### Constructors discovery

The example of a class hierarchy includes a superclass and a subclass, both of which declare overloaded constructors with different accessibility.


```
class SuperClass {
   public SuperClass() {}
   protected SuperClass(int i) {}
   SuperClass(int i1, int i2) {}
   private SuperClass(int i1, int i2, int i3) {}
}

class SubClass extends SuperClass {
   public SubClass() {}
   protected SubClass(int i) {}
   SubClass(int i1, int i2) {}
   private SubClass(int i1, int i2, int i3) {}
}
```


The ‘Class::getDeclaredConstructors‘ constructor returns an array of all_ _constructors _declared_ in the subclass (including _public_, _protected_, _package_, and _private_ constructors), but excluding any constructors inherited from the superclass.


```
Class<?> clazz = SubClass.class;

Constructor<?>[] constructors = clazz.getDeclaredConstructors();
assertEquals(
   Set.of(
       "public demo.SubClass()",
       "protected demo.SubClass(int)",
       "demo.SubClass(int,int)",
       "private demo.SubClass(int,int,int)"
   ),
   toStringSet(constructors)
);
```


The ‘Class::getDeclaredConstructor‘ constructor returns the specified_ _constructor of any accessibility _declared_ in the subclass by its parameter types.


```
Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(int.class, int.class, int.class);
assertEquals("private demo.SubClass(int,int,int)", declaredConstructor.toString());
```


The ‘Class::getConstructors’ constructor returns an array of all _public_ constructors declared in the subclass. Notice that the subclass inherits no constructors from the superclass.


```
Constructor<?>[] constructors = clazz.getConstructors();
assertEquals(
   Set.of(
       "public demo.SubClass()"
   ),
   toStringSet(constructors)
);
```


The ‘Class::getConstructor‘ constructor returns the specified _public_ constructor declared in the subclass by its parameter types.


```
Constructor<?> constructor = clazz.getConstructor();
assertEquals("public demo.SubClass()", constructor.toString());
```



###### Member classes discovery

The example of a class hierarchy includes a superclass and a subclass, both of which declare static member classes with different accessibility.


```
class SuperClass {
   public static class SuperPublicNestedClass {}
   protected static class SuperProtectedNestedClass {}
   static class SuperDefaultNestedClass {}
   private static class SuperPrivateNestedClass {}
}

class SubClass extends SuperClass {
   public static class SubPublicNestedClass {}
   protected static class SubProtectedNestedClass {}
   static class SubDefaultNestedClass {}
   private static class SubPrivateNestedClass {}
}
```


The ‘Class::getDeclaredClasses‘ method returns an array of all_ _member classes _declared_ in the subclass (including _public_, _protected_, _package_, and _private_ methods), but excluding any member classes inherited from the superclass.


```
Class<?> clazz = SubClass.class;

Class<?>[] declaredClasses = clazz.getDeclaredClasses();
assertEquals(
   Set.of(
       "class demo.SubClass$SubPublicNestedClass",
       "class demo.SubClass$SubProtectedNestedClass",
       "class demo.SubClass$SubDefaultNestedClass",
       "class demo.SubClass$SubPrivateNestedClass"
   ),
   toStringSet(declaredClasses)
);
```


The ‘Class::getClasses’ method returns an array of all _public_ member classes, including those declared in the subclass and inherited from the superclass.


```
Class<?>[] classes = clazz.getClasses();
assertEquals(
   Set.of(
       "class demo.SuperClass$SuperPublicNestedClass",
       "class demo.SubClass$SubPublicNestedClass"
   ),
   toStringSet(classes)
);
