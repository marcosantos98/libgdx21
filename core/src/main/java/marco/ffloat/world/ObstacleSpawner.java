package marco.ffloat.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.World;
import marco.ffloat.event.LevelUpEvent;
import marco.ffloat.screen.FirstScreen;

public class ObstacleSpawner extends Entity {

    public boolean canSpawn;
    private float time;

    public LevelSystem levelSystem = new LevelSystem();

    public int currentObstacles;

    public ObstacleSpawner(int x, int y, int width, int height, World<Entity> world, boolean initialAdd) {
        super(null, x, y, width, height, world, initialAdd);
    }

    @Override
    public void update(float delta) {
        if (canSpawn) {
            // FIXME: 6/26/22
            if(currentObstacles < levelSystem.getInfoForLevel().numberOfObstacle) {
                time += delta;
                if (time >= 1f) {
                    int rndX = MathUtils.random(300, Gdx.graphics.getWidth() - 300);
                    Obstacle obstacle = new Obstacle(levelSystem.getInfoForLevel().obstacleLP,ObstacleType.DEAD_BIRD, this.world()).withPos(rndX, Gdx.graphics.getHeight());
                    this.world().add(obstacle.entityItem(), obstacle.x(), obstacle.y(), obstacle.width(), obstacle.height());
                    this.time = 0f;
                    this.currentObstacles++;
                }
            } else {
                levelSystem.currentLevel++;
                this.currentObstacles = 0;
                FirstScreen.eventManager.sendEvent(new LevelUpEvent());
                canSpawn = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {

    }
}
