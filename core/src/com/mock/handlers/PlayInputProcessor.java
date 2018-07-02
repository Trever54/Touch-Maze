package com.mock.handlers;

import com.badlogic.gdx.InputAdapter;

import com.mock.main.Game;
import com.mock.states.Play;

public class PlayInputProcessor extends InputAdapter {
    
    private float originalX;
    private float originalY;
    private float offsetX;
    private float offsetY;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0) {
            Play.engaged = true;
            originalX = screenX;
            originalY = screenY;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { 
        if (pointer == 0) {
            Play.engaged = false;
            Play.player.getBody().setLinearVelocity(0, 0);
        }
        return true; 
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer == 0) { 
            offsetX = screenX - originalX;
            offsetY = screenY - originalY;
            originalX = screenX;
            originalY = screenY;
            
            /* With offset
            Play.player.setX(Play.player.getX() + offsetX);
            Play.player.setY(Play.player.getY() - offsetY);
            */
            
            if (Play.player.getX() < - Play.player.getWidth()) {
                // Play.player.setX(0);
                Play.player.setX(Game.V_WIDTH - Play.player.getWidth());
            }
            if (Play.player.getX() > Game.V_WIDTH) {
                //Play.player.setX(Game.V_WIDTH - Play.player.getWidth());
                Play.player.setX(0);
            }
            if (Play.player.getX() >= 0 
                    || Play.player.getX() <= Game.V_WIDTH - Play.player.getWidth()) {
                Play.player.setX(Play.player.getX() + offsetX);
            }
            if (Play.player.getY() < 0) {
                Play.player.setY(0);
            }
            if (Play.player.getY() > Game.V_HEIGHT - Play.player.getHeight()) {
                Play.player.setY(Game.V_HEIGHT - Play.player.getHeight());
            }
            if (Play.player.getY() >= 0 
                    || Play.player.getY() <= Game.V_HEIGHT - Play.player.getHeight()) {
                Play.player.setY(Play.player.getY() - offsetY);
            }
        }
        return true;
    } 
}
