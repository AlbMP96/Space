package es.alberto.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Spaceship implements Drawable{

    private static Texture ship;
    private Sprite spriteShip;
    private float y, x;
    private static final float vx = 30;

    Rectangle body;
    Rectangle wings;

    Spaceship() {
        ship = new Texture("spaceship.png");
        spriteShip = new Sprite(ship);
        body = new Rectangle();
        wings = new Rectangle();
    }

    void start() {
        y = 100;
        x = Gdx.graphics.getWidth() / 2 - (ship.getWidth() / 3) / 2;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(ship, x, y, ship.getWidth() / 3, ship.getHeight() / 3);
    }

    void update() {
        if (Gdx.input.getX() > 0 && Gdx.input.getX() < Gdx.graphics.getWidth()) {
            if (x + ((ship.getWidth() / 3) / 2) - 30 > Gdx.input.getX()) {
                x  -= vx;
            } else if (x + ((ship.getWidth() / 3) / 2) + 30 < Gdx.input.getX()) {
                x += vx;
            } else {
                x = Gdx.input.getX() - (ship.getWidth() / 3) / 2;
            }
        }

        if (x <= 0) {
            x = 0;
        } else if ( x >= (Gdx.graphics.getWidth() - (float) ship.getWidth() / 3)) {
            x = Gdx.graphics.getWidth() - ship.getWidth() / 3;
        }

        collisionRectangles();

    }

    Rectangle[] collisionRectangles() {
      Rectangle[] rectangles = new Rectangle[2];
        rectangles[0] = body.set(x + (spriteShip.getWidth() / 9), y, (spriteShip.getWidth() / 5) / 2, spriteShip.getHeight() / 4);
        rectangles[1] = wings.set(x, y, ship.getWidth() / 3, (spriteShip.getHeight() / 5) / 2);

        return rectangles;
    }
}
