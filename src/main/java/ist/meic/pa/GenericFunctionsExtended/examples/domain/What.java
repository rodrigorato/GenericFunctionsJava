package ist.meic.pa.GenericFunctionsExtended.examples.domain;

import ist.meic.pa.GenericFunctionsExtended.AfterMethod;
import ist.meic.pa.GenericFunctionsExtended.BeforeMethod;
import ist.meic.pa.GenericFunctionsExtended.GenericFunction;

@GenericFunction
public class What {
    public static void is(Black i) {
        System.out.print("What is black? ");
    }

    public static void is(Red i) {
        System.out.print("What is red? ");
    }

    @BeforeMethod
    public static void is(Blue o) {
        System.out.println("Blue ");
    }

    @AfterMethod
    public static void is(Object o) {
        System.out.print(" Is it an object?");
    }

    @AfterMethod
    public static void is(Color o) {
        System.out.print(" Is it a color?");
    }

    @AfterMethod
    public static void is(SuperBlack o) {
        System.out.print(" It is all of that and much more...");
    }
}
