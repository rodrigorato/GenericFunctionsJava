package ist.meic.pa.GenericFunctionsExtended.examples;


import ist.meic.pa.GenericFunctionsExtended.examples.domain.Color;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Red;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.SuperBlack;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.What;

public class TestI {
    public static void main(String[] args) {
        Object[] colors = new Color[]{new SuperBlack(), new Red()};
        for (Object o : colors) What.is(o);
    }
}
