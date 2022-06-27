package marco.ffloat.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends ScreenAdapter {

    public SpriteBatch batch = new SpriteBatch();
    public BitmapFont font = new BitmapFont();

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.font.draw(this.batch, "GAME OVER!", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        this.batch.end();
    }
}
