package ist.meic.pa.GenericFunctionsExtended.examples.domain;

import ist.meic.pa.GenericFunctionsExtended.AfterMethod;
import ist.meic.pa.GenericFunctionsExtended.BeforeMethod;
import ist.meic.pa.GenericFunctionsExtended.GenericFunction;

@GenericFunction
public class MakeIt {
    public static void ddouble(C1 c) {System.out.println("C1");}

    @BeforeMethod
    @AfterMethod
    public static void ddouble(Object c) {System.out.println("Object");}

    @BeforeMethod
    @AfterMethod
    public static void ddouble(Foo c) {System.out.println("Foo");}

}

