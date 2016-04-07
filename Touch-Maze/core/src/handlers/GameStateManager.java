package handlers;

import java.util.Stack;

import levels.TestLevel1;
import main.Game;
import states.GameState;
import states.LevelMenu;
import states.MainMenu;
import states.Play;

public class GameStateManager {

    public static final int MAIN_MENU = 0;
    public static final int LEVEL_MENU = 1;
    public static final int PLAY = 2;
    public static final int TEST_LEVEL_1 = 3;
    private Game game;
    private Stack<GameState> gameStates;
    
    public GameStateManager(Game game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(TEST_LEVEL_1);
    }
    
    public void update(float dt) {
        gameStates.peek().update(dt);
    }
    
    public void render() {
        gameStates.peek().render();
    }
    
    private GameState getState(int state) {
        if (state == MAIN_MENU) return new MainMenu(this);
        if (state == LEVEL_MENU) return new LevelMenu(this);
        if (state == PLAY) return new Play(this);
        if (state == TEST_LEVEL_1) return new TestLevel1(this);
        return null;
    }
    
    public void setState(int state) {
        popState();
        pushState(state);
    }
    
    public void pushState(int state) {
        gameStates.push(getState(state));
    }
    
    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
    
    public void dispose() {} 
    
    public Game game() { 
        return game; 
    }
}












