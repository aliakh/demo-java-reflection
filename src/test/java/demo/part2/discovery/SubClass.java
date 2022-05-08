package demo.part2.discovery;

class SubClass extends SuperClass {

    // constructors
    public SubClass() {}
    protected SubClass(int i) {}
    SubClass(int i1, int i2) {}
    private SubClass(int i1, int i2, int i3) {}

    // fields
    public int subPublicField;
    protected int subProtectedField;
    int subDefaultField;
    private int subPrivateField;

    // methods
    public void subPublicMethod() {}
    protected void subProtectedMethod() {}
    void subDefaultMethod() {}
    private void subPrivateMethod() {}

    // nested classes
    public static class SubPublicNestedClass {}
    protected static class SubProtectedNestedClass {}
    static class SubDefaultNestedClass {}
    private static class SubPrivateNestedClass {}
}
