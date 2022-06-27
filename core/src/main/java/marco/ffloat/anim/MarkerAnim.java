package marco.ffloat.anim;

public class MarkerAnim extends Animation<IAnimatable> {

    //0 - y
    //1 - maxY
    //2 - minY

    public MarkerAnim(IAnimatable entity) {
        this.add(new AnimationState<>(entity, (state) -> {
            if (entity.getValues()[0] <= entity.getValues()[1]) {
                entity.setValues(0, entity.getValues()[0] + .5f);
            } else state.isFinished = true;
        }));
        this.add(new AnimationState<>(entity, (state) -> {
            if (entity.getValues()[0] >= entity.getValues()[2]) {
                entity.setValues(0, entity.getValues()[0] - .5f);
            } else state.isFinished = true;
        }));
    }
}
