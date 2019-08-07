package org.jetbrains.research.boolector;

public class BoolSort extends BoolectorSort {
    BoolSort(long ref) {
        super(ref);
    }

    public static BoolSort boolSort() {
        return new BoolSort(Native.bitvecSort(1));
    }
}
