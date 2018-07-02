package com.mock.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

import com.mock.handlers.GameStateManager;
import com.mock.main.Game;
import com.mock.states.Play;

public class TestLevel1 extends Play {

    private Pixmap map;
    private float LEVEL_SPEED = Game.V_HEIGHT / 500;
    
    public TestLevel1(GameStateManager gsm) {
        super(gsm);
        Play.speed = LEVEL_SPEED;
        map = new Pixmap(Gdx.files.internal("testMap4.png"));
        createLevel(map);
    }

    @SuppressWarnings("static-access")
    public void update(float dt) {
        super.update(dt);
        increaseSpeed(); 
        if (gameOver) {
            dispose();
            gsm.setState(gsm.TEST_LEVEL_1);
            Play.speed = LEVEL_SPEED;
            gameOver = false;
        }
    }
    
    public void render() {
        super.render();
    }
    
    public void dispose() {
        super.dispose();
    }
    
    public void increaseSpeed() {
        if (Play.speed <= 10) {
            Play.speed = Play.speed + 0.01f;
        }
    } 
}
