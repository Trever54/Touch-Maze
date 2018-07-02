package com.mock.handlers;

import com.badlogic.gdx.InputAdapter;

public class MenuInputProcessor extends InputAdapter {
    
    public static boolean touchDown = false;
    public static boolean touchUp = false;
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDown = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {  
        touchUp = true;
        touchDown = false;
        return true; 
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    } 

}
