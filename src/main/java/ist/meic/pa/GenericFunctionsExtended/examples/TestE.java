package ist.meic.pa.GenericFunctionsExtended.examples;

import ist.meic.pa.GenericFunctionsExtended.examples.domain.Identify;

public class TestE {
    public static void main(String[] args) {
        Object objects = new Object[] { 123, "Foo", 1.2};
        System.out.println(Identify.it(objects));
    }
}
