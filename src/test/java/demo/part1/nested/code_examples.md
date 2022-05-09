### Nested classes and interfaces


##### Code examples

The following unit test uses the methods `Class::isMemberClass`, `Modifier::isStatic`, `Class::isLocalClass`, `Class::isAnonymousClass` to distinguish between static member classes, non-static member classes, local classes, and anonymous classes.


```
public class NestedClassesTest1 {

   static class StaticMemberClass {
       void test() {
           Class<?> clazz = this.getClass();
           assertTrue(clazz.isMemberClass());
           assertTrue(Modifier.isStatic(clazz.getModifiers()));
           assertFalse(clazz.isLocalClass());
           assertFalse(clazz.isAnonymousClass());
       }
   }

   @Test
   public void staticMemberClassTest() {
       new StaticMemberClass().test();
   }

   class NonStaticMemberClass {
       void test() {
           Class<?> clazz = this.getClass();
           assertTrue(clazz.isMemberClass());
           assertFalse(Modifier.isStatic(clazz.getModifiers()));
           assertFalse(clazz.isLocalClass());
           assertFalse(clazz.isAnonymousClass());
       }
   }

   @Test
   public void nonStaticMemberClassTest() {
       new NonStaticMemberClass().test();
   }

   @Test
   public void localClassTest() {
       class LocalClass {
           void test() {
               Class<?> clazz = this.getClass();
               assertFalse(clazz.isMemberClass());
               assertTrue(clazz.isLocalClass());
               assertFalse(clazz.isAnonymousClass());
           }
       }
       new LocalClass().test();
   }

   @Test
   public void anonymousClassTest() {
       new Object() {
           void test() {
               Class<?> clazz = this.getClass();
               assertFalse(clazz.isMemberClass());
               assertFalse(clazz.isLocalClass());
               assertTrue(clazz.isAnonymousClass());
           }
       }.test();
   }
}
```


The following unit test uses the methods `Class::getClasses` and `Class::getDeclaredClasses` to determine member classes: all public (public class inherited from superclass and public class declared in subclass) and only declared (public, protected, package and private classes declared in a subclass).


```
class SuperClass {
   public static class SuperPublicNestedClass {}
   protected static class SuperProtectedNestedClass {}
   static class SuperPackageNestedClass {}
   private static class SuperPrivateNestedClass {}
}

class SubClass extends SuperClass {
   public static class SubPublicNestedClass {}
   protected static class SubProtectedNestedClass {}
   static class SubPackageNestedClass {}
   private static class SubPrivateNestedClass {}
}
```





```
public class NestedClassesTest2 {

   @Test
   public void getClassesTest() {
       assertEquals(
           Set.of(
               "class demo.SuperClass$SuperPublicNestedClass",
               "class demo.SubClass$SubPublicNestedClass"
           ),
           toStringSet(SubClass.class.getClasses())
       );
   }

   @Test
   public void getDeclaredClassesTest() {
       assertEquals(
           Set.of(
               "class demo.SubClass$SubPublicNestedClass",
               "class demo.SubClass$SubProtectedNestedClass",
               "class demo.SubClass$SubPackageNestedClass",
               "class demo.SubClass$SubPrivateNestedClass"
           ),
           toStringSet(SubClass.class.getDeclaredClasses())
       );
   }
}
```


The following unit test uses the methods `Class::getEnclosingConstructor`, `Class::getEnclosingMethod` to determine the enclosing constructor and the enclosing method of local and anonymous classes.


```
public class NestedClassesTest3 {

   // enclosing constructor
   NestedClassesTest3() {
       class LocalClass {
           void test() {
               Class<?> clazz = this.getClass();
               Constructor<?> enclosingConstructor = clazz.getEnclosingConstructor();
               assertEquals("demo.NestedClassesTest3()",
                   enclosingConstructor.toString());
           }
       }
       new LocalClass().test();

       new Object() {
           void test() {
               Class<?> clazz = this.getClass();
               Constructor<?> enclosingConstructor = clazz.getEnclosingConstructor();
               assertEquals("demo.NestedClassesTest3()",
                   enclosingConstructor.toString());
           }
       }.test();
   }

   @Test
   // enclosing method
   public void getEnclosingMethodTest() {
       class LocalClass {
           void test() {
               Class<?> clazz = this.getClass();
               Method enclosingMethod = clazz.getEnclosingMethod();
               assertEquals("public void demo.NestedClassesTest3.getEnclosingMethodTest()",
                   enclosingMethod.toString());
           }
       }
       new LocalClass().test();

       new Object() {
           void test() {
               Class<?> clazz = this.getClass();
               Method enclosingMethod = clazz.getEnclosingMethod();
               assertEquals("public void demo.NestedClassesTest3.getEnclosingMethodTest()",
                   enclosingMethod.toString());
           }
       }.test();
   }
}
```


The following unit test shows the difference between the methods `Class::getDeclaringClass` and `Class::getEnclosingClass`. The `Class::getDeclaringClass` method returns the class of which the given class is a member; this method is only suitable for static and non-static member classes. The `Class::getEnclosingClass` method returns the immediately enclosing class; this method is suitable for static, non-static member classes, local and anonymous classes.


```
public class NestedClassesTest4 {

   static class StaticMemberClass {
       void test() {
           Class<?> clazz = this.getClass();
           assertEquals(NestedClassesTest4.class, clazz.getDeclaringClass());
           assertEquals(NestedClassesTest4.class, clazz.getEnclosingClass());
       }
   }

   @Test
   public void staticMemberClassTest() {
       new StaticMemberClass().test();
   }

   class NonStaticMemberClass {
       void test() {
           Class<?> clazz = this.getClass();
           assertEquals(NestedClassesTest4.class, clazz.getDeclaringClass());
           assertEquals(NestedClassesTest4.class, clazz.getEnclosingClass());
       }
   }

   @Test
   public void nonStaticMemberClassTest() {
       new NonStaticMemberClass().test();
   }

   @Test
   public void localClassTest() {
       class LocalClass {
           void test() {
               Class<?> clazz = this.getClass();
               assertNull(clazz.getDeclaringClass());
               assertEquals(NestedClassesTest4.class, clazz.getEnclosingClass());
           }
       }
       new LocalClass().test();
   }

   @Test
   public void anonymousClassTest() {
       new Object() {
           void test() {
               Class<?> clazz = this.getClass();
               assertNull(clazz.getDeclaringClass());
               assertEquals(NestedClassesTest4.class, clazz.getEnclosingClass());
           }
       }.test();
   }
}
```


The following code uses methods `Class::getNestHost`, `Class::isNestmateOf` and `Class::getNestMembers` to identify nests. Notice the nested class within another nested class.


```
class TopLevelClass {

   static class NestedClass1 {}
   static class NestedClass2 {
       static class NestedClass20 {}
   }
}
```





```
public class NestedClassesTest5 {

   @Test
   public void getNestHostTest() {
       assertEquals(TopLevelClass.class, TopLevelClass.class.getNestHost());
       assertEquals(TopLevelClass.class, NestedClass1.class.getNestHost());
       assertEquals(TopLevelClass.class, NestedClass2.class.getNestHost());
       assertEquals(TopLevelClass.class, NestedClass20.class.getNestHost());
   }

   @Test
   public void isNestmateOfTest() {
       assertTrue(TopLevelClass.class.isNestmateOf(TopLevelClass.class));
       assertTrue(TopLevelClass.class.isNestmateOf(NestedClass1.class));
       assertTrue(TopLevelClass.class.isNestmateOf(NestedClass2.class));
       assertTrue(TopLevelClass.class.isNestmateOf(NestedClass20.class));
   }

   @Test
   public void getNestMembersTest() {
       Set<Class<?>> expectedNestMembers = Set.of(
           TopLevelClass.class,
           NestedClass1.class,
           NestedClass2.class,
           NestedClass20.class
       );

       assertEquals(expectedNestMembers, toSet(TopLevelClass.class.getNestMembers()));
       assertEquals(expectedNestMembers, toSet(NestedClass1.class.getNestMembers()));
       assertEquals(expectedNestMembers, toSet(NestedClass2.class.getNestMembers()));
       assertEquals(expectedNestMembers, toSet(NestedClass20.class.getNestMembers()));
   }
}
