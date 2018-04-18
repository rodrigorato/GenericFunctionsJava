package ist.meic.pa.GenericFunctions.injectors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * This class is responsible for injecting code into the loaded classes,
 * in order to make their methods "Generic Functions", like in CLOS.
 */
public class GenericCallInjector implements AbstractInjector {
    private String callBackFunctionName = "callBack";
    private String callBackFunctionCall = callBackFunctionName + "($args);";
    private String callBackFunctionCode = "public static void " + callBackFunctionName + "(Object[] originalArgs) {" +
            "   System.out.println(\"Hello world!\"); " +
            "   for(int i = 0; i < originalArgs.length; i++) {" +
            "       System.out.println(originalArgs[i].getClass().getName()); " +
            "   }" +
            "}";

    @Override
    public void injectCode(CtClass ctClass) {
        try {
            addCallBackFunction(ctClass);
            for(CtMethod m : ctClass.getDeclaredMethods()) {
                if(!m.getName().equals(callBackFunctionName)) {
                        m.insertBefore(callBackFunctionCall);
                }
            }

        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    private void addCallBackFunction(CtClass ctClass) {
        try {
            CtMethod m = CtNewMethod.make(callBackFunctionCode, ctClass);
            ctClass.addMethod(m);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

    }
}
