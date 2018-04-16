package ist.meic.pa.GenericFunctions.translator;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.Translator;

/**
 * This will inject code into the loaded classes, according to it's annotations
 */
public class GenericFunctionTranslator implements Translator {
    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {

    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {

    }
}
