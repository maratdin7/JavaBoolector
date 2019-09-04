package org.jetbrains.research.boolector;

public class BitvecSort extends BoolectorSort {

    BitvecSort(long ref, int width) {
        super(ref, width);
    }

    public static BitvecSort bitvecSort(int width) {
        return new BitvecSort(Native.bitvecSort(width), width);
    }
}
