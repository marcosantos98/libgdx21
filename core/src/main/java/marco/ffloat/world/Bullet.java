package marco.ffloat.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import marco.ffloat.Assets;

public class Bullet extends Entity {

    private final Player player;

    public Bullet(Player player, float x, float y, int width, int height, World<Entity> world, boolean initialAdd) {
        super(Assets.platform.get(), x, y, width, height, world, initialAdd);
        this.player = player;
        this.setZIndex(50);
    }

    @Override
    public void update(float delta) {
        Response.Result move = this.world().move(this.entityItem(), this.x(), this.y() + 300f * delta, Collisions.bullet);
        this.setY(move.goalY);
        this.sprite().setY(move.goalY);

        for (int i = 0; i < move.projectedCollisions.size(); i++) {
            Collision collisions = move.projectedCollisions.get(i);
            if (collisions.other.userData instanceof Obstacle) {
                ((Obstacle) collisions.other.userData).onHit(this, this.player);
                this.remove();
            }
        }

    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.sprite().draw(batch);
    }

    public int damage() {
        return this.player.dmg;
    }
}
