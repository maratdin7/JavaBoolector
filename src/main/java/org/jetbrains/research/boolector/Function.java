package org.jetbrains.research.boolector;

import java.util.List;

public class Function extends BoolectorFun {
    final private long[] params;

    private Function(long ref, long[] params) {
        super(ref);
        this.params = params;
    }

    public BoolectorNode apply(List<BoolectorNode> argNodesFunc) {
        long[] argNodes = toLong(argNodesFunc.toArray(new BoolectorNode[0]));
        return new BoolectorNode(Native.apply(argNodes, argNodes.length, ref));
    }

    public long[] getParams() {
        return params;
    }

    static Function func(BoolectorNode nodeBody, List<FuncParam> func_params) {
        long[] params = toLong(func_params.toArray(new FuncParam[0]));
        return new Function(Native.fun(params, params.length, nodeBody.ref), params);
    }

    static Function forAll(BoolectorNode nodeBody, List<FuncParam> func_params) {
        long[] params = toLong(func_params.toArray(new FuncParam[0]));
        return new Function(Native.forAll(params, params.length, nodeBody.ref), params);
    }
}
