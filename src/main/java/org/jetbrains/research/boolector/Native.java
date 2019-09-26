package org.jetbrains.research.boolector;

public final class Native {

    static native long btor();

    static native void btorRelease(long btor);

    static native long var(long btor, long ref, String name);

    static native void releaseNode(long btor, long ref);

    static native long bitvecSort(long btor, int width);

    static native void releaseSort(long btor, long ref);

    static native boolean isBitvecSort(long btor, long ref);

    static native boolean isArraySort(long btor, long ref);

    static native int getWidth(long btor, long ref);

    static native long not(long btor, long ref);

    static native long add(long btor, long firstRef, long secondRef);

    static native long and(long btor, long firstRef, long secondRef);

    static native long or(long btor, long firstRef, long secondRef);

    static native long xor(long btor, long firstRef, long secondRef);

    static native long neg(long btor, long ref);

    static native long eq(long btor, long firstRef, long secondRef);

    static native long sub(long btor, long firstRef, long secondRef);

    static native long mul(long btor, long firstRef, long secondRef);

    static native long sdiv(long btor, long firstRef, long secondRef);

    static native long udiv(long btor, long firstRef, long secondRef);

    static native long smod(long btor, long firstRef, long secondRef);

    static native long urem(long btor, long firstRef, long secondRef);

    static native long sgt(long btor, long firstRef, long secondRef);

    static native long ugt(long btor, long firstRef, long secondRef);

    static native long sgte(long btor, long firstRef, long secondRef);

    static native long slt(long btor, long firstRef, long secondRef);

    static native long slte(long btor, long firstRef, long secondRef);

    static native long sll(long btor, long firstRef, long secondRef);

    static native long srl(long btor, long firstRef, long secondRef);

    static native long sra(long btor, long firstRef, long secondRef);

    static native long implies(long btor, long firstRef, long secondRef);

    static native long iff(long btor, long firstRef, long secondRef);

    static native long concat(long btor, long firstRef, long secondRef);

    static native long cond(long btor, long condRef, long firstRef, long secondRef);

    static native long zero(long btor, long ref);

    static native long constBitvec(long btor, String bits);

    static native boolean isBoolSort(long btor, long ref);

    static native long constNodeTrue(long btor);

    static native long constNodeFalse(long btor);

    static native long getSort(long btor, long ref);

    static native int getId(long btor, long ref);

    static native String getSymbol(long btor, long ref);

    static native int simplify(long btor);

    static native long constInt(long btor, int value, long ref);

    static native long sext(long btor, long ref, int width);

    static native long uext(long btor, long ref, int width);

    static native long slice(long btor, long ref, int upper, int lower);

    static native long arraySort(long btor, long indexSortRef, long elementSortRef);

    static native long array(long btor, long ref, String name);

    static native long read(long btor, long arrayRef, long indexRef);

    static native long write(long btor, long arrayRef, long indexRef, long valueRef);

    static native long param(long btor, long ref, String name);

    static native long fun(long btor, long[] params, int length, long nodeBody);

    static native long apply(long btor, long[] argNodes, int length, long ref);

    static native String getBits(long btor, long ref);

    static native long constLong(long btor, String value, long sort);

    static native long forAll(long btor, long[] params, int length, long ref);

    public static native boolean boolectorAssert(long btor, String ans, long ref);

    static native int kindNode(long btor, long ref);

    static native long constArray(long btor, long array_ref, long index_ref, long element_ref);

    static native int getWidthNode(long btor, long ref);

    static native int getIndexWidth(long btor, long ref);

    static native String dumpSmt2(long btor);

    static native String printModel(long btor);

    static native void assume(long btor, long ref);

    static native long bitvecAssignment(long btor, long ref);

    static native long matchNodeByName(long btor, String name);

    static native long copy(long btor, long ref);

    static native void assertForm(long btor, long ref);

    static native int getSat(long btor);
}
