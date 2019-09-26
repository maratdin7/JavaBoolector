package org.jetbrains.research.boolector;

public class BoolectorSort extends BoolectorObject {
    private Integer width;

    BoolectorSort(Btor btor, long ref, Integer width) {
        super(btor, ref);
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public boolean isBitvecSort() {
        return Native.isBitvecSort(btor.getRef(), ref);
    }

    public boolean isBoolSort() {
        return (isBitvecSort() && width == 1);
    }

    public boolean isArraySort() {
        return Native.isArraySort(btor.getRef(), ref);
    }

    public BitvecSort toBitvecSort() {
        if (isArraySort()) throw new ClassCastException();
        else return new BitvecSort(btor, ref, width);
    }

    public BoolSort toBoolSort() {
        if (isBoolSort()) return new BoolSort(btor, ref);
        else throw new ClassCastException();
    }

    public ArraySort toArraySort() {
        if (isArraySort()) return new ArraySort(btor, ref, width);
        else throw new ClassCastException();
    }

    public void release() {
        Native.releaseSort(btor.getRef(), ref);
    }
}
