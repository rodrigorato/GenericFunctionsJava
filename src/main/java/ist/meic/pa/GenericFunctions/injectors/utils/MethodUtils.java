package ist.meic.pa.GenericFunctions.injectors.utils;

import ist.meic.pa.GenericFunctions.injectors.exception.MethodsNotComparableException;

import java.lang.reflect.Method;

public class MethodUtils {
    /**
     * Compares the argument types of 2 methods.
     * NOTE: Assumes both method arguments are comparable!
     * {@link #areMethodsComparable(Method, Method)}
     *
     * @return a number below 0 if the first method is more specific than the second, a number above 0 otherwise and 0 if they're the same
     */
    public static int methodArgumentsComparator(Method thisMethod, Method thatMethod) {

        for(int p = 0; p < thisMethod.getParameterCount(); p++) {
            Class thisMethodParameter = thisMethod.getParameterTypes()[p];
            Class thatMethodParameter = thatMethod.getParameterTypes()[p];

            // If the parameter types are the same, just skip ahead
            if(thisMethodParameter.getCanonicalName().equals(thatMethodParameter.getCanonicalName())) {
                continue;
            }

            if(thisMethodParameter.isAssignableFrom(thatMethodParameter)) {
                return 1;

            } else if (thatMethodParameter.isAssignableFrom(thisMethodParameter)) {
                return -1;

            }

        }

        return 0;
    }

    public static boolean areMethodsComparable(Method thisMethod, Method thatMethod) {
        if(thisMethod.getParameterCount() != thatMethod.getParameterCount()) {
            return false;
        }

        if(!classesInSameHierarchy(thisMethod.getReturnType(), thatMethod.getReturnType())) {
            return false;
        }

        for(int p = 0; p < thisMethod.getParameterCount(); p++) {
            if(!classesInSameHierarchy(thisMethod.getParameterTypes()[p], thatMethod.getParameterTypes()[p])) {
                return false;
            }
        }
        return true;
    }

    public static boolean classesInSameHierarchy(Class thisClass, Class thatClass) {
        return thisClass.isAssignableFrom(thatClass) || thatClass.isAssignableFrom(thisClass);
    }
}