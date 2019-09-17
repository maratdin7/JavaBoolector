package org.jetbrains.research.boolector;

public class BoolSort extends BoolectorSort {
    BoolSort(long ref) {
        super(ref, 1);
    }

    public static BoolSort boolSort() {
        return new BoolSort(Native.bitvecSort(1));
    }
}
