package ist.meic.pa.GenericFunctions.examples;

import ist.meic.pa.GenericFunctions.examples.domain.Bug;
import ist.meic.pa.GenericFunctions.examples.domain.C1;
import ist.meic.pa.GenericFunctions.examples.domain.C2;

public class TestF {
    public static void main(String[] args) {
        Object c1 = new C1(), c2 = new C2();
        Bug.bug(c1);
        Bug.bug(c2);
    }
}
