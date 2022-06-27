package marco.ffloat.anim;

import marco.ffloat.util.function.VoidFunc;

public class AnimationState<T extends IAnimatable> extends Animation.State<T> {

    private final VoidFunc<Animation.State<T>> updateFunc;

    public AnimationState(T animable, VoidFunc<Animation.State<T>> updateFunc) {
        super(animable);
        this.updateFunc = updateFunc;
    }

    @Override
    public void update() {
        updateFunc.apply(this);
    }
}
