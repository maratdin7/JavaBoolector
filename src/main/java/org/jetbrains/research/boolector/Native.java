package org.jetbrains.research.boolector;

public final class Native {

    static native void btor();

    static native void btorRelease();

    static native long var(long ref, String name);

    static native void releaseNode(long ref);

    static native long bitvecSort(int width);

    static native void releaseSort(long ref);

    static native boolean isBitvecSort(long ref);

    static native boolean isArraySort(long ref);

    static native int getWidth(long ref);

    static native long not(long ref);

    static native long add(long firstRef, long secondRef);

    static native long and(long firstRef, long secondRef);

    static native long or(long firstRef, long secondRef);

    static native long xor(long firstRef, long secondRef);

    static native long neg(long ref);

    static native long eq(long firstRef, long secondRef);

    static native long copy(long ref);

    static native void assertForm(long ref);

    static native int getSat();

    static native long sub(long firstRef, long secondRef);

    static native long mul(long firstRef, long secondRef);

    static native long sdiv(long firstRef, long secondRef);

    static native long udiv(long firstRef, long secondRef);

    static native long smod(long firstRef, long secondRef);

    static native long urem(long firstRef, long secondRef);

    static native long sgt(long firstRef, long secondRef);

    static native long sgte(long firstRef, long secondRef);

    static native long slt(long firstRef, long secondRef);

    static native long slte(long firstRef, long secondRef);

    static native long sll(long firstRef, long secondRef);

    static native long srl(long firstRef, long secondRef);

    static native long sra(long firstRef, long secondRef);

    static native long implies(long firstRef, long secondRef);

    static native long iff(long firstRef, long secondRef);

    static native long concat(long firstRef, long secondRef);

    static native long cond(long condRef, long firstRef, long secondRef);

    static native long zero(long ref);

    static native long constBitvec(String bits);

    static native boolean isBoolSort(long ref);

    static native long constNodeTrue();

    static native long constNodeFalse();

    static native long getSort(long ref);

    static native int getId(long ref);

    static native String getSymbol(long ref);

    static native int simplify();

    static native long constInt(int value, long ref);

    static native long sext(long ref, int width);

    static native long uext(long ref, int width);

    static native long slice(long ref, int upper, int lower);

    static native long arraySort(long indexSortRef, long elementSortRef);

    static native long array(long ref, String name);

    static native long read(long arrayRef, long indexRef);

    static native long write(long arrayRef, long indexRef, long valueRef);

    static native long param(long ref, String name);

    static native long fun(long[] params, int length, long nodeBody);

    static native long apply(long[] argNodes, int length, long ref);

    static native String printModel();

    static native String getBits(long ref);

    static native long constLong(String value, long sort);

    static native long forAll(long[] params, int length, long ref);

    public static native boolean boolectorAssert(String ans, long ref);

    static native int kindNode(long ref);

    static native long constArray(long array_ref, long index_ref, long element_ref);

    static native int getWidthNode(long ref);

    static native int getIndexWidth(long ref);

    static native String dumpSmt2();

    static native void assume(long ref);

    static native long bitvecAssignment(long ref);

    static native long matchNodeByName(String name);
}
