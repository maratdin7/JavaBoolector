package org.jetbrains.research.boolector;

public class BoolNode extends BoolectorNode {
    BoolNode(Btor btor, long ref) {
        super(btor, ref, null, TypeNode.BOOLNODE);
    }

    static BoolNode constBool(Btor btor, boolean bool) {
        return new BoolNode(btor, bool ? Native.constNodeTrue(btor.getRef()) : Native.constNodeFalse(btor.getRef()));
    }

    public BoolNode and(BoolNode boolNode) {
        return new BoolNode(btor, Native.and(btor.getRef(), ref, boolNode.getRef()));
    }

    public BoolNode or(BoolNode boolNode) {
        return new BoolNode(btor, Native.or(btor.getRef(), ref, boolNode.getRef()));
    }

    public BoolNode xor(BoolNode boolNode) {
        return new BoolNode(btor, Native.xor(btor.getRef(), ref, boolNode.getRef()));
    }

    public BoolNode not() {
        return new BoolNode(btor, Native.not(btor.getRef(), ref));
    }

    public BoolNode implies(BoolNode boolNode) {
        return new BoolNode(btor, Native.implies(btor.getRef(), ref, boolNode.getRef()));
    }

    public BoolNode iff(BoolNode boolNode) {
        return new BoolNode(btor, Native.iff(btor.getRef(), ref, boolNode.getRef()));
    }

    public Boolean assigment() {
        return Native.bitvecAssignment(btor.getRef(), ref) == 1;
    }

}
