package es.alberto.space;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Background background;
	private Spaceship ship;
	private Asteroids[] asteroids =  new Asteroids[Asteroids.numberOfAsteroids];

	private GameState gameState;
	long startTime;
	long finishTime;

	private ScreenText scoreText;
	private ScreenText screenText;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Background();
		ship = new Spaceship();
		for (int i = 0; i < Asteroids.numberOfAsteroids; i++) {
			asteroids[i] = new Asteroids(i);
		}

		scoreText = new ScreenText(Gdx.graphics.getWidth() / 4,Gdx.graphics.getHeight() - 100);
		screenText = new ScreenText(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		start();
	}

	@Override
	public void render () {
		updateState();
		draw();
	}

	private void start() {
		gameState = GameState.TAP_TO_PLAY;

		for (Asteroids asteroid: asteroids) {
			asteroid.start();
		}
		ship.start();
	}

	private void draw(){
		batch.begin();
		background.draw(batch);
		ship.draw(batch);
		for (Asteroids asteroid : asteroids) {
			asteroid.draw(batch);
		}

		drawScreenText();
		//renderShapes();

		batch.end();
	}


	private void update(){
		background.update();
		if (Gdx.input.isTouched()) {
			ship.update();
		}
		for (Asteroids asteroid : asteroids) {
			asteroid.update();
		}

		// check space
		//while(Gdx.input.isTouched()){

		//}
	}

	private void updateState() {
		switch(gameState) {

			case PLAY:
				update();
				checkCollisions();

				break;

			case TAP_TO_PLAY:

				if (Gdx.input.justTouched()) {
					SoundEffects.play(SFX.MUSIC);
					gameState = GameState.PLAY;
					startTime = System.nanoTime();
				}
				break;

			case GAME_OVER:
				SoundEffects.dispose();
				if (Gdx.input.justTouched()) {
					start();
					gameState = GameState.TAP_TO_PLAY;
				}
				break;

		}
	}

	private void checkCollisions() {
		Rectangle[] shipRectangle = ship.collisionRectangles();
		for (Asteroids asteroid : asteroids) {
			Circle asteroidCircle = asteroid.getCollisionCircle();
			if (Intersector.overlaps(asteroidCircle, shipRectangle[0]) || Intersector.overlaps(asteroidCircle, shipRectangle[1])) {
				die();
			}
		}

	}

	private void drawScreenText() {

		switch (gameState) {

			case TAP_TO_PLAY:

				screenText.setText("TAP TO PLAY");
				screenText.draw(batch);

				break;

			case PLAY:

				scoreText.setText("Time: " + Math.round((System.nanoTime() - startTime) / Math.pow(10, 9)));
				scoreText.draw(batch);

				break;

			case GAME_OVER:

				screenText.setText("GAME OVER");
				screenText.draw(batch);
				scoreText.setText("Time: " + Math.round((finishTime - startTime) / Math.pow(10, 9)));
				scoreText.draw(batch);

				break;
		}
	}

	private void die() {
		finishTime = System.nanoTime();
		gameState = GameState.GAME_OVER;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}


	// Method to test collisions
	private void renderShapes() {

		ShapeRenderer shapeRenderer = new ShapeRenderer();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);

		Rectangle body = ship.body;
		Rectangle wings = ship.wings;
		shapeRenderer.rect(body.x, body.y, body.width, body.height);
		shapeRenderer.rect(wings.x, wings.y, wings.width, wings.height);

		shapeRenderer.setColor(Color.BLUE);

		for (Asteroids asteroid : asteroids) {

			Circle asteroidCircle = asteroid.getCollisionCircle();

			shapeRenderer.circle(asteroidCircle.x, asteroidCircle.y, asteroidCircle.radius);
		}

		shapeRenderer.end();
	}

}
