package ist.meic.pa.GenericFunctions.translator;

import ist.meic.pa.GenericFunctions.GenericFunction;
import javassist.*;

import java.util.Arrays;

/**
 * This will inject code into the loaded classes, according to it's annotations
 */
public class GenericFunctionTranslator implements Translator {
    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
        // We don't need to do anything here, for now
    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
        // First, check if the class has out GenericFunction annotation
        CtClass loadedClass = pool.get(classname);
        try {
            if(Arrays.stream(loadedClass.getAnnotations())
                    .anyMatch(p -> p instanceof GenericFunction)) {
                // Apply our instrumentation!

            }
        } catch (ClassNotFoundException e) {
            // TODO: handle this? is this ever going to happen?
            e.printStackTrace();
        }


    }
}
