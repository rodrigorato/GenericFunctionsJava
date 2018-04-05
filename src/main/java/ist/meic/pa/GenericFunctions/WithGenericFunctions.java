package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class WithGenericFunctions {
    public static void main(String[] args) {
        // TODO: Stuff and thangs

        // Handle control to next class in args
        try {
            Method newMain = Class.forName(args[0]).getMethod("main", String[].class);

            // Get the new parameters for the class we're about to hand over control to
            String[] parameters = Arrays.copyOfRange(args, 1, args.length);

            // Hand over control, providing the right arguments
            newMain.invoke(null, parameters);

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("No main class was specified! Can't hand over control, ending.");
            throw e;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Can't really do anything about this, let's just end the program
            System.exit(-1);
        }
    }
}
