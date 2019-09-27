package org.jetbrains.research.boolector;

public class ArrayNode extends BoolectorNode {

    ArrayNode(Btor btor, long ref, String name) {
        super(btor, ref, name, TypeNode.ARRAYNODE);
    }

    public static ArrayNode arrayNode(ArraySort sort, String name) {
        Btor btor = sort.getBtor();
        name = name + "!" + numberOfNames++;
        return new ArrayNode(btor, Native.array(btor.getRef(), sort.getRef(), name), name);
    }

    public static ArrayNode constArrayNode(BitvecSort indexSort, BitvecNode element) {
        Btor btor = indexSort.getBtor();
        BitvecSort elementsSort = (BitvecSort) element.getSort();
        ArraySort arraySort = ArraySort.arraySort(indexSort, elementsSort);
        return new ArrayNode(btor,
                Native.constArray(btor.getRef(), arraySort.getRef(), indexSort.getRef(), element.getRef()),
                null);
    }

    public int getIndexWidth() {
        return Native.getIndexWidth(btor.getRef(), ref);
    }

    public BitvecNode read(BitvecNode index) {
        return new BitvecNode(btor, Native.read(btor.getRef(), ref, index.getRef()), null);
    }

    public ArrayNode write(BitvecNode index, BitvecNode value) {
        int widthIndex = getIndexWidth();
        if (index.getWidth() != widthIndex) index.toBitvecNode(widthIndex);
        return new ArrayNode(btor, Native.write(btor.getRef(), ref, index.getRef(), value.getRef()), null);
    }
}
