package ist.meic.pa.GenericFunctions.injectors;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
                        m.insertBefore(generateCallBackFunctionCall(m.getName()));
                }
            }

        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void callBack(Class originalClass, String methodName, Object[] originalArgs) {
        /*
        // Call the respective before methods, from the most specific to the least one
        if(!isSetupMethod()) {

            doBeforeMethods();
        }
        */


        Method[] declaredMethods = originalClass.getDeclaredMethods();
        for (Method method : declaredMethods) {

            if(!isSetupMethod(method)) {
                for (Class parameterType : method.getParameterTypes()) {
                    System.out.println(parameterType.getName());
                }
                System.out.println("");
            }
        }

        /*
        // Call the respective after methods, from the least specific to the most one
        if(!isSetupMethod) {
            doAfterMethods();
        }
        */
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

    private String generateCallBackFunctionCall(String methodName) {
        return "ist.meic.pa.GenericFunctions.injectors.GenericCallInjector" +
                "." + callBackFunctionName + "($class, \"" + methodName + "\", $args);";
    }

}
