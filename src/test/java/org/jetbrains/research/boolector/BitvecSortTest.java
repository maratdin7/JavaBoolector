package org.jetbrains.research.boolector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BitvecSortTest {

    @Test
    public void bitvecSort() {
        Btor btor = new Btor();
        BitvecSort sort = BitvecSort.bitvecSort(btor, 8);
        assertTrue(sort.isBitvecSort());
        assertEquals(8, sort.getWidth());
        btor.release();
    }
}