package ist.meic.pa.GenericFunctionsExtended.examples;

import ist.meic.pa.GenericFunctionsExtended.examples.domain.Color;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.Red;
import ist.meic.pa.GenericFunctionsExtended.examples.domain.SuperBlack;

public class TestN {
    public static void main(String[] args) {
        Object red1 = new Red(), red2 = new Red(), black = new SuperBlack();
        System.out.println(Color.mix(red1, black, red2));
        System.out.println(Color.mix(black, black, red2));
        System.out.println(Color.mix(black, black, black));
    }
}
