package org.jetbrains.research.boolector;

import java.util.HashSet;
import java.util.Set;

public class BitvecNode extends BoolectorNode {

    private static Set<String> setConstName = new HashSet<>();

    BitvecNode(Btor btor, long ref, String name, Integer width) {
        super(btor, ref, name, width, TypeNode.BITVECNODE);
    }

    static void setConstNameClear() {
        setConstName.clear();
    }

    public static BitvecNode var(BitvecSort sort, String name, boolean fresh) {
        Btor btor = sort.getBtor();
        if (fresh) {
            name = name + "!" + numberOfNames++;
            return new BitvecNode(btor, Native.var(btor.getRef(), sort.getRef(), name), name, sort.getWidth());
        } else if (setConstName.contains(name)) return matchNodeByName(btor, name);
        else {
            setConstName.add(name);
            return new BitvecNode(btor, Native.var(btor.getRef(), sort.getRef(), name), name, sort.getWidth());
        }
    }

    public static BitvecNode matchNodeByName(Btor btor, String name) {
        return new BitvecNode(btor, Native.matchNodeByName(btor.getRef(), name), name, null);
    }

    public static BitvecNode zero(BitvecSort sort) {
        Btor btor = sort.getBtor();
        return new BitvecNode(btor, Native.zero(btor.getRef(), sort.getRef()), null, sort.getWidth());
    }

    public static BitvecNode constBitvec(Btor btor, String bits) {
        return new BitvecNode(btor, Native.constBitvec(btor.getRef(), bits), null, bits.length());
    }

    public static BitvecNode constInt(int value, BitvecSort sort) {
        Btor btor = sort.getBtor();
        return new BitvecNode(btor, Native.constInt(btor.getRef(), value, sort.getRef()), null, sort.getWidth());
    }

    public static BitvecNode constLong(long value, BitvecSort sort) {
        Btor btor = sort.getBtor();
        return new BitvecNode(btor, Native.constLong(btor.getRef(), String.valueOf(value), sort.getRef()), null, sort.getWidth());
    }

    public BitvecNode sext(int width) {
        int curSize = getWidth();
        return new BitvecNode(btor, Native.sext(btor.getRef(), ref, width - curSize), null, width);
    }

    public BitvecNode uext(int width) {
        int curSize = getWidth();
        return new BitvecNode(btor, Native.uext(btor.getRef(), ref, width - curSize), null, width);
    }

    public BitvecNode slice(int upper, int lower) {
        return new BitvecNode(btor, Native.slice(btor.getRef(), ref, upper, lower), null, upper - lower + 1);
    }

    public BitvecNode not() {
        return new BitvecNode(btor, Native.not(btor.getRef(), ref), null, getWidth());
    }

    public BitvecNode add(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.add(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode and(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.and(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode or(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.or(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode xor(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.xor(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode neg() {
        return new BitvecNode(btor, Native.neg(btor.getRef(), ref), null, getWidth());
    }

    public BitvecNode sub(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.sub(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode mul(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.mul(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode sdiv(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.sdiv(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode udiv(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.udiv(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode smod(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.smod(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode urem(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.urem(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BoolNode sgt(BitvecNode bvNode) {
        return new BoolNode(btor, Native.sgt(btor.getRef(), ref, bvNode.getRef()));
    }

    public BoolNode ugt(BitvecNode bvNode) {
        return new BoolNode(btor, Native.ugt(btor.getRef(), ref, bvNode.getRef()));
    }

    public BoolNode sgte(BitvecNode bvNode) {
        return new BoolNode(btor, Native.sgte(btor.getRef(), ref, bvNode.getRef()));
    }

    public BoolNode slt(BitvecNode bvNode) {
        return new BoolNode(btor, Native.slt(btor.getRef(), ref, bvNode.getRef()));
    }

    public BoolNode slte(BitvecNode bvNode) {
        return new BoolNode(btor, Native.slte(btor.getRef(), ref, bvNode.getRef()));
    }

    public BitvecNode sll(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.sll(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode srl(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.srl(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode sra(BitvecNode bvNode) {
        return new BitvecNode(btor, Native.sra(btor.getRef(), ref, bvNode.getRef()), null, getWidth());
    }

    public BitvecNode concat(BitvecNode bvNode) {
        int width = getWidth() + bvNode.getWidth();
        return new BitvecNode(btor, Native.concat(btor.getRef(), ref, bvNode.getRef()), null, width);
    }

    public String getBits() {
        return Native.getBits(btor.getRef(), ref);
    }

    public long assignment() {
        return Native.bitvecAssignment(btor.getRef(), ref);
    }
}
