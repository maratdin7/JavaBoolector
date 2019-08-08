package org.jetbrains.research.boolector;

import java.io.IOException;
import java.util.List;

public class Btor {
    public enum Status {
        SAT(10),
        UNSAT(20),
        UNKNOWN(0);

        private final int intValue;

        Status(int intValue) {
            this.intValue = intValue;
        }

        public static Status fromInt(int i) {
            if (SAT.toInt() == i) return SAT;
            if (UNSAT.toInt() == i) return UNSAT;
            return UNKNOWN;
        }

        public final int toInt() {
            return this.intValue;
        }
    }

    public Btor() {
        Native.btor();
    }

    static {
        try {
            NativeUtils.loadLibrary("boolector-3.0.0");
            NativeUtils.loadLibrary("boolector-jni-3.0.0");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to load dynamic libraries");
        }
    }

    public void btorRelease() {
        BitvecNode.setConstNameClear();
        Native.btorRelease();
    }

    public String getSmtLib() {
        return Native.dumpSmt2();
    }

    public String model2SmtLib() {
        return Native.printModel();
    }

    public BoolNode constBool(boolean bool) {
        return BoolNode.constBool(bool);
    }

    public ArrayNode arrayNode(ArraySort sort, String name) {
        return ArrayNode.arrayNode(sort, name);
    }

    public ArrayNode constArrayNode(BitvecSort indexSort, BitvecNode element) {
        return ArrayNode.constArrayNode(indexSort, element);
    }

    public BitvecNode zero(BitvecSort sort) {
        return BitvecNode.zero(sort);
    }

    public BitvecNode constBitvec(String bits) {
        return BitvecNode.constBitvec(bits);
    }

    public BitvecNode constInt(int value, BitvecSort sort) {
        return BitvecNode.constInt(value, sort);
    }

    public BitvecNode constLong(long value, BitvecSort sort) {
        return BitvecNode.constLong(value, sort);
    }

    public BitvecNode var(BitvecSort sort, String name, boolean fresh) {
        return BitvecNode.var(sort, name, fresh);
    }

    public Function func(BoolectorNode nodeBody, List<BoolectorFun.FuncParam> func_params) {
        return Function.func(nodeBody, func_params);
    }

    public Function forAll(BoolectorNode nodeBody, List<BoolectorFun.FuncParam> func_params) {
        return Function.forAll(nodeBody, func_params);
    }

    public int simplify() {
        return Native.simplify();
    }

    public Status check() {
        return Status.fromInt(Native.getSat());
    }
}
