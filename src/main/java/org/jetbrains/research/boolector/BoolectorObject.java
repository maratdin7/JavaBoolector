package org.jetbrains.research.boolector;
import java.util.Objects;

public abstract class BoolectorObject {
    protected final long ref;

    BoolectorObject(long ref) {
        this.ref = ref;
    }

    abstract public void release();

    public static long[] toLong(BoolectorObject[] boolectorObj) {
        int size = boolectorObj.length;
        long[] toLong = new long[size];
        for (int i = 0; i < size; i++) {
            toLong[i] = boolectorObj[i].ref;
        }
        return toLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolectorObject that = (BoolectorObject) o;
        return ref == that.ref;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }
}
