package marco.ffloat.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.World;

public class Platform extends Entity {
    public Platform(Texture texture, int x, int y, int width, int height, World<Entity> world, boolean initialAdd) {
        super(texture, x, y, width, height, world, initialAdd);
        setZIndex(999);
    }

    @Override
    public void update(float delta) {
        //NOOP
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.sprite().draw(batch);
    }
}
