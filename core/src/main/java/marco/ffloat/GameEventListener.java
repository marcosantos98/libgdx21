package marco.ffloat;

import com.dongbat.jbump.World;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import marco.ffloat.event.*;
import marco.ffloat.util.CameraShakeConstraint;
import marco.ffloat.world.Entity;
import marco.ffloat.world.FloatingCity;
import marco.ffloat.world.ObstacleSpawner;
import marco.ffloat.world.Player;

public class GameEventListener implements IEventListener {

    private final World<Entity> world;
    private final FocusCameraController controller;
    private final Player player;
    private final FloatingCity city;
    private final ObstacleSpawner obstacleSpawner;
    private final CameraShakeConstraint shakeConstraint = new CameraShakeConstraint();

    public boolean showOptions;

    public GameEventListener(World<Entity> world, FocusCameraController controller, EventManager manager, Player player, FloatingCity city, ObstacleSpawner obstacleSpawner) {
        this.world = world;
        this.controller = controller;
        this.player = player;
        this.player.setY(-32);
        this.city = city;
        this.obstacleSpawner = obstacleSpawner;

        manager.addListener(StartGameEvent.class, this);
        manager.addListener(StartSpawnerEvent.class, this);
        manager.addListener(LevelUpEvent.class, this);
        manager.addListener(SpawnNPCEvent.class, this);
        manager.addListener(NewLevelEvent.class, this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof StartGameEvent) {
            this.player.appear = true;
        } else if(event instanceof StartSpawnerEvent) {
            this.player.canShoot = true;
            this.obstacleSpawner.canSpawn = true;
        } else if(event instanceof LevelUpEvent) {
            this.city.appear = true;
            controller.addCameraConstraint(shakeConstraint);
        } else if (event instanceof SpawnNPCEvent) {
            this.showOptions = true;
            controller.removeCameraConstraint(shakeConstraint);
            this.player.canShoot = false;
        } else if(event instanceof NewLevelEvent) {
            this.showOptions = false;
            this.city.disappear = true;
            obstacleSpawner.remove();
            new BreakoutObstacleSpawner(this.world);
        }
    }
}
