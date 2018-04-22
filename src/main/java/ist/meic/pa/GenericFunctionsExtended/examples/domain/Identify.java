package ist.meic.pa.GenericFunctionsExtended.examples.domain;

import ist.meic.pa.GenericFunctionsExtended.GenericFunction;

@GenericFunction
public class Identify {

    public static String it(Object o) { return "Object"; }
    public static String it(String s) { return "String"; }
    private static String it(Integer a) { return "Integer"; }
    public static String it(Object[] arr) {
        String res = "";
        for (Object o : arr)
            res += it(o);
        return res;
    }
}
