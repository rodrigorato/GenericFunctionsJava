package ist.meic.pa.GenericFunctions.examples;


import ist.meic.pa.GenericFunctions.examples.domain.Explain;

public class TestH {
    public static void main(String[] args) {
        Object[] objs = new Object[]{"Hello", 1, 2.0};
        for (Object o : objs) Explain.it(o);
    }
}
