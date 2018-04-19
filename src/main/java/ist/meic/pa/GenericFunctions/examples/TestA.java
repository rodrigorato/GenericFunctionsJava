package ist.meic.pa.GenericFunctions.examples;


import ist.meic.pa.GenericFunctions.examples.domain.Black;
import ist.meic.pa.GenericFunctions.examples.domain.Blue;
import ist.meic.pa.GenericFunctions.examples.domain.Color;
import ist.meic.pa.GenericFunctions.examples.domain.Red;

public class TestA {
    public static void main(String[] args) {
        Color[] colors = new Color[] { new Red(), new Blue(), new Black()};
        for(Color c : colors) System.out.println(Color.mix(c));
    }

}
