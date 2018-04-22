package ist.meic.pa.GenericFunctions.examples;


import ist.meic.pa.GenericFunctions.examples.domain.Com;

public class TestB {
    public static void main(String[] args) {
        Object[] objects = new Object[] { new Object(), "Foo", 123};
        for(Object c : objects) System.out.println(Com.bine(c));
    }
}
