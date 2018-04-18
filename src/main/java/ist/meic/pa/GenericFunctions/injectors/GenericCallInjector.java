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
            addCallBackFunction(ctClass);
            for(CtMethod m : ctClass.getDeclaredMethods()) {
                if(!m.getName().equals(callBackFunctionName)) {
                        m.insertBefore(generateCallBackFunctionCall());
                }
            }

        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    private String generateCallBackFunctionCode() {
        String code = "public static void " + callBackFunctionName + "(Class originalClass, Object[] originalArgs) {" +
                "   /*System.out.println(\"Hello world!\"); " +
                "   for(int i = 0; i < originalArgs.length; i++) {" +
                "       System.out.println(originalArgs[i].getClass().getName()); " +
                "   }" +
                "   System.out.println(originalClass.getDeclaredMethods());*/" +
                "" +
                "   java.lang.reflect.Method[] declaredMethods = originalClass.getDeclaredMethods();" +
                "   for(int m = 0; m < declaredMethods.length; m++) {" +
                "       java.lang.reflect.Method method = declaredMethods[m];" +
                "       for(int a = 0; a < method.getParameterTypes().length; a++) {" +
                "           Class parameterType = method.getParameterTypes()[a];" +
                "           System.out.println(parameterType.getName());" +
                "       }" +
                "       System.out.println(\"\");" +
                "   }";

                code += "}";

        return code;
    }

    private String generateCallBackFunctionCall() {
        return callBackFunctionName + "($class, $args);";
    }

    private void addCallBackFunction(CtClass ctClass) {
        try {
            CtMethod m = CtNewMethod.make(generateCallBackFunctionCode(), ctClass);
            ctClass.addMethod(m);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

    }
}
