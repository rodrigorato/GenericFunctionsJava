package ist.meic.pa.GenericFunctions.injectors;

import javassist.CtClass;

public interface AbstractInjector {
    void injectCode(CtClass ctClass);
}
