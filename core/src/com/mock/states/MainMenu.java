package com.mock.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.mock.handlers.GameStateManager;
import com.mock.handlers.MenuInputProcessor;
import com.mock.main.Game;

public class MainMenu extends GameState {

    private Sprite mainMenuTitle;
    private Sprite playButton;
    private Sprite soundButton;
    private Sprite exitButton;
    
    
    public MainMenu(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setInputProcessor(Game.menuInput);

        mainMenuTitle = new Sprite(new Texture("mainMenuTitle.png"));
        mainMenuTitle.setSize(Game.V_WIDTH, Game.V_HEIGHT / 5);
        mainMenuTitle.setX(0);
        mainMenuTitle.setY(Game.V_HEIGHT - mainMenuTitle.getHeight());
        
        playButton = new Sprite(new Texture("playButton.png"));
        playButton.setSize(Game.V_WIDTH / 2, Game.V_HEIGHT / 10);
        playButton.setX((Game.V_WIDTH / 2) - (playButton.getWidth() / 2));
        playButton.setY(mainMenuTitle.getY() - (Game.V_HEIGHT / 5));
        
        soundButton = new Sprite(new Texture("soundButton.png"));
        soundButton.setSize(Game.V_WIDTH / 2, Game.V_HEIGHT / 10);
        soundButton.setX((Game.V_WIDTH / 2) - (playButton.getWidth() / 2));
        soundButton.setY(playButton.getY() - (Game.V_HEIGHT / 5));
        
        exitButton = new Sprite(new Texture("exitButton.png"));
        exitButton.setSize(Game.V_WIDTH / 2, Game.V_HEIGHT / 10);
        exitButton.setX((Game.V_WIDTH / 2) - (playButton.getWidth() / 2));
        exitButton.setY(soundButton.getY() - (Game.V_HEIGHT / 5));
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(mainMenuTitle, mainMenuTitle.getX(), mainMenuTitle.getY(), mainMenuTitle.getWidth(), mainMenuTitle.getHeight());
        sb.draw(playButton, playButton.getX(), playButton.getY(), playButton.getWidth(), playButton.getHeight());
        sb.draw(soundButton, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        sb.draw(exitButton, exitButton.getX(), exitButton.getY(), exitButton.getWidth(), exitButton.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        mainMenuTitle.getTexture().dispose();
        playButton.getTexture().dispose();
        soundButton.getTexture().dispose();
        exitButton.getTexture().dispose();
    }
    
    @SuppressWarnings("static-access")
    public void handleInput() {
        if (MenuInputProcessor.touchUp) {
            dispose();
            gsm.pushState(gsm.LEVEL_MENU);
            MenuInputProcessor.touchUp = false;
        }   
    }
}
