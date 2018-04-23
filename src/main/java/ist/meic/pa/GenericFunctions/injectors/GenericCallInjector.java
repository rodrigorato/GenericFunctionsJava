package ist.meic.pa.GenericFunctions.injectors;

import ist.meic.pa.GenericFunctions.AfterMethod;
import ist.meic.pa.GenericFunctions.BeforeMethod;
import ist.meic.pa.GenericFunctions.injectors.utils.MethodUtils;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * This class is responsible for injecting code into the loaded classes,
 * in order to make their methods "Generic Functions", like in CLOS.
 */
public class GenericCallInjector implements AbstractInjector {
    private String callBackFunctionName = "callBack";
    public static boolean invokedSuccessful = false;
    public static boolean isSetup = false;
    public static boolean beforeMethodsDone = false;
    public static boolean existsApplicableMethod = false;

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
        Method best = findBestMethod(originalClass, originalArgs);

        existsApplicableMethod = best != null;
        if(!existsApplicableMethod) {
            return null;
        }

        // check if this is not a setup method call
        // and if we already did the before methods for this call
        // also, only do this if there is an actual method to call after the setup ones
        if(!isSetup && !beforeMethodsDone) {
            doBeforeMethods(originalClass, originalArgs);
        }

        beforeMethodsDone = true;
        invokedSuccessful = false;

        // And if it's not the method we're running right now
        if (!methodLongName.equals(MethodUtils.getLongNameFromMethod(best))) {
            Object result = null;
            try {
                // Call the actual method
                best.setAccessible(true); // take care of private methods
                result = best.invoke(null, originalArgs);
                invokedSuccessful = true;
            } catch (Exception e) {
                invokedSuccessful = false;
            }

            if (!isSetup) {
                doAfterMethods(originalClass, originalArgs);
            }
            beforeMethodsDone = false;

            return result;
        }

        return null; // We're done here!
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
                        if(MethodUtils.isMethodMoreSpecific(originalArgs, candidate,beforeMethods.get(i))){
                            beforeMethods.add(i,candidate);
                            added = true;
                            break;
                        }
                    }
                    if(!added){
                        beforeMethods.addLast(candidate);
                    }
                }
            }
        }

        MethodUtils.callMethodList(beforeMethods,originalArgs,"");
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
                        if(MethodUtils.isMethodMoreSpecific(originalArgs, afterMethods.get(i),candidate)){
                            afterMethods.add(i,candidate);
                            added = true;
                            break;
                        }
                    }
                    if(!added){
                        afterMethods.addLast(candidate);
                    }
                }
            }
        }

        MethodUtils.callMethodList(afterMethods,originalArgs,"");

    }

    public static Method findBestMethod(Class originalClass, Object[] args) {
        Method[] allMethods = originalClass.getDeclaredMethods();

        Method bestMethod = null;
        for(Method candidate : allMethods) {
            if(MethodUtils.isMethodApplicable(candidate, args) && !MethodUtils.isSetupMethod(candidate)) {
                if (bestMethod == null ||
                        MethodUtils.isMethodMoreSpecific(args, candidate, bestMethod)) {
                    bestMethod = candidate;
                }
            }
        }
        return bestMethod;

    }

    private String generateCallBackFunctionCall(String methodName, String returnClassName) {
        String code = "";

        if (returnClassName.equals("void")){
            code += "if (!" + GenericCallInjector.class.getCanonicalName() + ".isSetup){" +
                    "   " + GenericCallInjector.class.getCanonicalName() +
                    "       ." + callBackFunctionName + "($class, \"" + methodName + "\", $args);" +
                    "   if(" + GenericCallInjector.class.getCanonicalName() + ".invokedSuccessful){" +
                    "       return;" +
                    "   }" +
                    "}";
        } else {
            code += "Object ret = " + GenericCallInjector.class.getCanonicalName() +
                    "                   ." + callBackFunctionName + "($class, \"" + methodName + "\", $args);" +
                    "if(ret != null) {" +
                    "   return (" + returnClassName + ")ret;" +
                    "}";
        }

        code += "if(!" + GenericCallInjector.class.getCanonicalName() +
                "       .existsApplicableMethod) {" +
                "   return " + (returnClassName.equals("void") ? "" : "null") + ";" +
                "}";

        return code;
    }

}
