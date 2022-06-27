package marco.ffloat.world;

import com.badlogic.gdx.graphics.Texture;
import marco.ffloat.Assets;

public enum ObstacleType {
    DEAD_BIRD(Assets.player.get(), 32, 32, 20),
    BREAKOUT(Assets.player.get(), 100, 20, 20);

    public final Texture texture;
    public final int width;
    public final int height;
    public final int lp;

    ObstacleType(Texture texture, int width, int height, int lp) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.lp = lp;
    }
}
