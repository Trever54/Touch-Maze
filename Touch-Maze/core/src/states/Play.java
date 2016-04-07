package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.FaceDirection;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entities.Entity;
import entities.Orb;
import entities.Player;
import entities.Wall;
import handlers.BackgroundManager;
import handlers.GameStateManager;
import handlers.MyContactListener;
import main.Game;

public class Play extends GameState {
    
    public static boolean gameOver = false;
    public static boolean debug = false;
    
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCam;
    private MyContactListener contactListener;
    
    // background
    private BackgroundManager bm;
    private Stage stage;
    
    // play state essentials
    public static Player player;
    public static boolean engaged = false;
    public Array<Sprite> trail;
    
    // Wall for test
    public static float speed;
    public static Array<Entity> entities;
    public static Array<Entity> entitiesToRemove;
    
    // advanced ray casting collision points for player
    private Vector2 p1, p2;
    private ShapeRenderer sr; 
    
    public Play(GameStateManager gsm) {
        super(gsm);
        
        // input processor
        Gdx.input.setInputProcessor(Game.playInput);   
        
        // create world (and set gravity)
        contactListener = new MyContactListener();
        world = new World(new Vector2(0, 0), true); 
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();
   
        // create background
        bm = new BackgroundManager(new Texture("spaceBackground.png"));
        stage = new Stage(new ScreenViewport(), sb);
        
        // create player
        createPlayer();
        sr = new ShapeRenderer();
        p1 = new Vector2(player.getPosition());
        p2 = new Vector2(player.getPosition());
        
        // create trail list
        trail = new Array<Sprite>();
        entities = new Array<Entity>();
        entitiesToRemove = new Array<Entity>();
 
        // set up box2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);    
    }
    
    public void update(float dt) {
        world.step(dt, 6, 2);
        removeEntities();
        // Background movement
        bm.update(dt);
        // player movement
        player.update(dt);
        // entity movement
        for (Entity e : entities) {
            e.update(dt); 
        }
        // Ray casting collision
        p2 = p1;
        p1 = new Vector2(player.getPosition());
        if (!p1.equals(p2)) {
            world.rayCast(contactListener.callback, p1, p2);
        }
    }
    
    public void render() {
        // clear Screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.getRoot().setColor(1, 1, 1, 0);
        stage.getRoot().addAction(Actions.fadeOut(1));

        // set Projection Matrix
        sb.setProjectionMatrix(cam.combined);
        
        // render background
        bm.render(sb);
        
        // draw Player
        player.render(sb);
        
        // draw trail
        leaveTrail();
         
        // draw/remove entities
        for (Entity e : entities) {
            if (e.getY() <= Game.V_HEIGHT && e.getY() >= - ((Game.V_HEIGHT / 64) * 2)) {
                e.render(sb); 
            } else if (e.getY() <= Game.V_HEIGHT) {
                entitiesToRemove.add(e);
            }
        }
        
        if (debug) {
            drawRayCasting();   // collision testing
            b2dr.render(world, b2dCam.combined);    // Draw box2d world
        }
    }
    
    public void dispose() {
        for (int i = 0; i < entities.size; i++) {
            entities.get(i).getTexture().dispose();
        }
        player.getTexture().dispose();
        bm.dispose();
        //sb.dispose();
        //sr.dispose();
        //b2dr.dispose();
    }
    
    public void removeEntities() {
        Entity temp;
        for (int i = 0; i < entitiesToRemove.size; i++) {
            temp = entitiesToRemove.get(i);
            entities.removeValue(temp, false);
            temp.getTexture().dispose();
            world.destroyBody(temp.getBody());
        }
        entitiesToRemove.clear();
    }
    
    public void createLevel(Pixmap image) {
        int w = image.getWidth();
        int h = image.getHeight();  
        float xSpace = Game.V_WIDTH / 16;
        float ySpace = Game.V_HEIGHT / 64;
        if (w != 16) { throw new IllegalArgumentException(); }    
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getPixel(xx, yy);
                
                // System.out.println("X: " + xx + " Y: " + yy + " #: " + pixel);
    
                // if blue pixel (0, 0, 255)
                if (pixel == 65535) {
                    createWall("BLUE", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                }
                // if yellow pixel (255, 255, 0)
                if (pixel == -65281) {
                    createWall("YELLOW", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                } 
                // if green pixel (0, 255, 0)
                if (pixel == 16711935) {
                    createWall("GREEN", xSpace * xx, Game.V_HEIGHT + (ySpace* ((h-1) - yy)));
                }
                // if red pixel (255, 0, 0)
                if (pixel == -16776961) {
                    createWall("RED", xSpace * xx, Game.V_HEIGHT + (ySpace* ((h-1) - yy)));
                }
                // if purple pixel (255, 0, 255)
                if (pixel == -16711681) {
                    createWall("PURPLE", xSpace * xx, Game.V_HEIGHT + (ySpace* ((h-1) - yy)));
                }
                // if dark blue pixel (0, 0, 230)
                if (pixel == 59135) {
                    createOrb("BLUE", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                }
                // if dark yellow pixel (230, 230, 0)
                if (pixel == -421134081) {
                    createOrb("YELLOW", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                }  
                // if dark green pixel (0, 230, 0)
                if (pixel == 15073535) {
                    createOrb("GREEN", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                }     
                // if dark red pixel (230, 0, 0)
                if (pixel == -436207361) {
                    createOrb("RED", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                }     
                // if dark purple pixel (230, 0, 230)
                if (pixel == -436148481) {
                    createOrb("PURPLE", xSpace * xx, Game.V_HEIGHT + (ySpace * ((h - 1) - yy)));
                }     
            }
        }
    }
    
    public void createWall(String color, float posX, float posY) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape(); 
        Texture wallTexture = null;
        bdef.type = BodyType.KinematicBody;
        Body body = world.createBody(bdef);  
        shape.setAsBox(Game.V_WIDTH / 32, Game.V_HEIGHT / 128);
        fdef.shape = shape;
        body.createFixture(fdef);
        switch (color) {
            case "BLUE":
                body.setUserData("blueWall");
                wallTexture = new Texture("blueWall.png");
                break;
            case "YELLOW":
                body.setUserData("yellowWall");
                wallTexture = new Texture("yellowWall.png");
                break;
            case "GREEN":
                body.setUserData("greenWall");
                wallTexture = new Texture("greenWall.png");
                break;
            case "RED":
                body.setUserData("redWall");
                wallTexture = new Texture("redWall.png");
                break;
            case "PURPLE":
                body.setUserData("purpleWall");
                wallTexture = new Texture("purpleWall.png");
                break;
            default:
                System.err.println("Error in the color of creating a wall");
        }  
        Wall wall = new Wall(body, 
                new Sprite(wallTexture), 
                posX, 
                posY,
                Game.V_WIDTH / 16, 
                Game.V_HEIGHT / 64);
        wall.getBody().setTransform(wall.getX() + wall.getWidth() / 2, 
                wall.getY() + wall.getHeight() / 2, 0);
        entities.add(wall);
    }
    
    public void createOrb(String color, float posX, float posY) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        Texture orbTexture = null;
        bdef.type = BodyType.KinematicBody;
        Body body = world.createBody(bdef);  
        shape.setRadius(Game.V_WIDTH / 32);
        fdef.shape = shape;
        body.createFixture(fdef);
        switch (color) {
            case "BLUE":
                body.setUserData("blueOrb");
                orbTexture = new Texture("blueOrb.png");
                break;
            case "YELLOW":
                body.setUserData("yellowOrb");
                orbTexture = new Texture("yellowOrb.png");
                break;
            case "GREEN":
                body.setUserData("greenOrb");
                orbTexture = new Texture("greenOrb.png");
                break;
            case "RED":
                body.setUserData("redOrb");
                orbTexture = new Texture("redOrb.png");
                break;
            case "PURPLE":
                body.setUserData("purpleOrb");
                orbTexture = new Texture("purpleOrb.png");
                break;
            default:
                System.err.println("Error in the color of creating an orb");
        }  
        Orb orb = new Orb(body, 
                new Sprite(orbTexture), 
                posX, 
                posY,
                Game.V_WIDTH / 16, 
                Game.V_WIDTH / 16);
        orb.getBody().setTransform(orb.getX() + orb.getWidth() / 2, 
                orb.getY() + orb.getHeight() / 2, 0);
        entities.add(orb);
    }
    
    public void createPlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape(); 
        bdef.type = BodyType.DynamicBody;
        Body body = world.createBody(bdef);  
        shape.setRadius(Game.V_WIDTH / 50);
        fdef.shape = shape;
        body.createFixture(fdef);
        body.setUserData("bluePlayer");
        Texture bluePlayer = new Texture("bluePlayer.png");
        player = new Player(body, 
                new Sprite(bluePlayer), 
                Game.V_WIDTH / 25, 
                Game.V_WIDTH / 25);
        player.getBody().setTransform(player.getX() + player.getWidth() / 2, 
                player.getY() + player.getHeight() / 2, 0);
    }  
    
    public void leaveTrail(){
        Sprite t = new Sprite(new Texture("bluePlayer.png"));
        if (player.getBody().getUserData().equals("yellowPlayer")) {
            t = new Sprite(new Texture("yellowPlayer.png"));
        }
        if (player.getBody().getUserData().equals("greenPlayer")) {
            t = new Sprite(new Texture("greenPlayer.png"));
        }
        if (player.getBody().getUserData().equals("redPlayer")) {
            t = new Sprite(new Texture("redPlayer.png"));
        }
        if (player.getBody().getUserData().equals("purplePlayer")) {
            t = new Sprite(new Texture("purplePlayer.png"));
        }
        t.setPosition(player.getX() + player.getWidth() / 4, 
                player.getY());
        t.setSize(player.getWidth() / 2, player.getHeight() / 2);
        if (engaged) {
            trail.add(t);
        }
        sb.begin();
        for (Sprite s : trail) {
            sb.draw(s, s.getX(), s.getY(), s.getWidth(), s.getHeight());
            s.setY(s.getY() - 2);
        }
        for (Sprite s : trail) {
            if (trail.size > 20 || !engaged) {
                trail.removeValue(s, false);
                s.getTexture().dispose();
                break;
            }
        }
        sb.end();
    }
    
    private void drawRayCasting() {
        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeType.Line);
        sr.line(p1, p2);
        sr.end();
    }  
}
