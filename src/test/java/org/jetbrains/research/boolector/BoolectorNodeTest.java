package org.jetbrains.research.boolector;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoolectorNodeTest {

    @Test
    public void bitvecNodeFirst() {
        Btor btor = new Btor();
        BitvecSort sort = BitvecSort.bitvecSort(8);
        BitvecNode x = BitvecNode.var(sort, "nullINc", true);
        BitvecNode y = BitvecNode.var(sort, "nullINc", true);
        BitvecNode ansXor = x.xor(y);
        BitvecNode ansOr = andNot(x, y).or(andNot(y, x));
        BoolNode eq = ansXor.eq(ansOr);
        BoolNode formula = eq.not();
        formula.assertForm();
        Btor.Status result = btor.check();
        assertEquals(Btor.Status.UNSAT, result);
        btor.release();
    }

    private BitvecNode andNot(BitvecNode x, BitvecNode y) {
        return x.and(y.not());
    }

    @Test
    public void bitvecNodeSecond() {
        Btor btor = new Btor();
        BitvecSort sort = BitvecSort.bitvecSort(8);
        BitvecNode x = BitvecNode.var(sort, "nullINc", true);
        BitvecNode y = BitvecNode.var(sort, "nullINc", true);
        BitvecNode zero = BitvecNode.zero(sort);
        BoolNode xSgtZero = x.sgt(zero);
        BoolNode ySgtZero = y.sgt(zero);
        BoolNode varsSgtZero = xSgtZero.and(ySgtZero);

        BitvecNode add = x.add(y);   // saddo???
        BoolNode addSgtZero = add.sgt(zero);
        BoolNode overflow = add.sgt(x);
        BoolNode varsSgt = varsSgtZero.and(overflow);
        BoolNode ans = varsSgt.implies(addSgtZero);
        btor.simplify();
        assertFormulae(btor, ans);
    }

    @Test
    public void BitveNodeTest() {
        Btor btor = new Btor();
        BitvecNode x, y, longConst, sext, uext, slice, neg, add, sub, mul, sdiv, udiv, smod, urem, sll, srl, sra, concat;
        BoolNode sgt, sgte, slt, slte;
        x = BitvecNode.constBitvec("000101");
        y = BitvecNode.constBitvec("000011");
        BitvecSort long_sort = BitvecSort.bitvecSort(64);
        longConst = BitvecNode.constLong(3000000000L, long_sort);


        sext = x.sext(10);
        uext = x.uext(10);
        slice = x.slice(2, 0);
        neg = x.neg();
        add = x.add(y);
        sub = x.sub(y);
        mul = x.mul(y);
        sdiv = x.sdiv(y);
        udiv = x.udiv(y);
        smod = x.smod(y);
        urem = x.urem(y);
        sgt = x.sgt(y);
        sgte = x.sgte(y);
        slt = x.slt(y);
        slte = x.slte(y);
        sll = x.sll(y);
        srl = x.srl(y);
        sra = x.sra(y);
        concat = x.concat(y);
        BitvecNode var = BitvecNode.var(long_sort, "test", false);
        BitvecNode noFresh = BitvecNode.var(long_sort, "test", false);
        x = BitvecNode.constInt(-5, long_sort);
        btor.check();

        long assignment = x.assignment();
        BoolNode test = BoolNode.constBool(true);
        boolectorAssert("0000000000000000000000000000000010110010110100000101111000000000", longConst);
        boolectorAssert("0000000101", sext);
        boolectorAssert("0000000101", uext);
        boolectorAssert("101", slice);
        boolectorAssert("111011", neg);
        boolectorAssert("001000", add);
        boolectorAssert("000010", sub);
        boolectorAssert("001111", mul);
        boolectorAssert("000001", sdiv);
        boolectorAssert("000001", udiv);
        boolectorAssert("000010", smod);
        boolectorAssert("000010", urem);
        boolectorAssert("1", sgt);
        boolectorAssert("1", sgte);
        boolectorAssert("0", slt);
        boolectorAssert("0", slte);
        boolectorAssert("101000", sll);
        boolectorAssert("000000", srl);
        boolectorAssert("000000", sra);
        boolectorAssert("000101000011", concat);
        assertEquals(-5, assignment);
        assertEquals(var.ref, noFresh.ref);
        assertTrue(test.assigment());
        btor.release();
    }

    @Test
    public void BoolNodeTest() {
        Btor btor = new Btor();
        BoolNode x, y, or, xor, iff;
        x = BoolNode.constBool(true);
        y = BoolNode.constBool(false);
        or = x.or(y);
        xor = x.xor(y);
        iff = x.iff(y);
        btor.check();
        boolectorAssert("1", or);
        boolectorAssert("1", xor);
        boolectorAssert("0", iff);
        btor.release();
    }

    @Test
    public void boolectorNodeTest() {
        Btor btor = new Btor();
        BoolectorNode x, y, bool, bitvec, ite;

        x = BitvecNode.constBitvec("000101");
        y = BitvecNode.constBitvec("000011");

        bool = BitvecNode.constBitvec("1");
        ite = x.ite(bool.toBoolNode(), y);
        BoolectorSort getSort = x.getSort();
        bitvec = BitvecNode.var(getSort.toBitvecSort(), "test", false);
        x.getID();
        btor.check();
        boolectorAssert("000101", ite);
        assertEquals("test", bitvec.getSymbol());
        assertFalse(x.isBoolNode());
        assertTrue(bitvec.isBitvecNode());
        int i = 0;
        try {
            bitvec.toArrayNode();
        } catch (ClassCastException e) {
            ++i;
        }
        assertEquals(1, i);
        btor.release();
    }

    @Test
    public void tempFiles() {
        Btor btor = new Btor();
        BoolNode x = BoolNode.constBool(true);
        x.assertForm();
        btor.check();
        assertEquals("(model )\n", btor.printModel());
        assertEquals("(set-logic QF_BV)\n" +
                "(assert true)\n" +
                "(check-sat)\n" +
                "(exit)\n", btor.dumpSmt2());
        btor.release();
    }

    @Test
    public void arrayNodeTest() {
        Btor btor = new Btor();
        BitvecNode x, i, j;
        ArrayNode arrayConst, array;
        BitvecSort index;

        x = BitvecNode.constBitvec("000101");
        i = BitvecNode.constBitvec("000000");
        j = BitvecNode.constBitvec("100000");

        index = x.getSort().toBitvecSort();
        ArraySort sort = ArraySort.arraySort(index, index);
        array = ArrayNode.arrayNode(sort, "Temp");

        arrayConst = ArrayNode.constArrayNode(index, x);
        BoolNode eq = arrayConst.read(i).eq(arrayConst.read(j));
        btor.check();
        boolectorAssert("1", eq);
        btor.release();
    }

    @Test
    public void arrayNodeTest1() {
        Btor btor = new Btor();

        BitvecSort sortIndex = BitvecSort.bitvecSort(3);
        BitvecSort sortElem = BitvecSort.bitvecSort(8);
        ArraySort sortArray = ArraySort.arraySort(sortIndex, sortElem);

        BitvecNode[] indices = new BitvecNode[8];
        for (int i = 0; i < 8; i++) {
            indices[i] = BitvecNode.constInt(i, sortIndex);
        }
        ArrayNode array = ArrayNode.arrayNode(sortArray, "testArray");
        BitvecNode max = array.read(indices[0]);
        BitvecNode read;

        for (int i = 1; i < 8; i++) {
            read = array.read(indices[i]);
            BoolNode ugt = read.ugt(max);
            BitvecNode temp = read.ite(ugt, max).toBitvecNode();
            max.release();
            max = temp;
            read.release();
            ugt.release();
        }
        BitvecNode index = BitvecNode.var(sortIndex, null, true);
        read = array.read(index);

        BoolNode formula = read.ugt(max);

        formula.assertForm();
        Btor.Status result = btor.check();
        assertEquals(Btor.Status.UNSAT, result);
        btor.release();
    }

    @Test
    public void arrayNodeTest2() {
        Btor btor = new Btor();

        BitvecSort sortIndex = BitvecSort.bitvecSort(1);
        BitvecSort sortElem = BitvecSort.bitvecSort(8);
        ArraySort sortArray = ArraySort.arraySort(sortIndex, sortElem);

        ArrayNode array = ArrayNode.arrayNode(sortArray, null);
        BitvecNode index1 = BitvecNode.var(sortIndex, null, true);
        BitvecNode index2 = BitvecNode.var(sortIndex, null, true);
        BitvecNode read1 = array.read(index1);
        BitvecNode read2 = array.read(index2);
        BoolectorNode eq = index1.eq(index2);
        BoolectorNode ne = read1.eq(read2).not();
        eq.assertForm();
        Btor.Status result = btor.check();
        assertEquals(Btor.Status.SAT, result);
        ne.assume();
        result = btor.check();
        assertEquals(Btor.Status.UNSAT, result);
        result = btor.check();
        assertEquals(Btor.Status.SAT, result);
        btor.release();
    }

    private static void assertFormulae(Btor btor, BoolNode node) {
        BoolNode formula = node.not();
        formula.assertForm();
        Btor.Status ans = btor.check();
        assertEquals(Btor.Status.UNSAT, ans);
        btor.release();
    }

    private static void boolectorAssert(String ans, BoolectorNode node) {
        assertTrue(Native.boolectorAssert(ans, node.ref));
    }
}