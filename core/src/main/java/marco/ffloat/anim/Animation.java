package marco.ffloat.anim;

import java.util.ArrayList;
import java.util.List;

public class Animation<T> {

    public abstract static class State<T> {

        protected T animatable;
        protected boolean isFinished;


        public State(T animatable) {
            this.animatable = animatable;
        }

        public abstract void update();
    }

    private final List<State<T>> states = new ArrayList<>();

    private int index;
    private boolean stop;

    public void add(State<T> state) {
        this.states.add(state);
    }

    public void start() {
        this.stop = false;
    }

    public void stop() {
        this.stop = true;
    }

    public void update() {
        if(this.stop && this.index == 0) return;  //Let the animation stop
        State<T> s = this.states.get(this.index);
        if (s.isFinished) this.advanceIndex();
        else s.update();
    }

    private void advanceIndex() {
        if (this.index + 1 > this.states.size() - 1) {
            this.states.forEach(s -> s.isFinished = false);
            this.index = 0;
        } else this.index++;
    }
}
