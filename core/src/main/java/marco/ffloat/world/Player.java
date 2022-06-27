package marco.ffloat.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import marco.ffloat.Assets;
import marco.ffloat.Float;
import marco.ffloat.event.StartSpawnerEvent;
import marco.ffloat.particle.ParticleSystem;
import marco.ffloat.particle.RocketParticle;
import marco.ffloat.screen.FirstScreen;

public class Player extends Entity {

    public static int destroyed;
    public static int allMoney;
    private float bulletSpawnTimer = 1.1f;
    private final float bulletSpawnTime = .2f;
    public boolean canShoot = false;
    public boolean appear;
    public int money;

    public int dmg = 10;

    public Player(Texture texture, int x, int y, int width, int height, World<Entity> world, boolean initialAdd) {
        super(texture, x, y, width, height, world, initialAdd);
        this.setZIndex(1000);
    }

    @Override
    public void update(float delta) {

        if (this.appear) {
            float newY = this.y() + 300 * delta;
            if (newY >= 48) {
                newY = 48;
                this.appear = false;
                FirstScreen.eventManager.sendEvent(new StartSpawnerEvent());
            } else this.setY(newY);
        }

        float newX = this.x();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= 500f * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += 500f * delta;
        }

        if (newX > Gdx.graphics.getWidth() - 32) newX = Gdx.graphics.getWidth() - 32;
        if (newX < 0) newX = 0;

        this.bulletSpawnTimer += delta;

        if (this.canShoot) {
            if (this.bulletSpawnTimer > this.bulletSpawnTime) {
                this.spawnBullet();
                this.bulletSpawnTimer = 0f;
            }
        }

        Response.Result move = this.world().move(this.entityItem(), newX, this.y() + 0.01f, Collisions.player);
        this.setX(newX);

        ParticleSystem.addParticle(new RocketParticle(this.x() + 16 - 5, this.y() - 10));
    }

    private void spawnBullet() {
        new Bullet(this, this.x() + 16 - 5, this.y() + 32, 10, 16, this.world(), true);
        Assets.shoot.get().play(Float.sound);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.sprite().setPosition(this.x(), this.y());
        this.sprite().draw(batch);
    }
}
