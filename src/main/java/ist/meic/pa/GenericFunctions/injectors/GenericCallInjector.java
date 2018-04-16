package ist.meic.pa.GenericFunctions.injectors;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This class is responsible for injecting code into the loaded classes,
 * in order to make their methods "Generic Functions", like in CLOS.
 */
public class GenericCallInjector implements AbstractInjector {
    @Override
    public void injectCode(CtClass ctClass) {
        /*  Check if there is a method for just Object parameters
            If there isn't:
                Create it

            Inject in the beginning ->
                check if there is a right call;
                if yes:
                    return doRightCall();
                else:
                    inject nothing;


       */

        // TODO: Do right checking for most generic and different methods here
        CtMethod[] methods = ctClass.getMethods();
        CtMethod mostAbstractMethod = Arrays.stream(methods)
                .filter(GenericCallInjector::checkAllObject)
                .findFirst()
                .orElse(null); // FIXME: return new method

        injectCodeInMostAbstractMethod(mostAbstractMethod);


    }


    /**
     * Injects code in a given method, making it call the respective type method in the hierarchy
     * @param m the method to inspect/intercept
     * @return the intercepted method
     */
    private static CtMethod injectCodeInMostAbstractMethod(CtMethod m) {
        return m;
    }

    /**
     * Checks if all parameter types of a given method are "Object"
     * @param m the method to inspect
     * @return true/false
     */
    private static boolean checkAllObject(CtMethod m) {
        try {
            // Can't really do t instanceof Object, so we just hack it here.
            return Arrays.stream(m.getParameterTypes()).allMatch(t -> t.getClass().getName().equals("Object"));
        } catch (NotFoundException e) {
            return false;
        }
    }
}
