package marco.ffloat.util.function;

public interface TFunc<A, B, C, R> {

    R apply(A a, B b, C c);
}
