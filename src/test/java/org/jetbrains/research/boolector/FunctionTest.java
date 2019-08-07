package org.jetbrains.research.boolector;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FunctionTest {

    @Test
   public void testAll() {
        Btor btor = new Btor();
        BitvecSort sort = BitvecSort.bitvecSort(8);
        BitvecNode x = BitvecNode.var(sort, "nullINc", true);
        BitvecNode y = BitvecNode.var(sort, "nullINc", true);
        BitvecNode a= BitvecNode.constInt(10,sort);
        BitvecNode b = BitvecNode.constInt(20, sort);
        BitvecNode ab = a.add(b);
        BitvecNode temp = x.add(y);
        BoolectorFun.FuncParam firstParam = BoolectorFun.FuncParam.param(sort,"nullINc");
        BoolectorFun.FuncParam secondParam = BoolectorFun.FuncParam.param(sort,"nullINc");
        List<BoolectorFun.FuncParam> param = Arrays.asList(firstParam,secondParam);
        Function slt = Function.func(temp, param);
        List paramX =Arrays.asList(x,y);
        BitvecNode first = slt.apply(paramX).toBitvecNode();
        BitvecNode second = slt.apply(paramX).toBitvecNode();
        BoolNode eq = first.eq(second);

        assertFormuls(btor,eq);
    }

    public static void assertFormuls(Btor btor,BoolNode node) {
        BoolNode formula = node.not();
        formula.assertForm();
        Btor.Status result = btor.check();
        assertEquals(Btor.Status.UNSAT, result);

        btor.btorRelease();
    }

}