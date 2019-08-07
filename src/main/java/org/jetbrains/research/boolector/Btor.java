package org.jetbrains.research.boolector;

import java.io.IOException;

public class Btor {
    public enum Status {
        SAT(10),
        UNSAT(20),
        UNKNOWN(0);

        private final int intValue;

        Status(int intValue) {
            this.intValue = intValue;
        }

        public static Status fromInt(int i) {
            if (SAT.toInt() == i) return SAT;
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
            NativeUtils.loadLibrary("boolector-3.0.0");
            NativeUtils.loadLibrary("boolector-jni-3.0.0");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to load dynamic libraries");
        }
    }

    public void btorRelease() {
        BitvecNode.setConstNameClear();
        Native.btorRelease();
    }

    public void dumpSmt2() {
        Native.dumpSmt2();
    }

    public void printModel() {
        Native.printModel();
    }

    public int simplify() {
        return Native.simplify();
    }

    public Status check() {
        return Status.fromInt(Native.getSat());
    }
}
