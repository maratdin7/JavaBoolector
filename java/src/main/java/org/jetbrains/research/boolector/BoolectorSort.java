package org.jetbrains.research.boolector;

public class BoolectorSort extends BoolectorObject {

    BoolectorSort(long ref, Integer width) {
        super(ref);
        this.width = width;
    }

    private Integer width;

    public int getWidth() {
        return width;
    }

    public boolean isBitvecSort() {
        return Native.isBitvecSort(ref);
    }

    public boolean isBoolSort() {
        return (isBitvecSort() && width==1);
    }

    public boolean isArraySort() {
        return Native.isArraySort(ref);
    }

    public BitvecSort toBitvecSort() {
        if (isArraySort()) throw new ClassCastException();
        else return new BitvecSort(ref, width);
    }

    public BoolSort toBooleSort() {
        if (isBoolSort()) return new BoolSort(ref);
        else throw new ClassCastException();
    }

    public ArraySort toArraySort() {
        if (isArraySort()) return new ArraySort(ref, width);
        else throw new ClassCastException();
    }

    public void release() {
        Native.releaseSort(ref);
    }
}
