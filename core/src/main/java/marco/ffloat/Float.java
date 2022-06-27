package marco.ffloat;

import com.badlogic.gdx.Game;
import marco.ffloat.screen.FirstScreen;

public class Float extends Game {

    public static float sound = 1f;

    public static Float instance;

    @Override
    public void create() {
        if(instance == null) instance = this;
        Assets.load();
        setScreen(new FirstScreen());
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}