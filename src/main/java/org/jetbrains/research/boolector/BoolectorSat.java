package org.jetbrains.research.boolector;

public class BoolectorSat {

    public enum Status {
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

    public static int simplify() {
        return Native.simplify();
    }

    public static Status getBoolectorSat() {
        return Status.fromInt(Native.getSat());
    }

}

