package ist.meic.pa.GenericFunctionsExtended.examples;

import ist.meic.pa.GenericFunctionsExtended.examples.domain.Black;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Color;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Red;

public class TestC {
    public static void main(String[] args) {
        Object colors = new Object[] { new Red(), 2.9, new Black(), "Holla!"};
        System.out.println(Color.mix(colors));
    }
}

