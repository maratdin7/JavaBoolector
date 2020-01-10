package org.jetbrains.research.boolector;

import java.io.IOException;
import java.util.Objects;

public class Btor {

    private long ref;

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
        this.ref = Native.btor();
    }

    static {
        try {
            NativeUtils.loadLibrary("boolector");
            NativeUtils.loadLibrary("boolector-java");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to load dynamic libraries");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Btor)) return false;
        Btor btor = (Btor) o;
        return ref == btor.ref;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }

    public void release() {
        BitvecNode.setConstNameClear();
        Native.btorRelease(this.ref);
        this.ref = -1;
    }

    public String dumpSmt2() {
        return Native.dumpSmt2(this.ref);
    }

    public String printModel() {
        return Native.printModel(this.ref);
    }

    public int simplify() {
        return Native.simplify(this.ref);
    }

    public Status check() {
        return Status.fromInt(Native.getSat(this.ref));
    }

    long getRef() {
        return ref;
    }
}
