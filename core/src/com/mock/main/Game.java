package com.mock.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mock.handlers.GameStateManager;
import com.mock.handlers.MenuInputProcessor;
import com.mock.handlers.PlayInputProcessor;

public class Game extends ApplicationAdapter {

	public static final String TITLE = "Touch Maze";
	public static final float STEP = 1 / 60f;
	public static int V_WIDTH;
    public static int V_HEIGHT;
	public static PlayInputProcessor playInput;
	public static MenuInputProcessor menuInput;
	private float accum;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameStateManager gsm;
	
	@Override
	public void create () {
		V_WIDTH = Gdx.graphics.getWidth();
		V_HEIGHT = Gdx.graphics.getHeight();
		playInput = new PlayInputProcessor();
		menuInput = new MenuInputProcessor();
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
		}
	}
	
	@Override
	public void dispose () {}
	@Override
	public void resize(int width, int height) {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }

}
