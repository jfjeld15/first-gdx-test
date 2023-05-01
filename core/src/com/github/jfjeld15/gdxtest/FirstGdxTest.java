package com.github.jfjeld15.gdxtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

public class FirstGdxTest extends ApplicationAdapter {
	private float horizontalSpeed;
	private Texture idleSheet;
	private Music laserGun;
	private OrthographicCamera camera;
	private SpriteBatch idleBatch;
	private Animation<TextureRegion> idleAnimation;
	private Rectangle player;
	private float stateTime;
	
	@Override
	public void create () {
		// Camera setup (Ensures that the camera is always showing 640x480 area)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);

		idleBatch = new SpriteBatch();

		// Load the images:
		idleSheet = new Texture(Gdx.files.internal("_Run.png"));
		TextureRegion[][] tmp = TextureRegion.split(idleSheet, idleSheet.getWidth()/10, idleSheet.getHeight());
		TextureRegion[] idleFrames = new TextureRegion[10];
		int index = 0;
		for (int i=0; i<10; i++) {
			idleFrames[index++] = tmp[0][i];
		}
		idleAnimation = new Animation<>(1/10f, idleFrames);
		idleBatch = new SpriteBatch();
		stateTime = 0f;

		// Load the music and sound FX:
		laserGun = Gdx.audio.newMusic(Gdx.files.internal("Laser Gun.mp3"));

		// Start music playback (And looping)
		laserGun.setLooping(true);
		laserGun.play();

		// Set up the player starting point
		player = new Rectangle();
		player.x = 640f /2 - 20f/2;
		player.y = 10;
		player.width = 20;
		player.height = 38;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);  // Dark Blue
		camera.update();
		stateTime += Gdx.graphics.getDeltaTime();  // Accumulate elapsed animation time

		idleBatch.setProjectionMatrix(camera.combined);
		TextureRegion currentFrame = idleAnimation.getKeyFrame(stateTime, true);
		idleBatch.begin();
		idleBatch.draw(currentFrame, player.x, player.y);
		idleBatch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) horizontalSpeed = 0;
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) horizontalSpeed = 0;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (horizontalSpeed != -10f) {
				horizontalSpeed -= 0.2f;
			}
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (horizontalSpeed != 10f) {
				horizontalSpeed += 0.2f;
			}
		}
		else {horizontalSpeed = 0;}
		player.x += (int) horizontalSpeed;
		if(player.x < 0) player.x = 0;
		if(player.x > 640-20) player.x = 640-20;

	}
	
	@Override
	public void dispose () {
		idleBatch.dispose();
		idleSheet.dispose();
		laserGun.dispose();
	}
}
