package org.jetbrains.research.boolector;

public class BoolectorNode extends BoolectorObject {
    public enum Kind {
        BOOLNODE(0),
        BITVECNODE(1),
        ARRAYNODE(2),
        UNKNOWN;

        private final int intValue;

        Kind() {
            this.intValue = -1;
        }

        Kind(int value) {
            this.intValue = value;
        }

        public static Kind fromInt(int i) {
            if (BOOLNODE.toInt() == i) return BOOLNODE;
            if (BITVECNODE.toInt() == i) return BITVECNODE;
            if (ARRAYNODE.toInt() == i) return BITVECNODE;

            // special case for booleans and floats
            if (i == 3) return BOOLNODE;
            if (i == 4) return BITVECNODE;

            return UNKNOWN;
        }

        public final int toInt() {
            return this.intValue;
        }
    }

    private Kind kind = Kind.UNKNOWN;

    BoolectorNode(long ref) {
        super(ref);
    }

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
        if (kindObj == 0) {
            kind = Kind.BITVECNODE;
            return true;
        } else return false;
    }

    public boolean isBitvecConst() {
        int kindObj = Native.kindNode(ref);
        if (kindObj == 1) {
            kind = Kind.BITVECNODE;
            return true;
        } else return false;
    }

    public boolean isArrayNode() {
        return getKind() == Kind.ARRAYNODE;
    }

    public boolean isBoolNode() {
        return getKind() == Kind.BOOLNODE;
    }

    public boolean isBitvecNode() {
        return (getKind() == Kind.BITVECNODE);
    }

    private Kind getKind() {
        if (kind != Kind.UNKNOWN) return kind;

        int kindObj = Native.kindNode(ref);
        kind = Kind.fromInt(kindObj);
        return kind;
    }
}


