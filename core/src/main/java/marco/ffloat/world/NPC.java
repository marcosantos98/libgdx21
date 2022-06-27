package marco.ffloat.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.World;
import marco.ffloat.anim.IAnimatable;
import marco.ffloat.anim.MarkerAnim;

public class NPC extends Entity implements IAnimatable {

    private final Sprite marker = new Sprite(new Texture(Gdx.files.internal("player.png")));

    private float maxY;
    private float minY;

    private MarkerAnim anim;

    public NPC(Texture texture, int x, int y, int width, int height, World<Entity> world) {
        super(texture, x, y, width, height, world, false);

        this.marker.setBounds(x + (width >> 1) - 8, y + height + 4, 16, 16);

        this.maxY = this.marker.getY() + 4;
        this.minY = this.marker.getY();

        this.anim = new MarkerAnim(this);

        setZIndex(999);
    }

    @Override
    public void update(float delta) {
        this.anim.update();
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.sprite().draw(batch);
        this.marker.draw(batch);
    }

    @Override
    public float[] getValues() {
        return new float[]{
                this.marker.getY(),
                this.maxY,
                this.minY
        };
    }

    @Override
    public void setValues(int index, float values) {
        if (index == 0) {
            this.marker.setY(values);
        }
    }
}
