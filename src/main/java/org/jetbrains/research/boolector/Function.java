package org.jetbrains.research.boolector;

import java.util.List;

public class Function extends BoolectorFun {
    final private long[] params;

    private Function(Btor btor, long ref, long[] params) {
        super(btor, ref);
        this.params = params;
    }

    public BoolectorNode apply(List<BoolectorNode> argNodesFunc) {
        long[] argNodes = toLong(argNodesFunc.toArray(new BoolectorNode[0]));
        return BoolectorNode.create(btor, Native.apply(btor.getRef(), argNodes, argNodes.length, ref), null);
    }

    public long[] getParams() {
        return params;
    }

    static Function func(BoolectorNode nodeBody, List<FuncParam> funcParams) {
        Btor btor = nodeBody.getBtor();
        long[] params = toLong(funcParams.toArray(new FuncParam[0]));
        return new Function(btor, Native.fun(btor.getRef(), params, params.length, nodeBody.getRef()), params);
    }

    static Function forAll(BoolectorNode nodeBody, List<FuncParam> funcParams) {
        Btor btor = nodeBody.getBtor();
        long[] params = toLong(funcParams.toArray(new FuncParam[0]));
        return new Function(btor, Native.forAll(btor.getRef(), params, params.length, nodeBody.getRef()), params);
    }
}
