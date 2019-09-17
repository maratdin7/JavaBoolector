package org.jetbrains.research.boolector;

public class ArraySort extends BoolectorSort {

    ArraySort(long ref, Integer width) {
        super(ref, width);
    }

    public static ArraySort arraySort(BoolectorSort index, BoolectorSort element) {
        return new ArraySort(Native.arraySort(index.ref, element.ref), element.getWidth());
    }
}
