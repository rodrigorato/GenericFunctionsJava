package ist.meic.pa.GenericFunctionsExtended.injectors;

import javassist.CtClass;

public interface AbstractInjector {
    void injectCode(CtClass ctClass);
}
