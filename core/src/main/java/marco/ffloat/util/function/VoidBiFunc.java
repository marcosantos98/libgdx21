package marco.ffloat.util.function;

@FunctionalInterface
public interface VoidBiFunc<A, B> {

    void apply(A a, B b);
}
