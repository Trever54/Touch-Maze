package com.mock.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import com.mock.main.Game;
import com.mock.states.Play;

public class Orb extends Entity {
    
    public Orb(Body body, Sprite sprite, float posX, float posY, float width, float height) {
        super(body, sprite, posX, posY, width, height);
    }
    
    public void update(float dt) {
        this.setY(this.getY() - Play.speed);
        super.update(dt);
    }
    
}
