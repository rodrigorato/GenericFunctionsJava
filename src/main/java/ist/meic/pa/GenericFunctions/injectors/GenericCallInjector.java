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
import java.util.LinkedList;
import java.util.List;

/**
 * This class is responsible for injecting code into the loaded classes,
 * in order to make their methods "Generic Functions", like in CLOS.
 */
public class GenericCallInjector implements AbstractInjector {
    private String callBackFunctionName = "callBack";
    public static boolean invokedSuccessful = false;
    public static boolean isSetup = false;
    public static boolean beforeDone = false;

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

        if(!isSetup && !beforeDone) { // check if this is not a setup method call and if we already did the before methods for this call
            doBeforeMethods(originalClass, originalArgs);
        }

        beforeDone = true;

        invokedSuccessful = false;

        try {
            Method best = findBestMethod(originalClass, originalArgs);
            if (best == null)
                return null;
            if(!methodLongName.equals(getLongNameFromMethod(best))) {
                best.setAccessible(true); // take care of private methods
                Object result = best.invoke(null, originalArgs);
                invokedSuccessful = true;
                if(!isSetup) {
                    doAfterMethods(originalClass, originalArgs);
                }
                beforeDone = false;
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static String getLongNameFromMethod(Method m) {
        String[] names = m.toString().split(" ");
        return names[names.length -1];
    }

    private static void doBeforeMethods(Class originalClass, Object[] originalArgs){

        Method[] methods = originalClass.getDeclaredMethods();

        LinkedList<Method> beforeMethods = new LinkedList<>();

        for (Method candidate : methods){
            if(MethodUtils.isMethodApplicable(candidate,originalArgs) && candidate.isAnnotationPresent(BeforeMethod.class)){
                if(beforeMethods.isEmpty()){
                    beforeMethods.add(candidate);
                }
                else{
                    boolean added = false;
                    for(int i = 0; i < beforeMethods.size(); i++){
                        if(MethodUtils.isMethodMoreSpecific(candidate,beforeMethods.get(i))){
                            beforeMethods.add(i,candidate);
                            break;
                        }
                    }
                    if(!added){
                        beforeMethods.addLast(candidate);
                    }
                }
            }
        }

        isSetup = true;
        for (Method m : beforeMethods) {
            m.setAccessible(true);

            try {
                m.invoke(null,originalArgs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        isSetup = false;
    }

    private static void doAfterMethods(Class originalClass, Object[] originalArgs){

        Method[] methods = originalClass.getDeclaredMethods();

        LinkedList<Method> afterMethods = new LinkedList<>();

        for (Method candidate : methods){
            if(MethodUtils.isMethodApplicable(candidate,originalArgs) && candidate.isAnnotationPresent(AfterMethod.class)){
                if(afterMethods.isEmpty()){
                    afterMethods.add(candidate);
                }
                else{
                    boolean added = false;
                    for(int i = 0; i < afterMethods.size(); i++){
                        if(MethodUtils.isMethodMoreSpecific(afterMethods.get(i),candidate)){
                            afterMethods.add(i,candidate);
                            break;
                        }
                    }
                    if(!added){
                        afterMethods.addLast(candidate);
                    }
                }
            }
        }

        isSetup = true;
        for (Method m : afterMethods) {

            m.setAccessible(true);

            try {
                m.invoke(null,originalArgs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        isSetup = false;

    }

    public static Method findBestMethod(Class originalClass, Object[] args) {
        Method[] allMethods = originalClass.getDeclaredMethods();

        Method bestMethod = null;
        for(Method candidate : allMethods) {
            if(MethodUtils.isMethodApplicable(candidate, args) && !isSetupMethod(candidate)) {
                if (bestMethod == null ||
                        MethodUtils.isMethodMoreSpecific(candidate, bestMethod)) {
                    bestMethod = candidate;
                }
            }
        }
        return bestMethod;

    }

    public static boolean isSetupMethod(Method m) {
        return m.isAnnotationPresent(AfterMethod.class) || m.isAnnotationPresent(BeforeMethod.class);
    }

    private String generateCallBackFunctionCall(String methodName, String returnClassName) {
        if (returnClassName.equals("void")){
            return "if (!ist.meic.pa.GenericFunctions.injectors.GenericCallInjector.isSetup){" +
                    "ist.meic.pa.GenericFunctions.injectors.GenericCallInjector" +
                    "." + callBackFunctionName + "($class, \"" + methodName + "\", $args);" +
                    "if(ist.meic.pa.GenericFunctions.injectors.GenericCallInjector.invokedSuccessful){" +
                    "   return;" +
                    "}}";
        }
        return "Object ret = ist.meic.pa.GenericFunctions.injectors.GenericCallInjector" +
                "." + callBackFunctionName + "($class, \"" + methodName + "\", $args);" +
                "if(ret != null) {" +
                "   return (" + returnClassName + ")ret;" +
                "}";
    }

}
