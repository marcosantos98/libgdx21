package marco.ffloat.particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import marco.ffloat.Assets;

public class ObstacleDeadParticle implements IParticle {

    private float startX;
    private float startY;
    private float dirX;
    private float dirY;
    private float lifetime = 50;

    public ObstacleDeadParticle(float startX, float startY) {
        this.startX = startX;
        this.startY = startY;
        float angle = (float)(MathUtils.random() * 2 * Math.PI);
        float t = MathUtils.random(0.5f, 1.5f);
        this.dirX = MathUtils.cos(angle) * 4f * t;
        this.dirY = MathUtils.sin(angle) * 4f * t;
    }

    @Override
    public boolean isDead() {
        return lifetime <= 0;
    }

    @Override
    public void tick(float delta) {
        this.startX += dirX;
        this.startY += dirY;
        lifetime--;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(Assets.platform.get(), this.startX, this.startY, 10, 10);
    }

    @Override
    public void dispose() {

    }
}
