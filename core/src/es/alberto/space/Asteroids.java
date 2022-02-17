package es.alberto.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class Asteroids implements Drawable{

    private static Texture asteroid;
    private Sprite asteroidRot;

    private static final float distanceBetween = 700;
    private float x;
    private float y;
    private float vy = 40;
    static final int numberOfAsteroids = 5;
    private static final Random randomGenerator = new Random();
    private int index;

    private Circle collisionCircle;


    Asteroids(int index) {
        this.index = index;
        asteroid = new Texture("asteroid.png");
        asteroidRot = new Sprite(asteroid);
        collisionCircle = new Circle();
        asteroidRot.setSize(asteroidRot.getWidth() / 3, asteroidRot.getHeight() / 3);
    }

    void start() {
        setY(Gdx.graphics.getHeight() + index * distanceBetween);
        asteroidRot.setOriginCenter();
        randomRotation();
        randomizeX();
    }

    public void draw (SpriteBatch batch) {
        asteroidRot.draw(batch);
    }

    private void randomizeX() {
        x = randomGenerator.nextFloat() * (Gdx.graphics.getWidth() - asteroidRot.getWidth());
        asteroidRot.setX(x);
    }

    // rotate sprite randomly
    private void randomRotation() {
        float rotation = randomGenerator.nextFloat() * 360;
        asteroidRot.setRotation(rotation);
    }

    void update() {
        if (y + asteroid.getHeight() < 0) {
            recycle();
        }
        setY(y - vy);

        collisionCircle.set(x + asteroidRot.getWidth() / 2,y + asteroidRot.getHeight() / 2,asteroidRot.getHeight() / 3);
    }

    private void setY(float y) {
        this.y = y;
        asteroidRot.setY(y);
    }

    private void recycle() {
        setY(y + numberOfAsteroids * distanceBetween);
        randomizeX();
        randomRotation();
    }

    Circle getCollisionCircle() {

        return collisionCircle;
    }
}
