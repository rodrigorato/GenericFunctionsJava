package ist.meic.pa.GenericFunctionsExtended.examples.domain;

import ist.meic.pa.GenericFunctionsExtended.GenericFunction;

@GenericFunction
public class Bug {
    // Test F
    public static void bug(Object o) {
        System.out.println("Object");
    }
    public static void bug(Foo f){
        System.out.println("Foo");
    }
    public static void bug(Bar b){ System.out.println("Bar"); }
}
