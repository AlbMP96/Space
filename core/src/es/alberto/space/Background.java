package es.alberto.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Background {

    private Texture background;
    private float y, vy;

    Background() {
        background = new Texture("background.png");
        vy = 100;
    }

    void draw(SpriteBatch batch) {
        batch.draw(background, 0, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(background, 0, y + Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    void update() {
        y -= vy;
        if (y + Gdx.graphics.getHeight() < 0) {
            y += Gdx.graphics.getHeight();
        }
    }
}
