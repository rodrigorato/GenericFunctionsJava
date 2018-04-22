package ist.meic.pa.GenericFunctionsExtended.injectors.utils;


import java.lang.reflect.Method;

public class MethodUtils {

public static boolean isMethodMoreSpecific(Object[] originalArgs, Method thisMethod, Method thatMethod) {
        return compareMethodArguments(originalArgs, thisMethod, thatMethod) < 0;
    }

    /**
     * @return An integer:
     *          < 0 - If the first method arguments are more specific than the second argument's
     *          = 0 - If the first method argument types are the same as the second argument's
     *          > 0 - If the first method arguments are less specific than the second argument's
     */
    public static int compareMethodArguments(Object[] originalArgs, Method thisMethod, Method thatMethod) {
        Class[] thisParameterTypes = thisMethod.getParameterTypes();
        Class[] thatParameterTypes = thatMethod.getParameterTypes();

        for(int p = 0; p < thisParameterTypes.length; p++) {
            if(!thisParameterTypes[p].getCanonicalName()
                    .equals(thatParameterTypes[p].getCanonicalName())) {

                if(isSubOf(thisParameterTypes[p], thatParameterTypes[p])) {
                    return -1;

                } else {
                    if(thisParameterTypes[p].isInterface() && thatParameterTypes[p].isInterface()) {

                        // Return according to the original arg type's interface declarations
                        for(Class interfaceType : originalArgs[p].getClass().getInterfaces()) {
                            if(isSubOf(thisParameterTypes[p], interfaceType)) {
                                return -1;
                            } else if(isSubOf(thatParameterTypes[p], interfaceType)) {
                                return 1;
                            }
                        }
                    }
                    return 1;
                }
            }
        }

        return 0;
    }

    /**
     * @return true when the method can be called with args, false otherwise
     */
    public static boolean isMethodApplicable(Method method, Object[] args) {
        if(method.getParameterCount() != args.length) {
            return false;
        }

        for(int p = 0; p < args.length; p++) {
            if(!isSubOf(args[p].getClass(), method.getParameterTypes()[p])) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return true when 2 classes belong in the same class hierarchy, false otherwise
     */
    public static boolean inSameHierarchy(Class thisClass, Class thatClass) {
        return isSubOf(thisClass, thatClass) || isSuperOf(thisClass, thatClass);
    }

    /**
     * Checks if a given class is a subclass/subinterface of another class
     * @return true when the first argument is a subclass/subinterface (or the same) of the second argument, false otherwise
     */
    @SuppressWarnings("unchecked")
    public static boolean isSubOf(Class thisClass, Class thatClass){
        return thatClass.isAssignableFrom(thisClass);
    }

    /**
     * Checks if a given class is a superclass/superinterface of another class
     * @return true when the first argument is a superclass/superinterface (or the same) of the second argument, false otherwise
     */
    @SuppressWarnings("unchecked")
    public static boolean isSuperOf(Class thisClass, Class thatClass){
        return thisClass.isAssignableFrom(thatClass);
    }

}
