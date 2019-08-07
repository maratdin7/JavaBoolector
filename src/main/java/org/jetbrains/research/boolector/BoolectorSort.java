package org.jetbrains.research.boolector;

public class BoolectorSort extends BoolectorObject {

    BoolectorSort(long ref) {
        super(ref);
    }

    public boolean isBitvecSort() {
        return Native.isBitvecSort(ref);
    }

    public boolean isBoolSort() {
        return Native.isBoolSort(ref);
    }

    public boolean isArraySort() {
        return Native.isArraySort(ref);
    }

    public BitvecSort toBitvecSort() {
        if (isArraySort()) throw new ClassCastException();
        else return new BitvecSort(ref);
    }

    public BoolSort toBoolSort() {
        if (isBoolSort()) return new BoolSort(ref);
        else throw new ClassCastException();
    }

    public ArraySort toArraySort() {
        if (isArraySort()) return new ArraySort(ref);
        else throw new ClassCastException();
    }

    public void release() {
        Native.releaseSort(ref);
    }
}
