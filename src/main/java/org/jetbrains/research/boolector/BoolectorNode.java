package org.jetbrains.research.boolector;

public abstract class BoolectorNode extends BoolectorObject {
    protected static int numberOfNames;

    protected String name;
    private TypeNode kind;
    private Integer width;
    private BoolectorSort sort;

    BoolectorNode(Btor btor, long ref) {
        super(btor, ref);
    }

    BoolectorNode(Btor btor, long ref, String name, TypeNode type) {
        super(btor, ref);
        this.name = name;
        this.width = getWidthSort();
        this.kind = type;
        this.sort = BoolectorSort.fromSort(btor, Native.getSort(btor.getRef(), ref), getWidth());
    }

    static BoolectorNode create(Btor btor, long ref, String name) {
        Integer width = Native.getWidthNode(btor.getRef(), ref);
        BoolectorSort sort = BoolectorSort.fromSort(btor, Native.getSort(btor.getRef(), ref), width);
        if (sort.isArraySort()) return new ArrayNode(btor, ref, name);
        else if (sort.isBitvecSort()) return new BitvecNode(btor, ref, name);
        else return new BoolNode(btor, ref);
    }

    public int getWidth() {
        return width;
    }

    public void release() {
        Native.releaseNode(btor.getRef(), ref);
    }

    public BoolectorNode copy() {
        return BoolectorNode.create(btor, Native.copy(btor.getRef(), ref), name);
    }

    public BoolNode eq(BoolectorNode node) {
        assert this.btor == node.btor;
        return new BoolNode(btor, Native.eq(btor.getRef(), ref, node.getRef()));
    }

    public BoolectorNode ite(BoolNode cond, BoolectorNode elseNode) {
        assert (this.btor == cond.btor && this.btor == elseNode.btor);
        return BoolectorNode.create(btor, Native.cond(btor.getRef(), cond.getRef(), ref, elseNode.getRef()), null);
    }

    public BoolectorSort getSort() {
        return BoolectorSort.fromSort(btor, Native.getSort(btor.getRef(), ref), getWidth());
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

    public BitvecNode toBitvecNode(int castSize) {
        BitvecNode node = (BitvecNode) this;
        int curSize = getWidth();
        if (curSize == castSize) return node;
        else if (curSize < castSize) return node.sext(castSize);
        else return node.slice(castSize, 0);
    }

    public boolean isArrayNode() {
        return kind == TypeNode.ARRAYNODE;
    }

    public boolean isBoolNode() {
        return kind == TypeNode.BOOLNODE || (isBitvecNode() && width == 1);
    }

    public boolean isBitvecNode() {
        return kind == TypeNode.BITVECNODE;
    }
}


