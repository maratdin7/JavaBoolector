package org.jetbrains.research.boolector;

import java.io.IOException;

public class Btor {

    enum Status {
        SAT(10),
        UNSAT(20),
        UNKNOWN(0);

        private final int intValue;

        Status(int intValue) {
            this.intValue = intValue;
        }

        public static Status fromInt(int i) {
            if (SAT.toInt() == i ) return SAT;
            if (UNSAT.toInt() == i) return UNSAT;
            return UNKNOWN;
        }

        public final int toInt() {
            return this.intValue;
        }
    }

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

    public void release() {
        BitvecNode.setConstNameClear();
        Native.btorRelease();
    }

    public String dumpSmt2() {
        return Native.dumpSmt2();
    }

    public String printModel() {
        return Native.printModel();
    }

    public int simplify() {
        return Native.simplify();
    }

    public Status check() {
        return Status.fromInt(Native.getSat());
    }
}
