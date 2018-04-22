package ist.meic.pa.GenericFunctions.examples;

import ist.meic.pa.GenericFunctions.examples.domain.Black;
import ist.meic.pa.GenericFunctions.examples.domain.Color;
import ist.meic.pa.GenericFunctions.examples.domain.Red;

public class TestC {
    public static void main(String[] args) {
        Object colors = new Object[] { new Red(), 2.9, new Black(), "Holla!"};
        System.out.println(Color.mix(colors));
    }
}

