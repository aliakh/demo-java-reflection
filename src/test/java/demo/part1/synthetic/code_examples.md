### Synthetic classes and interfaces


##### Code examples

The `Class::isSynthetic` method determines a synthetic class.


```
Class<? extends Runnable> clazz = ((Runnable) () -> {}).getClass();
assertTrue(clazz.isSynthetic());
```


In the described Java implementation, lambdas are implemented as synthetic classes.
