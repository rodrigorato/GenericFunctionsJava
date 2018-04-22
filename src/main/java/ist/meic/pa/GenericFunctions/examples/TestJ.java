package ist.meic.pa.GenericFunctions.examples;

import ist.meic.pa.GenericFunctions.examples.domain.Blue;
import ist.meic.pa.GenericFunctions.examples.domain.Color;
import ist.meic.pa.GenericFunctions.examples.domain.What;

import java.util.NoSuchElementException;

public class TestJ {
    public static void main(String[] args) {
        Color blue = new Blue();
        What.is(blue);
    }

}
