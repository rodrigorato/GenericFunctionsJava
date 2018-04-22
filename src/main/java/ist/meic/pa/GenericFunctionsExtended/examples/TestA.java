package ist.meic.pa.GenericFunctionsExtended.examples;


import ist.meic.pa.GenericFunctionsExtended.examples.domain.Black;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Blue;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Color;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Red;

public class TestA {
    public static void main(String[] args) {
        Color[] colors = new Color[] { new Red(), new Blue(), new Black()};
        for(Color c : colors) System.out.println(Color.mix(c));
    }

}
