package handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.Game;

public class BackgroundManager {

    private final float SPEED = 0.5f; 
    private Texture background;
    private float backgroundY;
    private float loopY;
    private boolean loop = false;
    
    public BackgroundManager(Texture texture) {
        this.background = texture;
        backgroundY = 0;
        loopY = Game.V_HEIGHT;
    }
    
    public void update(float dt) { 
        backgroundY -= SPEED;
        if (loop) {
            loopY -= SPEED;
        }
    }
    
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, backgroundY, Game.V_WIDTH, Game.V_WIDTH * 4);
        if (backgroundY <= -(Game.V_WIDTH * 4) + Game.V_HEIGHT) {
            loop = true;
        }
        if (loop) {
            sb.draw(background, 0, loopY - SPEED, Game.V_WIDTH, Game.V_WIDTH * 4);
        }
        if (loopY < 0) {
            backgroundY = loopY;
            loopY = Game.V_HEIGHT;
            loop = false;
        }
        sb.end();
    }
    
    public void dispose() {
        background.dispose();
    }
}
