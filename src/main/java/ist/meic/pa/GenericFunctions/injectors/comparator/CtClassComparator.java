package ist.meic.pa.GenericFunctions.injectors.comparator;

import javassist.CtClass;
import javassist.CtMethod;

import java.util.Comparator;

public class CtClassComparator implements Comparator<CtClass> {
    @Override
    public int compare(CtClass thisClass, CtClass thatClass) {
        if(thisClass.getName().equals(thatClass.getName())) {
            return 0;
        }

        if(thisClass.subclassOf(thatClass)) {
            return 1;
        } else {
            return -1;
        }
    }
}
