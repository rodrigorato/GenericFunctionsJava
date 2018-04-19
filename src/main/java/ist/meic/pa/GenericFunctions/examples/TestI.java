package ist.meic.pa.GenericFunctions.examples;


import ist.meic.pa.GenericFunctions.examples.domain.Color;
import ist.meic.pa.GenericFunctions.examples.domain.Red;
import ist.meic.pa.GenericFunctions.examples.domain.SuperBlack;
import ist.meic.pa.GenericFunctions.examples.domain.What;

public class TestI {
    public static void main(String[] args) {
        Object[] colors = new Color[]{new SuperBlack(), new Red()};
        for (Object o : colors) What.is(o);
    }
}
