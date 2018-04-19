package ist.meic.pa.GenericFunctions.injectors;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.injectors.utils.MethodUtils;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is responsible for injecting code into the loaded classes,
 * in order to make their methods "Generic Functions", like in CLOS.
 */
public class GenericCallInjector implements AbstractInjector {
    private String callBackFunctionName = "callBack";

    @Override
    public void injectCode(CtClass ctClass) {

        try {
            for(CtMethod m : ctClass.getDeclaredMethods()) {
                if(!m.getName().equals(callBackFunctionName)) {
                        m.insertBefore(
                                generateCallBackFunctionCall(m.getLongName(), m.getReturnType().getName()));
                }
            }

        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static Object callBack(Class originalClass, String methodLongName, Object[] originalArgs) {
        /*
        // Call the respective before methods, from the most specific to the least one
        if(!isSetupMethod()) {

            doBeforeMethods();
        }
        */

        try {
            Method best = findBestMethod(originalClass, originalArgs);
            if(!methodLongName.equals(getLongNameFromMethod(best))) {
                return best.invoke(null, originalArgs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

        /*
        // Call the respective after methods, from the least specific to the most one
        if(!isSetupMethod) {
            doAfterMethods();
        }
        */
    }

    private static String getLongNameFromMethod(Method m) {
        String[] names = m.toString().split(" ");
        return names[names.length -1];
    }

    public static Method findBestMethod(Class originalClass, Object[] args) {
        Method[] allMethods = originalClass.getDeclaredMethods();

        Method bestMethod = null;
        for(Method candidate : allMethods) {
            if(MethodUtils.isMethodApplicable(candidate, args)) {
                if (bestMethod == null ||
                        MethodUtils.isMethodMoreSpecific(candidate, bestMethod)) {
                    bestMethod = candidate;
                }
            }
        }
        return bestMethod;

    }

    public static boolean isSetupMethod(Method m) {
        return hasAnnotation(m, AfterMethod.class) || hasAnnotation(m, BeforeMethod.class);
    }

    public static boolean hasAnnotation(Method m, Class targetAnnotation) {
        for(Annotation annotation : m.getAnnotations()) {
            if(annotation.getClass().getCanonicalName().equals(targetAnnotation.getCanonicalName())) {
                return true;
            }
        }
        return false;
    }

    private String generateCallBackFunctionCall(String methodName, String returnClassName) {
        return "Object ret = ist.meic.pa.GenericFunctions.injectors.GenericCallInjector" +
                "." + callBackFunctionName + "($class, \"" + methodName + "\", $args);" +
                "if(ret != null) {" +
                "   return (" + returnClassName + ")ret;" +
                "}";
    }

}
