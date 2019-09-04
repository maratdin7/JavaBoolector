package org.jetbrains.research.boolector;

import java.util.HashSet;
import java.util.Set;

public class BitvecNode extends BoolectorNode {

    private static Set<String> setConstName = new HashSet<>(); // мб HashMap и без matchNodeByName

    static void setConstNameClear() {
        setConstName.clear();
    }

    BitvecNode(long ref) {
        super(ref, TypeNode.BITVECNODE);
    }

    BitvecNode(long ref, String name, Integer width) {
        super(ref, name, width, TypeNode.BITVECNODE);
    }

    BitvecNode(long ref, String name) {
        super(ref, name, null, TypeNode.BITVECNODE);
    }

    BitvecNode(long ref, Integer width) {
        super(ref, null, width, TypeNode.BITVECNODE);
    }

    public static BitvecNode var(BitvecSort sort, String name, boolean fresh) {
        if (fresh) {
            name = name + "!" + numberOfNames++;
            return new BitvecNode(Native.var(sort.ref, name), name, sort.getWidth());
        } else if (setConstName.contains(name)) return matchNodeByName(name);
        else {
            setConstName.add(name);
            return new BitvecNode(Native.var(sort.ref, name), name, sort.getWidth());
        }
    }

    public static BitvecNode matchNodeByName(String name) {
        return new BitvecNode(Native.matchNodeByName(name), name);
    }

    public static BitvecNode zero(BitvecSort sort) {
        return new BitvecNode(Native.zero(sort.ref), sort.getWidth());
    }

    public static BitvecNode constBitvec(String bits) {
        return new BitvecNode(Native.constBitvec(bits), bits.length());
    }

    public static BitvecNode constInt(int value, BitvecSort sort) {
        return new BitvecNode(Native.constInt(value, sort.ref), sort.getWidth());
    }

    public static BitvecNode constLong(long value, BitvecSort sort) {
        return new BitvecNode(Native.constLong(String.valueOf(value), sort.ref), sort.getWidth());
    }

    public BitvecNode sext(int width) {
        int curSize = getWidth();
        return new BitvecNode(Native.sext(ref, width - curSize), width);
    }

    public BitvecNode uext(int width) {
        int curSize = getWidth();
        return new BitvecNode(Native.uext(ref, width - curSize), width);
    }

    public BitvecNode slice(int upper, int lower) {
        return new BitvecNode(Native.slice(ref, upper, lower), upper - lower + 1);
    }

    public BitvecNode not() {
        return new BitvecNode(Native.not(ref), getWidth());
    }

    public BitvecNode add(BitvecNode bvNode) {
        return new BitvecNode(Native.add(ref, bvNode.ref), getWidth());
    }

    public BitvecNode and(BitvecNode bvNode) {
        return new BitvecNode(Native.and(ref, bvNode.ref), getWidth());
    }

    public BitvecNode or(BitvecNode bvNode) {
        return new BitvecNode(Native.or(ref, bvNode.ref), getWidth());
    }

    public BitvecNode xor(BitvecNode bvNode) {
        return new BitvecNode(Native.xor(ref, bvNode.ref));
    }

    public BitvecNode neg() {
        return new BitvecNode(Native.neg(ref), getWidth());
    }

    public BitvecNode sub(BitvecNode bvNode) {
        return new BitvecNode(Native.sub(ref, bvNode.ref), getWidth());
    }

    public BitvecNode mul(BitvecNode bvNode) {
        return new BitvecNode(Native.mul(ref, bvNode.ref), getWidth());
    }

    public BitvecNode sdiv(BitvecNode bvNode) {
        return new BitvecNode(Native.sdiv(ref, bvNode.ref), getWidth());
    }

    public BitvecNode udiv(BitvecNode bvNode) {
        return new BitvecNode(Native.udiv(ref, bvNode.ref), getWidth());
    }

    public BitvecNode smod(BitvecNode bvNode) {
        return new BitvecNode(Native.smod(ref, bvNode.ref), getWidth());
    }

    public BitvecNode urem(BitvecNode bvNode) {
        return new BitvecNode(Native.urem(ref, bvNode.ref), getWidth());
    }

    public BoolNode sgt(BitvecNode bvNode) {
        return new BoolNode(Native.sgt(ref, bvNode.ref));
    }

    public BoolNode ugt(BitvecNode bvNode) {
        return new BoolNode(Native.ugt(ref, bvNode.ref));
    }

    public BoolNode sgte(BitvecNode bvNode) {
        return new BoolNode(Native.sgte(ref, bvNode.ref));
    }

    public BoolNode slt(BitvecNode bvNode) {
        return new BoolNode(Native.slt(ref, bvNode.ref));
    }

    public BoolNode slte(BitvecNode bvNode) {
        return new BoolNode(Native.slte(ref, bvNode.ref));
    }

    public BitvecNode sll(BitvecNode bvNode) {
        return new BitvecNode(Native.sll(ref, bvNode.ref), getWidth());
    }

    public BitvecNode srl(BitvecNode bvNode) {
        return new BitvecNode(Native.srl(ref, bvNode.ref), getWidth());
    }

    public BitvecNode sra(BitvecNode bvNode) {
        return new BitvecNode(Native.sra(ref, bvNode.ref), getWidth());
    }

    public BitvecNode concat(BitvecNode bvNode) {
        int width = getWidth() + bvNode.getWidth();
        return new BitvecNode(Native.concat(ref, bvNode.ref), width);
    }

    public String getBits() {
        return Native.getBits(ref);
    }

    public long assignment() {
        return Native.bitvecAssignment(ref);
    }
}
