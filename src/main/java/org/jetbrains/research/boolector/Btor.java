package org.jetbrains.research.boolector;

import java.io.IOException;

public class Btor {
    public Btor() {
        Native.btor();
    }

    static {
        try {
            NativeUtils.loadLibrary("boolector-3.0.0");
            NativeUtils.loadLibrary("boolector-jni-3.0.0");
        } catch (IOException e) {
            // This is probably not the best way to handle exception :-)
            e.printStackTrace();
        }
    }

    public void btorRelease() {
        BitvecNode.setConstNameClear();
        Native.btorRelease();
    }

    public void dumpSmt2() {Native.dumpSmt2();}

    public void printModel() {
        Native.printModel();
    }
}
