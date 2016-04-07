package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import handlers.GameStateManager;
import handlers.MenuInputProcessor;
import main.Game;

public class LevelMenu extends GameState {
    
    private Sprite testLevelTitle;
    private Sprite level1Button;
    private Sprite backButton;
    
    
    public LevelMenu(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setInputProcessor(Game.menuInput);

        testLevelTitle = new Sprite(new Texture("testLevelTitle.png"));
        testLevelTitle.setSize(Game.V_WIDTH / 2, Game.V_HEIGHT / 16);
        testLevelTitle.setX(0);
        testLevelTitle.setY(Game.V_HEIGHT - testLevelTitle.getHeight());
        
        level1Button = new Sprite(new Texture("level1Button.png"));
        level1Button.setSize(Game.V_WIDTH / 16, Game.V_WIDTH / 16);
        level1Button.setX((Game.V_WIDTH / 16) - (level1Button.getWidth() / 2));
        level1Button.setY(testLevelTitle.getY() - (Game.V_HEIGHT / 16));
        
        backButton = new Sprite(new Texture("backButton.png"));
        backButton.setSize(Game.V_WIDTH / 2, Game.V_HEIGHT / 10);
        backButton.setX((Game.V_WIDTH / 2) - (backButton.getWidth() / 2)); 
        backButton.setY(Game.V_HEIGHT / 64);
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
        sb.draw(testLevelTitle, testLevelTitle.getX(), testLevelTitle.getY(), testLevelTitle.getWidth(), testLevelTitle.getHeight());
        sb.draw(level1Button, level1Button.getX(), level1Button.getY(), level1Button.getWidth(), level1Button.getHeight());
        sb.draw(backButton, backButton.getX(), backButton.getY(), backButton.getWidth(), backButton.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        testLevelTitle.getTexture().dispose();
        level1Button.getTexture().dispose();
        backButton.getTexture().dispose();
    }
    
    @SuppressWarnings("static-access")
    public void handleInput() {
        if (MenuInputProcessor.touchUp) {
            dispose();
            gsm.pushState(gsm.TEST_LEVEL_1);
            MenuInputProcessor.touchUp = false;
        }   
    }  
}
