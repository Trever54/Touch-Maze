package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity {

    protected Body body;
    protected Sprite sprite;
    protected float width;
    protected float height;
    protected float posX;
    protected float posY;
    
    public Entity(Body body, Sprite sprite, float width, float height) {
        this.body = body;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.posX = 0;
        this.posY = 0;   
    }
    
    public Entity(Body body, Sprite sprite, float posX, float posY, float width, float height) {
        this.body = body;
        this.sprite = sprite;
        this.posX = posX;
        this.posY = posY;   
        this.width = width;
        this.height = height;
    }
    
    public void render(SpriteBatch sb) { 
        sb.begin();
        sb.draw(sprite, posX, posY, width, height);
        sb.end();
    }
    
    public void update(float dt) {
        this.body.setTransform(posX + width / 2, posY + height / 2, 0);
    }
    
    public void setTexture(Texture texture) { this.sprite.setTexture(texture); }
    public Texture getTexture() { return this.sprite.getTexture(); }
    public Body getBody() { return body; }
    public Vector2 getPosition() { return body.getPosition(); }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getX() { return posX; }
    public float getY() { return posY; }
    public void setX(float posX) { this.posX = posX; }
    public void setY(float posY) { this.posY = posY; } 
}
