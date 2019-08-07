package org.jetbrains.research.boolector;

public class BoolectorNode extends BoolectorObject {

    BoolectorNode(long ref) {
        super(ref);
    }

    private TypeNode kind = TypeNode.UNKNOWN;

    public void release() {
        Native.releaseNode(ref);
    }

    public BoolectorNode copy() {
        return new BoolectorNode(Native.copy(ref));
    }

    public BoolNode eq(BoolectorNode node) {
        return new BoolNode(Native.eq(ref, node.ref));
    }

    public BoolectorNode ite(BoolNode cond, BoolectorNode elseNode) {
        return new BoolectorNode(Native.cond(cond.ref, ref, elseNode.ref)); //fdjfksdjfkldsjfksdjfkljdskfjsdkfsjklfsfkdsfjklds
    }

    public BoolectorSort getSort() {//если уже изменялся все сломается
        return new BoolectorSort(Native.getSort(ref));
    }

    public int getID() {
        return Native.getId(ref);
    }

    public String getSymbol() {
        return Native.getSymbol(ref);
    }

    public int getWidth() {
        return Native.getWidthNode(ref);
    }

    public void assertForm() {
        Native.assertForm(ref);
    }

    public void assume() {
        Native.assume(ref);
    }
    //dsfjsklxfjksdljfklsdjfklsdjfklsjfkls
    public BitvecNode toBitvecNode() {
        if (isArrayNode()) throw new ClassCastException();
        return new BitvecNode(ref);
    }

    public BoolNode toBoolNode() {
        if (isBoolConst() || isBoolNode()) return new BoolNode(ref);
        else throw new ClassCastException();
    }

    public ArrayNode toArrayNode() {
        if (isArrayNode()) return new ArrayNode(ref);
        else throw new ClassCastException();
    }

    public boolean isBoolConst() {
       int kindObj = Native.kindNode(ref);
       if (kindObj==0) {
           kind = TypeNode.BOOLNODE;
           return true;
       }
        else return false;
    }

    public boolean isBitvecConst() {
        int kindObj = Native.kindNode(ref);
        if (kindObj==1) {
            kind = TypeNode.BITVECNODE;
            return true;
        }
        else return false;
    }
    public boolean isArrayNode(){
        if (kind == TypeNode.ARRAYNODE) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.ARRAYNODE;
        else return false;
    }

    public boolean isBoolNode() {
        if (kind == TypeNode.BOOLNODE) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.BOOLNODE;
        else return false;
    }

    public boolean isBitvecNode() {
        if (kind == TypeNode.BITVECNODE) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.BITVECNODE;
        else return false;
    }



    private TypeNode kindNode() {
        int kindObj = Native.kindNode(ref);
        switch (kindObj) {
            case 0:
                kind = TypeNode.BOOLNODE;
                return kind;
            case 1:
                kind = TypeNode.BITVECNODE;
            return kind;
            case 2:
                kind = TypeNode.ARRAYNODE;
            return kind;
            case 3:
                kind = TypeNode.BOOLNODE;
            return kind;
            case 4:
                kind = TypeNode.BITVECNODE;
            return kind;
            default:
                kind = TypeNode.UNKNOWN;
            return kind;
        }
    }

    /*public BoolectorNode xor(BoolectorNode node) {
        return new BoolectorNode(Native.xor(ref, node.ref));
    }*/
}


