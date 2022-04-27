### Sealed classes and interfaces


##### Code examples

The following class hierarchy shows the use of `sealed` superclass with `final`, `non-sealed`, and `sealed` subclasses.


```
sealed class Polygon permits Triangle, Quadrangle, Pentagon {}

final class Triangle extends Polygon {}
sealed class Quadrangle extends Polygon permits Parallelogram, Trapezoid, Kite {}
non-sealed class Pentagon extends Polygon {}

non-sealed class Parallelogram extends Quadrangle {}
non-sealed class Trapezoid extends Quadrangle {}
non-sealed class Kite extends Quadrangle {}
```


The `Class::isSealed` method determines a sealed class, the `Class::getPermittedSubclasses` method returns a list of permitted subclasses for this sealed class.


```
Class<?> clazz = Polygon.class;
assertTrue(clazz.isSealed());

assertEquals(
   Set.of(
       Pentagon.class,
       Triangle.class,
       Quadrangle.class
   ),
   toSet(clazz.getPermittedSubclasses())
);

assertFalse(Triangle.class.isSealed());
assertTrue(Quadrangle.class.isSealed());
assertFalse(Pentagon.class.isSealed());
