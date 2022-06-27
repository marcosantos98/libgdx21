package marco.ffloat.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import marco.ffloat.world.Player;

public class StatusScreen extends ScreenAdapter {

    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);

        this.batch.begin();
        this.font.draw(this.batch, "Score: " + Player.destroyed, Gdx.graphics.getWidth() /2f, Gdx.graphics.getHeight() / 2f);
        this.font.draw(this.batch, "Money: " + Player.allMoney, Gdx.graphics.getWidth() /2f, Gdx.graphics.getHeight() / 2f - 20);
        this.batch.end();
    }
}
