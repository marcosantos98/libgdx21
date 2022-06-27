package marco.ffloat.screen;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import com.gempukku.libgdx.lib.camera2d.constraint.LockedToCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.SceneCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.focus.EntityFocus;
import marco.ffloat.Assets;
import marco.ffloat.Float;
import marco.ffloat.GameEventListener;
import marco.ffloat.event.EventManager;
import marco.ffloat.event.NewLevelEvent;
import marco.ffloat.event.StartGameEvent;
import marco.ffloat.particle.ParticleSystem;
import marco.ffloat.util.CameraShakeConstraint;
import marco.ffloat.world.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FirstScreen implements Screen {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Entity platform;
    private Player player;
    private FloatingCity city;
    private ObstacleSpawner spawner;
    private NPC npc;
    private World<Entity> world;

    private BitmapFont font = new BitmapFont();

    private boolean drawDebug = false;

    public static TweenManager manager = new TweenManager();

    public GameEventListener eventListener;

    public static EventManager eventManager = new EventManager();

    private OrthographicCamera camera = new OrthographicCamera(1280, 720);
    private CameraShakeConstraint cameraShakeConstraint = new CameraShakeConstraint();
    private FocusCameraController cameraController;

    private Stage stage = new Stage();
    private CheckBox sound;

    private Window window = new Window("Upgrades:", Assets.skin.get());

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.sound = new CheckBox("Sound", Assets.skin.get());
        this.sound.setPosition(Gdx.graphics.getWidth() - this.sound.getWidth() - 10, 10);
        this.stage.addActor(this.sound);

        this.world = new World<>();
        this.spawner = new ObstacleSpawner(0, 0, 0, 0, this.world, true);
        this.player = new Player(Assets.player.get(), (Gdx.graphics.getWidth() >> 1) - 16, 32, 32, 32, world, true);
        this.city = new FloatingCity(this.world, this.eventManager);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
        this.camera.update();

        this.window.getTitleTable().padTop(20).padLeft(10).center();
        this.window.getTitleLabel().setColor(Color.WHITE);
        this.window.add(new Label("UPGRADE DAMAGE:", Assets.skin.get())).pad(10);
        int value = MathUtils.random(50);
        TextButton btm = new TextButton(value + " coins = +10 dmg" , Assets.skin.get());
        if(player.money - value < 0) btm.setDisabled(true);
        btm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.dmg += 10;
                player.money -= value;
            }
        });
        this.window.add(btm);
        this.window.setBounds(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 200, 300, 400);
        this.window.setVisible(false);
        this.stage.addActor(this.window);

        this.cameraController = new FocusCameraController(
                camera,
                new EntityFocus(position -> new Vector2(player.x(), player.y())),
                new LockedToCameraConstraint(new Vector2(0.5f, .5f)),
                new SceneCameraConstraint(new Rectangle(0f, 0f, camera.viewportWidth, camera.viewportHeight))
        );

        this.eventListener = new GameEventListener(world, cameraController, this.eventManager, this.player, this.city, this.spawner);
        this.eventManager.sendEvent(new StartGameEvent());

        Gdx.input.setInputProcessor(this.stage);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) this.drawDebug = !this.drawDebug;

        if (this.sound.isChecked()) Float.sound = 1f;
        else Float.sound = 0f;

        this.cameraController.update(delta);
        ParticleSystem.tick(delta);

        List<Item> removed = world.getItems().stream().filter(item -> ((Entity) item.userData).wasRemoved()).collect(Collectors.toList());
        for (int i = removed.size() - 1; i >= 0; i--) {
            world.remove(removed.get(i));
        }

        List<Item> current = Arrays.asList(this.world.getItems().stream().filter(item -> !((Entity) item.userData).wasRemoved()).toArray(Item[]::new));

        for (Item item : current) {
            Entity e = (Entity) item.userData;
            ((Entity) item.userData).update(delta);
        }

        manager.update(delta);

        this.stage.act(delta);

        if(this.eventListener.showOptions) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                this.window.setVisible(!this.window.isVisible());
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                this.window.setVisible(false);
                this.eventListener.showOptions = false;
                this.eventManager.sendEvent(new NewLevelEvent());
            }
        }
    }

    @Override
    public void render(float delta) {

        this.update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(23f / 255f, 46f / 255f, 84f / 255f, 1f);

        this.stage.draw();

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        List<Entity> collect = this.world.getItems().stream().map(item -> (Entity) item.userData).collect(Collectors.toList());
        Collections.sort(collect, Comparator.comparingInt(Entity::zIndex));

        for (Entity item : collect) {
            item.render(this.batch, delta);
        }

        ParticleSystem.render(batch, delta);
        font.draw(this.batch, "Money: " + this.player.money, 10, Gdx.graphics.getHeight() - 20);
        font.draw(this.batch,
                "Level Enemies: " + this.spawner.currentObstacles + "/" + this.spawner.levelSystem.getInfoForLevel().numberOfObstacle,
                10,
                Gdx.graphics.getHeight() - 40
        );
        font.draw(this.batch, "Current damage: " + this.player.dmg, 10, Gdx.graphics.getHeight() - 60);


        if(this.eventListener.showOptions) {
            this.font.draw(this.batch, "Press X to open upgrade menu!", this.player.x(), this.player.y() + 64);
            this.font.draw(this.batch, "Press SPACE to continue!", this.player.x(), this.player.y() + 84);
        }

        this.batch.end();

        if (this.drawDebug) {
            this.shapeRenderer.setColor(Color.BLUE);
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Rect rect : this.world.getRects()) {
                this.shapeRenderer.rect(rect.x, rect.y, rect.w, rect.h);
            }
            this.shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        this.camera.position.set(width / 2, height / 2, 0);
        this.camera.update();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}