package org.jetbrains.research.boolector;

import java.io.IOException;

public class Btor {
    public Btor() {
        Native.btor();
    }

    static {
        try {
            NativeUtils.loadLibraryFromJar("/libboolector-3.0.0.so");
            NativeUtils.loadLibraryFromJar("/libboolector-jni-3.0.0.so");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to load dynamic libraries");
        }
    }

    public void btorRelease() {
        BitvecNode.setConstNameClear();
        Native.btorRelease();
    }

    public String dumpSmt2() {
        return Native.dumpSmt2();
    }

    public String printModel() {
        return Native.printModel();
    }

}
