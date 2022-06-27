package marco.ffloat.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import marco.ffloat.Assets;
import marco.ffloat.Float;
import marco.ffloat.particle.ObstacleDeadParticle;
import marco.ffloat.particle.ParticleSystem;
import marco.ffloat.screen.GameOver;

public class Obstacle extends Entity {

    private int lp;
    private ObstacleType type;

    public Obstacle(int lp, ObstacleType type, World<Entity> world) {
        super(type.texture, 0, 0, type.width, type.height, world, false);
        this.lp = lp;
        setZIndex(50);
        this.type = type;
    }

    public Obstacle withPos(int x, int y) {
        this.setX(x);
        this.setY(y);
        return this;
    }

    public void onHit(Bullet bullet, Player player) {
        this.lp -= bullet.damage();
        if(lp <= 0) {
            for (int i = 0; i < 10; i++) {
                ParticleSystem.addParticle(new ObstacleDeadParticle(this.x(), this.y()));
            }
            Assets.explosion.get().play(Float.sound);
            this.remove();
            player.money += 1;
            Player.allMoney++;
            Player.destroyed++;
        }
    }

    @Override
    public void update(float delta) {

        if(wasRemoved()) return;

        this.checkForOutOfBounds();

        if(this.type == ObstacleType.DEAD_BIRD) {
            float newY = this.y() - 200f * delta;

            Response.Result move = this.world().move(this.entityItem(), this.x(), newY, Collisions.obstacle);

            for (int i = 0; i < move.projectedCollisions.size(); i++) {
                Collision collision = move.projectedCollisions.get(i);
                if(collision.other.userData instanceof Player) {
                    Assets.explosion.get().play(Float.sound);
                    Float.instance.setScreen(new GameOver());
                }
            }

            this.setY(move.goalY);
            this.sprite().setY(move.goalY);
            this.sprite().setX(this.x());
        }


    }

    private void checkForOutOfBounds() {
        if(this.y() + this.height() < 0f) this.remove();
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.sprite().setPosition(this.x(), this.y());
        this.sprite().draw(batch);
    }
}
