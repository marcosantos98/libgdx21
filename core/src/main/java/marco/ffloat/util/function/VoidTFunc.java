package marco.ffloat.util.function;

@FunctionalInterface
public interface VoidTFunc<A, B, C> {

    void apply(A a, B b, C c);
}
