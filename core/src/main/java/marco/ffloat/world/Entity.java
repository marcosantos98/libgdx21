package marco.ffloat.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

public abstract class Entity {

    private final Sprite sprite;
    private float x;
    private float y;
    private float width;
    private float height;
    private final Item<Entity> entityItem;
    private final World<Entity> world;
    private boolean remove;
    private int zIndex;

    public Entity(Texture texture, float x, float y, int width, int height, World<Entity> world, boolean initialAdd) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // FIXME: 6/24/22 
        if (texture == null) this.sprite = new Sprite();
        else this.sprite = new Sprite(texture);
        this.sprite.setBounds(x, y, width, height);

        this.entityItem = new Item<>(this);
        if (initialAdd) world.add(this.entityItem, x, y, width, height);
    }

    public int zIndex() {
        return zIndex;
    }

    public void setZIndex(int index) {
        this.zIndex = index;
    }

    public void remove() {
        this.remove = true;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch, float delta);

    public Sprite sprite() {
        return this.sprite;
    }

    public float x() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float y() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float width() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float height() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public World<Entity> world() {
        return this.world;
    }

    public Item<Entity> entityItem() {
        return this.entityItem;
    }

    public boolean wasRemoved() {
        return this.remove;
    }
}
