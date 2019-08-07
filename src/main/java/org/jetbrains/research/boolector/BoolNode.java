package org.jetbrains.research.boolector;

public class BoolNode extends BoolectorNode {
    BoolNode(long ref) {
        super(ref);
    }

    public static BoolNode constBool(boolean bool) {
        return new BoolNode(bool ? Native.constNodeTrue() : Native.constNodeFalse());
    }

    public BoolNode and(BoolNode boolNode) {
        return new BoolNode(Native.and(ref, boolNode.ref));
    }

    public BoolNode or(BoolNode boolNode) {
        return new BoolNode(Native.or(ref, boolNode.ref));
    }

    public BoolNode xor(BoolNode boolNode) {
        return new BoolNode(Native.xor(ref, boolNode.ref));
    }

    public BoolNode not() {
        return new BoolNode(Native.not(ref));
    }

    public BoolNode implies(BoolNode boolNode) {
        return new BoolNode(Native.implies(ref, boolNode.ref));
    }

    public BoolNode iff(BoolNode boolNode) {
        return new BoolNode(Native.iff(ref, boolNode.ref));
    }

    public Boolean assigment() {
        return this.toBitvecNode().assignment()==1;
    }

}
