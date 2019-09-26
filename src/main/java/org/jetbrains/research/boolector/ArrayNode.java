package org.jetbrains.research.boolector;

public class ArrayNode extends BoolectorNode {

    ArrayNode(long ref, String name, Integer width) {
        super(ref, name, width, TypeNode.ARRAYNODE);
    }

    public static ArrayNode arrayNode(ArraySort sort, String name) {
        name = name + "!" + numberOfNames++;
        return new ArrayNode(Native.array(sort.ref, name), name, sort.getWidth());
    }

    public static ArrayNode constArrayNode(BitvecSort indexSort, BitvecNode element) {
        BitvecSort elementsSort = element.getSort().toBitvecSort();
        ArraySort arraySort = ArraySort.arraySort(indexSort, elementsSort);
        return new ArrayNode(Native.constArray(arraySort.ref, indexSort.ref, element.ref), null, element.getWidth());
    }

    public int getIndexWidth() {
        return Native.getIndexWidth(ref);
    }

    public BitvecNode read(BitvecNode index) {
        return new BitvecNode(Native.read(ref, index.ref), null, null);
    }

    public ArrayNode write(BitvecNode index, BitvecNode value) {
        int widthIndex = getIndexWidth();
        int widthElement = getWidth();
        if (index.getWidth() != widthIndex) index.toBitvecNode(widthIndex);
        if (value.getWidth() != widthElement) value.toBitvecNode(widthElement);
        return new ArrayNode(Native.write(ref, index.ref, value.ref), null, widthElement);
    }
}
