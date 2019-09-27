package org.jetbrains.research.boolector;

public abstract class BoolectorFun extends BoolectorObject {

    BoolectorFun(Btor btor, long ref) {
        super(btor, ref);
    }

    public void release() {
        Native.releaseNode(btor.getRef(), ref);
    }

    public static class FuncParam extends BoolectorFun {
        FuncParam(Btor btor, long ref) {
            super(btor, ref);
        }

        public static FuncParam param(BoolectorSort sort, String name) {
            Btor btor = sort.getBtor();
            return name == null ?
                    new FuncParam(btor, Native.param(btor.getRef(), sort.getRef(), "nullInC")) :
                    new FuncParam(btor, Native.param(btor.getRef(), sort.getRef(), name));
        }
    }
}
