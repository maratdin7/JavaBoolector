package org.jetbrains.research.boolector;

public abstract class BoolectorSort extends BoolectorObject {
    private Integer width;

    BoolectorSort(Btor btor, long ref, Integer width) {
        super(btor, ref);
        this.width = width;
    }

    static BoolectorSort fromSort(Btor btor, long ref, Integer width) {
        if (Native.isArraySort(btor.getRef(), ref)) return new ArraySort(btor, ref, width);
        else if (Native.isBitvecSort(btor.getRef(), ref)) return new BitvecSort(btor, ref, width);
        else return new BoolSort(btor, ref);
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

    public void release() {
        Native.releaseSort(btor.getRef(), ref);
    }
}
