package org.jetbrains.research.boolector;

import java.util.HashSet;
import java.util.Set;

public class BitvecNode extends BoolectorNode {

    private static Set<String> setConstName = new HashSet<>();

    static void setConstNameClear() {
        setConstName.clear();
    }

    BitvecNode(long ref, String name, Integer width) {
        super(ref, name, width, TypeNode.BITVECNODE);
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
        return new BitvecNode(Native.matchNodeByName(name), name, null);
    }

    public static BitvecNode zero(BitvecSort sort) {
        return new BitvecNode(Native.zero(sort.ref), null, sort.getWidth());
    }

    public static BitvecNode constBitvec(String bits) {
        return new BitvecNode(Native.constBitvec(bits), null, bits.length());
    }

    public static BitvecNode constInt(int value, BitvecSort sort) {
        return new BitvecNode(Native.constInt(value, sort.ref), null, sort.getWidth());
    }

    public static BitvecNode constLong(long value, BitvecSort sort) {
        return new BitvecNode(Native.constLong(String.valueOf(value), sort.ref), null, sort.getWidth());
    }

    public BitvecNode sext(int width) {
        int curSize = getWidth();
        return new BitvecNode(Native.sext(ref, width - curSize), null, width);
    }

    public BitvecNode uext(int width) {
        int curSize = getWidth();
        return new BitvecNode(Native.uext(ref, width - curSize), null, width);
    }

    public BitvecNode slice(int upper, int lower) {
        return new BitvecNode(Native.slice(ref, upper, lower), null, upper - lower + 1);
    }

    public BitvecNode not() {
        return new BitvecNode(Native.not(ref), null, getWidth());
    }

    public BitvecNode add(BitvecNode bvNode) {
        return new BitvecNode(Native.add(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode and(BitvecNode bvNode) {
        return new BitvecNode(Native.and(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode or(BitvecNode bvNode) {
        return new BitvecNode(Native.or(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode xor(BitvecNode bvNode) {
        return new BitvecNode(Native.xor(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode neg() {
        return new BitvecNode(Native.neg(ref), null, getWidth());
    }

    public BitvecNode sub(BitvecNode bvNode) {
        return new BitvecNode(Native.sub(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode mul(BitvecNode bvNode) {
        return new BitvecNode(Native.mul(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode sdiv(BitvecNode bvNode) {
        return new BitvecNode(Native.sdiv(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode udiv(BitvecNode bvNode) {
        return new BitvecNode(Native.udiv(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode smod(BitvecNode bvNode) {
        return new BitvecNode(Native.smod(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode urem(BitvecNode bvNode) {
        return new BitvecNode(Native.urem(ref, bvNode.ref), null, getWidth());
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
        return new BitvecNode(Native.sll(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode srl(BitvecNode bvNode) {
        return new BitvecNode(Native.srl(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode sra(BitvecNode bvNode) {
        return new BitvecNode(Native.sra(ref, bvNode.ref), null, getWidth());
    }

    public BitvecNode concat(BitvecNode bvNode) {
        int width = getWidth() + bvNode.getWidth();
        return new BitvecNode(Native.concat(ref, bvNode.ref), null, width);
    }

    public String getBits() {
        return Native.getBits(ref);
    }

    public long assignment() {
        return Native.bitvecAssignment(ref);
    }
}
