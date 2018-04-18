package ist.meic.pa.GenericFunctions.injectors.comparator;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.Comparator;

public class CtMethodComparator implements Comparator<CtMethod> {

    /**
     * NOTE: Assumes both methods have the same number of arguments!
     * @see Comparator
     */
    @Override
    public int compare(CtMethod thisMethod, CtMethod thatMethod) {
        Comparator<CtClass> comparator = new CtClassComparator();

        int sum = 0;
        try {
            for (int i = 0; i < thisMethod.getParameterTypes().length; i++) {
                sum += (comparator.compare(thisMethod.getParameterTypes()[i], thatMethod.getParameterTypes()[i]));
            }
        } catch (NotFoundException e) { }

        return sum;
    }
}
