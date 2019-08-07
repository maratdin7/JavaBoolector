package org.jetbrains.research.boolector;

public class ArraySort extends BoolectorSort {

    ArraySort(long ref) {
        super(ref);
    }

    public static ArraySort arraySort(BoolectorSort index, BoolectorSort element) {
        return new ArraySort(Native.arraySort(index.ref, element.ref));
    }
}
