package demo.part2.discovery;

class SuperClass {

    // constructors
    public SuperClass() {}
    protected SuperClass(int i) {}
    SuperClass(int i1, int i2) {}
    private SuperClass(int i1, int i2, int i3) {}

    // fields
    public int superPublicField;
    protected int superProtectedField;
    int superDefaultField;
    private int superPrivateField;

    // methods
    public void superPublicMethod() {}
    protected void superProtectedMethod() {}
    void superDefaultMethod() {}
    private void superPrivateMethod() {}

    // nested classes
    public static class SuperPublicNestedClass {}
    protected static class SuperProtectedNestedClass {}
    static class SuperDefaultNestedClass {}
    private static class SuperPrivateNestedClass {}
}
