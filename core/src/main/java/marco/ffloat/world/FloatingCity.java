package marco.ffloat.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.World;
import marco.ffloat.Assets;
import marco.ffloat.event.EventManager;
import marco.ffloat.event.SpawnNPCEvent;
import marco.ffloat.event.StartSpawnerEvent;

public class FloatingCity extends Entity {

    private final EventManager eventManager;
    public boolean appear;
    public boolean disappear;
    private NPC npc;

    public FloatingCity(World<Entity> world, EventManager eventManager) {
        super(Assets.cityback.get(), 0, Gdx.graphics.getHeight(), 248 * 2, 128 * 2, world, true);
        this.eventManager = eventManager;
    }

    @Override
    public void update(float delta) {
        if (appear) {
            float newY = this.y() - 300 * delta;
            if (newY <= -52 * 2) {
                this.setY(-52 * 2);
                this.sprite().setY(this.y());
                this.appear = false;
                npc = new NPC(Assets.platform.get(), (int)MathUtils.random(this.x(), this.width() - 32), 50, 32, 32, world());
                this.world().add(npc.entityItem(), npc.x(), npc.y(), npc.width(), npc.height());
                this.eventManager.sendEvent(new SpawnNPCEvent());
            }
            this.sprite().setY(newY);
            this.setY(newY);
        }
        if(disappear) {
            this.world().remove(npc.entityItem());
            float newY = this.y() - 300 * delta;
            if(newY - this.height() < 0f) {
                this.setY(Gdx.graphics.getHeight());
                this.disappear = false;
                this.eventManager.sendEvent(new StartSpawnerEvent());
            }
            this.setY(newY);
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        this.sprite().draw(batch);
    }
}
