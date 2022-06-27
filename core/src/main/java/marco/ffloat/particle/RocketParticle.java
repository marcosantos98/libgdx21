package marco.ffloat.particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import marco.ffloat.Assets;

public class RocketParticle implements IParticle {

    private int lifetime = 10;
    private float startX;
    private float startY;
    private float dirX;
    private float dirY;

    public RocketParticle(float x, float y) {
        this.startX = x;
        this.startY = y;
        float angle = (float)(MathUtils.random() * 2 * Math.PI / 2);
        float t = MathUtils.random(0.5f, 1.5f);
        this.dirX = MathUtils.cos(angle) * 2f * t;
    }

    @Override
    public boolean isDead() {
        return this.lifetime <= 0;
    }

    @Override
    public void tick(float delta) {
        this.startX += this.dirX;
        this.startY -= 100 * delta;
        this.lifetime--;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(Assets.platform.get(), this.startX, this.startY, 10, 10);
    }

    @Override
    public void dispose() {

    }
}
