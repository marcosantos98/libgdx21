package marco.ffloat.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import marco.ffloat.Float;
import marco.ffloat.screen.StatusScreen;
import marco.ffloat.world.Entity;
import marco.ffloat.world.Obstacle;
import marco.ffloat.world.ObstacleType;

import java.util.List;
import java.util.stream.Collectors;

public class BreakoutObstacleSpawner extends Entity {

    public float time;

    public BreakoutObstacleSpawner(World<Entity> world) {
        super(null, 0, 0, 0, 0, world, true);
        for (int i =  0; i < 10; i++) {
            for (int j =  0; j < 10; j++) {
                Obstacle obstacle = new Obstacle(200, ObstacleType.BREAKOUT, world)
                        .withPos(50 + (100 + 20)*i, Gdx.graphics.getHeight() - 10 * 40 - 70 + (20 + 20) * j);
                this.world().add(obstacle.entityItem(), obstacle.x(), obstacle.y(), obstacle.width(), obstacle.height());
            }
        }
    }

    @Override
    public void update(float delta) {
        time  += delta;
        if(time >= 10) {
            List<Item> collect = this.world().getItems().stream().filter(item -> item.userData instanceof Obstacle).collect(Collectors.toList());
            for (Item item : collect) {
                ((Entity) item.userData).remove();
            }
            Float.instance.setScreen(new StatusScreen());
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {

    }
}
