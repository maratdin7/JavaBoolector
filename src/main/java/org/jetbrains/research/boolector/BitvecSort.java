package org.jetbrains.research.boolector;

public class BitvecSort extends BoolectorSort {

    BitvecSort(long ref) {
        super(ref);
    }

    public static BitvecSort bitvecSort(int width) {
        return new BitvecSort(Native.bitvecSort(width));
    }

    public int getWidth() {
        return Native.getWidth(ref);
    }



}
