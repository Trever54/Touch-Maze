package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import main.Game;

public class Player extends Entity {

    public Player(Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
        this.posX = (Game.V_WIDTH / 2) - (this.width / 2);
        this.posY = Game.V_HEIGHT / 5;
    }  
    
    public void update(float dt) {
        super.update(dt);
    }
    
}
