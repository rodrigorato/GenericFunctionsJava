package ist.meic.pa.GenericFunctionsExtended.examples.combine;

import ist.meic.pa.GenericFunctionsExtended.GenericFunction;

import java.util.Vector;

@GenericFunction
public interface Com {
    public static Object bine(Object a, Object b) {
        Vector<Object> v = new Vector<Object>();
        v.add(a);
        v.add(b);
        return v;
    }

    public static Integer bine(Integer a, Integer b) {
        return a + b;
    }

    public static Object bine(String a, Object b) {
        return a + ", " + b + "!";
    }

    public static Object bine(String a, Integer b) {
        return (b == 0) ? "" : a + bine(a, b - 1);
    }
}
