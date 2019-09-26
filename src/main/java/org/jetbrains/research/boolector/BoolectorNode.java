package org.jetbrains.research.boolector;

public class BoolectorNode extends BoolectorObject {
    private String name;
    private TypeNode kind;
    protected static int numberOfNames;
    private Integer width;

    BoolectorNode(Btor btor, long ref) {
        super(btor, ref);
    }

    BoolectorNode(Btor btor, long ref, String name, TypeNode type) {
        super(btor, ref);
        this.name = name;
        this.width = getWidthSort();
        kind = type;
    }

    public int getWidth() {
        return width;
    }

    public void release() {
        Native.releaseNode(btor.getRef(), ref);
    }

    public BoolectorNode copy() {
        return new BoolectorNode(btor, Native.copy(btor.getRef(), ref), name, kind);
    }

    public BoolNode eq(BoolectorNode node) {
        assert this.btor == node.btor;
        return new BoolNode(btor, Native.eq(btor.getRef(), ref, node.getRef()));
    }

    public BoolectorNode ite(BoolNode cond, BoolectorNode elseNode) {
        assert (this.btor == cond.btor && this.btor == elseNode.btor);
        return new BoolectorNode(btor, Native.cond(btor.getRef(), cond.getRef(), ref, elseNode.getRef()));
    }

    public BoolectorSort getSort() {//если уже изменялся все сломается
        return new BoolectorSort(btor, Native.getSort(btor.getRef(), ref), getWidth());
    }

    public int getID() {
        return Native.getId(btor.getRef(), ref);
    }

    public String getSymbol() {
        if (name == null) name = Native.getSymbol(btor.getRef(), ref);
        return name;
    }

    private int getWidthSort() {
        return Native.getWidthNode(btor.getRef(), ref);
    }

    public void assertForm() {
        Native.assertForm(btor.getRef(), ref);
    }

    public void assume() {
        Native.assume(btor.getRef(), ref);
    }

    public BitvecNode toBitvecNode() {
        if (isArrayNode()) throw new ClassCastException();
        return new BitvecNode(btor, ref, null);
    }

    public BitvecNode toBitvecNode(int castSize) {
        BitvecNode node = toBitvecNode();
        int curSize = getWidth();
        if (curSize == castSize) return node;
        else if (curSize < castSize) return node.sext(castSize);
        else return node.slice(castSize, 0);
    }

    public BoolNode toBoolNode() {
        if (isBoolNode() || (isBitvecNode() && width == 1)) return new BoolNode(btor, ref);
        else throw new ClassCastException();
    }

    public ArrayNode toArrayNode() {
        if (isArrayNode()) return new ArrayNode(btor, ref, name);
        else throw new ClassCastException();
    }

    public boolean isArrayNode() {
        if (kind == TypeNode.ARRAYNODE) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.ARRAYNODE;
        else return false;
    }

    public boolean isBoolNode() {
        if (kind == TypeNode.BOOLNODE || (isBitvecNode() && width == 1)) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.BOOLNODE;
        else return false;
    }

    public boolean isBitvecNode() {
        if (kind == TypeNode.BITVECNODE) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.BITVECNODE;
        else return false;
    }

    private TypeNode kindNode() {
        int kindObj = Native.kindNode(btor.getRef(), ref);
        switch (kindObj) {
            case 0:
            case 3:
                kind = TypeNode.BOOLNODE;
                return kind;
            case 1:
            case 4:
                kind = TypeNode.BITVECNODE;
                return kind;
            case 2:
                kind = TypeNode.ARRAYNODE;
                return kind;
            default:
                kind = TypeNode.UNKNOWN;
                return kind;
        }
    }
}


