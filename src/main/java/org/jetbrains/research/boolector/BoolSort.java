package org.jetbrains.research.boolector;

public class BoolSort extends BoolectorSort {
    private static final int BOOL_WIDTH = 1;

    BoolSort(Btor btor, long ref) {
        super(btor, ref, BOOL_WIDTH);
    }

    public static BoolSort boolSort(Btor btor) {
        return new BoolSort(btor, Native.bitvecSort(btor.getRef(), BOOL_WIDTH));
    }
}
