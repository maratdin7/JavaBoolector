package org.jetbrains.research.boolector;

public class BoolectorNode extends BoolectorObject {

    BoolectorNode(long ref) {
        super(ref);
    }
    BoolectorNode(long ref, String name, Integer width, TypeNode type) {
        super(ref);
        this.name = name;
        this.width = width;
        kind = type;
    }

    private String name;

    private TypeNode kind = TypeNode.UNKNOWN; //jdkgjdflkgjkfjglkdfjgklfdjgkljfdkgjfdklgjdfjglkdgjkldfjkgljfkl

    protected static int numberOfNames;

    private Integer width;

    public int getWidth() {
        if (width == null) width = getWidthSort();
        return width;
    }

    protected void setWidth(Integer newWidth) {
        width = newWidth;
    }

    public void release() {
        Native.releaseNode(ref);
    }

    public BoolectorNode copy() {
        return new BoolectorNode(Native.copy(ref), name, width, kind);
    }

    public BoolNode eq(BoolectorNode node) {
        return new BoolNode(Native.eq(ref, node.ref));
    }

    public BoolectorNode ite(BoolNode cond, BoolectorNode elseNode) {
        return new BoolectorNode(Native.cond(cond.ref, ref, elseNode.ref)); //fdjfksdjfkldsjfksdjfkljdskfjsdkfsjklfsfkdsfjklds
    }

    public BoolectorSort getSort() {//если уже изменялся все сломается
        return new BoolectorSort(Native.getSort(ref), getWidth());
    }

    public int getID() {
        return Native.getId(ref);
    }

    public String getSymbol() {
        if (name == null) name = Native.getSymbol(ref);
        return name;
    }

    private int getWidthSort() {
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
        return new BitvecNode(ref, null, getWidth());
    }

    public BitvecNode toBitvecNode(int castSize) {
        BitvecNode node = toBitvecNode();
        int curSize = getWidth();
        if (curSize == castSize) return node;
        else if (curSize < castSize) return node.sext(castSize);
        else return node.slice(castSize, 0);
    }

    public BoolNode toBoolNode() {
        if (isBoolNode() || (isBitvecNode() && width==1)) return new BoolNode(ref);
        else throw new ClassCastException();
    }

    public ArrayNode toArrayNode() {
        if (isArrayNode()) return new ArrayNode(ref,name, width);
        else throw new ClassCastException();
    }

    public boolean isArrayNode() {
        if (kind == TypeNode.ARRAYNODE) return true;
        if (kind == TypeNode.UNKNOWN) return kindNode() == TypeNode.ARRAYNODE;
        else return false;
    }

    public boolean isBoolNode() {
        if (kind == TypeNode.BOOLNODE || (isBitvecNode() && width==1)) return true;
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


