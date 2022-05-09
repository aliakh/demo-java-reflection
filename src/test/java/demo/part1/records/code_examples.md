### Record classes


##### Code examples

The following class is a record class with one record component.


```
record SomeRecord(int i) {}
```




The `Class::isRecord` method determines a record class, the `Class::getRecordComponents` method returns an array of one record component for this record class.


```
Class<?> clazz = SomeRecord.class;
assertTrue(clazz.isRecord());
assertEquals("class demo.SomeRecord", clazz.toString());

RecordComponent[] components = clazz.getRecordComponents();
assertEquals(1, components.length);

RecordComponent component = components[0];
assertEquals(clazz, component.getDeclaringRecord());
assertEquals("i", component.getName());
assertEquals(int.class, component.getType());
assertEquals("public int demo.SomeRecord.i()", component.getAccessor().toString());
assertEquals("int i", component.toString());
