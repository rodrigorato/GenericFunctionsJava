package ist.meic.pa.GenericFunctions;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class WithGenericFunctions {
    public static void main(String[] args) throws CannotCompileException {

        // Handle control to next class in args
        try {
            ClassPool cp = ClassPool.getDefault();

            CtClass ct = cp.get(args[0]);

            //TODO: changes to ctclass should go around here

            Class c = ct.toClass();

            Method newMain = c.getMethod("main", String[].class);

            // Get the new parameters for the class we're about to hand over control to
            String[] parameters = Arrays.copyOfRange(args, 1, args.length);

            // Hand over control, providing the right arguments
            newMain.invoke(null, (Object) parameters);

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("No main class was specified! Can't hand over control, ending.");
            throw e;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NotFoundException e) {
            // Can't really do anything about this, let's just end the program
            System.exit(-1);
        }
    }
}
