package org.jetbrains.research.boolector;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class BoolectorNodeTest {

    @Test
    public void bitvecNodeFirst() {
        Btor btor = new Btor();
        BitvecSort sort = BitvecSort.bitvecSort(8);
        BitvecNode x = btor.var(sort, "nullINc", true);
        BitvecNode y = btor.var(sort, "nullINc", true);
        BitvecNode ansXor = x.xor(y);
        BitvecNode ansOr = andNot(x, y).or(andNot(y, x));
        BoolNode eq = ansXor.eq(ansOr);
        BoolNode formula = eq.not();
        formula.assertForm();
        Btor.Status result = btor.check();
        assertEquals(Btor.Status.UNSAT, result);
        btor.btorRelease();
    }

    private BitvecNode andNot(BitvecNode x, BitvecNode y) {
        return x.and(y.not());
    }

    @Test
    public void bitvecNodeSecond() {
        Btor btor = new Btor();
        BitvecSort sort = BitvecSort.bitvecSort(8);
        BitvecNode x = btor.var(sort, "nullINc", true);
        BitvecNode y = btor.var(sort, "nullINc", true);
        BitvecNode zero = btor.zero(sort);
        BoolNode xSgtZero = x.sgt(zero);
        BoolNode ySgtZero = y.sgt(zero);
        BoolNode varsSgtZero = xSgtZero.and(ySgtZero);

        BitvecNode add = x.add(y);
        BoolNode addSgtZero = add.sgt(zero);
        BoolNode overflow = add.sgt(x);
        BoolNode varsSgt = varsSgtZero.and(overflow);
        BoolNode ans = varsSgt.implies(addSgtZero);
        btor.simplify();
        assertFormuls(btor, ans);
    }

    @Test
    public void BitveNodeTest() {
        Btor btor = new Btor();
        BitvecNode x, y, longConst, sext, uext, slice, neg, add, sub, mul, sdiv, udiv, smod, urem, sll, srl, sra, concat;
        BoolNode sgt, sgte, slt, slte;
        x = btor.constBitvec("000101");
        y = btor.constBitvec("000011");
        BitvecSort longSort = BitvecSort.bitvecSort(64);
        longConst = btor.constLong(3000000000L, longSort);


        sext = x.sext(4);
        uext = x.uext(4);
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
        BitvecNode var = btor.var(longSort, "test", false);
        BitvecNode noFresh = btor.var(longSort, "test", false);
        x = BitvecNode.constInt(-5, longSort);
        btor.check();

        long assignment = x.assignment();
        BoolNode test = btor.constBool(true);
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
        btor.btorRelease();
    }

    @Test
    public void BoolNodeTest() {
        Btor btor = new Btor();
        BoolNode x, y, or, xor, iff;
        x = btor.constBool(true);
        y = btor.constBool(false);
        or = x.or(y);
        xor = x.xor(y);
        iff = x.iff(y);
        btor.check();
        boolectorAssert("1", or);
        boolectorAssert("1", xor);
        boolectorAssert("0", iff);
        btor.btorRelease();
    }

    @Test
    public void BoolectorNode() {
        Btor btor = new Btor();
        BoolectorNode x, y, bool, bitvec, ite;

        x = btor.constBitvec("000101");
        y = btor.constBitvec("000011");

        bool = btor.constBitvec("1");
        ite = x.ite(bool.toBoolNode(), y);
        BoolectorSort getSort = x.getSort();
        bitvec = btor.var(getSort.toBitvecSort(), "test", true);
        x.getID();
        btor.check();
        boolectorAssert("000101", ite);
        assertTrue(Pattern.compile("test(!\\d)?").matcher(bitvec.getSymbol()).matches());
        assertFalse(x.isBoolConst());
        assertFalse(bitvec.isBitvecConst());
        int i = 0;
        try {
            bitvec.toArrayNode();
        } catch (ClassCastException e) {
            ++i;
        }
        assertEquals(1, i);
        btor.btorRelease();
    }

    @Test
    public void arrayNode() {
        Btor btor = new Btor();
        BitvecNode x, y, i, j;
        ArrayNode arrayConst, array;
        BitvecSort index;

        x = btor.constBitvec("000101");
        y = btor.constBitvec("000011");
        i = btor.constBitvec("000000");
        j = btor.constBitvec("100000");

        index = x.getSort().toBitvecSort();
        ArraySort sort = ArraySort.arraySort(index, index);
        array = btor.arrayNode(sort, "Temp");

        arrayConst = btor.constArrayNode(index, x);
        BoolNode eq = arrayConst.read(i).eq(arrayConst.read(j));
        btor.check();
        boolectorAssert("1", eq);
        btor.btorRelease();
    }

    private static void assertFormuls(Btor btor, BoolNode node) {
        BoolNode formula = node.not();
        formula.assertForm();
        Btor.Status result = btor.check();
        assertEquals(Btor.Status.UNSAT, result);
        btor.btorRelease();
    }

    private static void boolectorAssert(String ans, BoolectorNode node) {
        assertTrue(Native.boolectorAssert(ans, node.ref));
    }
}