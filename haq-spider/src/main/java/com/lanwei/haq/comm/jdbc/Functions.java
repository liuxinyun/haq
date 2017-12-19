package com.lanwei.haq.comm.jdbc;

/**
 * @author liuxinyun
 */
public class Functions {
    private Functions() {
    }

    public interface Function1<A, B> {
        B apply(A var1);
    }

    public interface Function2<F, G, T> {
        T apply(F var1, G var2);
    }

    public interface Function3<T> {
        void apply(T arg);
    }

    public interface Function0<T> {
        T apply();
    }

    /*public static <A, B, G> Functions.Function1<G, B> compose(final Functions.Function1<A, B> f, final Functions.Function1<G, A> g) {
        return new Functions.Function1() {
            public B apply(G arg) {
                return f.apply(g.apply(arg));
            }
        };
    }

    public static <A, B, G> Functions.Function1<A, G> andThen(final Functions.Function1<A, B> f, final Functions.Function1<B, G> g) {
        return new Functions.Function1() {
            public G apply(A arg) {
                return g.apply(f.apply(arg));
            }
        };
    }*/
}
