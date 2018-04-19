package ist.meic.pa.GenericFunctions.injectors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

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
                        m.insertBefore(generateCallBackFunctionCall());
                }
            }

        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    public static void callBack(Class originalClass, Object[] originalArgs) {
        Method[] declaredMethods = originalClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            for (Class parameterType : method.getParameterTypes()) {
                System.out.println(parameterType.getName());
            }
            System.out.println("");
        }

    }

    private String generateCallBackFunctionCall() {
        return "ist.meic.pa.GenericFunctions.injectors.GenericCallInjector" +
                ".callBack($class, $args);";
    }

}
