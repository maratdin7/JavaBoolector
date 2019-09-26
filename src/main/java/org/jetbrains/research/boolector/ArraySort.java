package org.jetbrains.research.boolector;

public class ArraySort extends BoolectorSort {

    ArraySort(Btor btor, long ref, Integer width) {
        super(btor, ref, width);
    }

    public static ArraySort arraySort(BoolectorSort index, BoolectorSort element) {
        Btor btor = index.getBtor();
        return new ArraySort(btor, Native.arraySort(btor.getRef(), index.getRef(), element.getRef()), element.getWidth());
    }
}
