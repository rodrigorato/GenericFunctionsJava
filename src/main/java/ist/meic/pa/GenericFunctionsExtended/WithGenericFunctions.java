package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctionsExtended.translator.GenericFunctionTranslator;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class WithGenericFunctions {
    public static void main(String[] args) {

        try {
            // Get our translator that will do our instrumentation
            Translator translator = new GenericFunctionTranslator();

            // Get the current class pool and add our translator to it's loader
            ClassPool classPool = ClassPool.getDefault();
            Loader classLoader = new Loader();
            classLoader.addTranslator(classPool, translator);

            // Get the new parameters for the class we're about to hand over control to
            String[] parameters = Arrays.copyOfRange(args, 1, args.length);

            // Hand over control, providing the right arguments
            classLoader.run(args[0], parameters);

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("No main class was specified! Can't hand over control, ending.");
            System.exit(-1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NotFoundException e) {
            // Can't really do anything about this, let's just end the program
            System.out.println("Reflection failed...");
            System.exit(-1);
        } catch (Throwable throwable) {
            System.out.println("Failed to run main class! - " + throwable.getMessage());
            System.exit(-1);
        }
    }
}
