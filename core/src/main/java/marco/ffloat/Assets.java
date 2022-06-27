package marco.ffloat;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import marco.ffloat.util.AssetProvider;

public class Assets {

    private static final AssetManager manager = new AssetManager();

    public static final AssetProvider<Skin> skin = new AssetProvider<>(manager, "ui/uiskin.json", Skin.class);

    public static final AssetProvider<Texture> player = new AssetProvider<>(manager, "player.png", Texture.class);
    public static final AssetProvider<Texture> platform = new AssetProvider<>(manager, "platform.png", Texture.class);
    public static final AssetProvider<Texture> cityback = new AssetProvider<>(manager, "cityback.png", Texture.class);

    public static final AssetProvider<Sound> shoot = new AssetProvider<>(manager, "sounds/shoot.wav", Sound.class);
    public static final AssetProvider<Sound> explosion = new AssetProvider<>(manager, "sounds/explosion.wav", Sound.class);

    public static void load() {
        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }
}
