package com.mock.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import com.mock.entities.Entity;
import com.mock.states.Play;

public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        String a = (String) contact.getFixtureA().getBody().getUserData();
        String b = (String) contact.getFixtureB().getBody().getUserData();
        Body playerBody = null; 
        Body entityBody = null;
        if (a.equals("bluePlayer") 
                || a.equals("yellowPlayer")
                || a.equals("greenPlayer")
                || a.equals("redPlayer")
                || a.equals("purplePlayer")) {
            playerBody = contact.getFixtureA().getBody();
            entityBody = contact.getFixtureB().getBody();
        } else if (b.equals("bluePlayer")
                || b.equals("yellowPlayer")
                || b.equals("greenPlayer")
                || b.equals("redPlayer")
                || b.equals("purplePlayer")) {
            playerBody = contact.getFixtureB().getBody();
            entityBody = contact.getFixtureA().getBody();
        } else {
            System.err.println("ERROR: MyContactListener.java doesn't support this player type!");
        }
        wallCollision(playerBody, entityBody);
        orbCollision(playerBody, entityBody);   
    }
    
    // Ray Cast Callback Interface
    public RayCastCallback callback = new RayCastCallback() {
        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point,
                Vector2 normal, float fraction) {
            String temp = (String) fixture.getBody().getUserData();
            if (temp.equals("blueWall") || temp.equals("yellowWall")
                    || temp.equals("greenWall") || temp.equals("redWall")
                    || temp.equals("purpleWall")) {
                wallCollision(Play.player.getBody(), fixture.getBody());
            }
            if (temp.equals("blueOrb") || temp.equals("yellowOrb")
                    || temp.equals("greenOrb") || temp.equals("redOrb")
                    || temp.equals("purpleOrb")) {
                orbCollision(Play.player.getBody(), fixture.getBody());
            }
            return 0;
        }  
    };

    @Override
    public void endContact(Contact contact) {}
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    public void wallCollision(Body playerBody, Body entityBody) {
        String p = (String) playerBody.getUserData();
        String e = (String) entityBody.getUserData();
        if (e.equals("yellowWall") && !p.equals("yellowPlayer")) {
            Play.gameOver = true;
        }
        if (e.equals("blueWall") && !p.equals("bluePlayer")) {
            Play.gameOver = true;
        }
        if (e.equals("greenWall") && !p.equals("greenPlayer")) {
            Play.gameOver = true;
        }
        if (e.equals("redWall") && !p.equals("redPlayer")) {
            Play.gameOver = true;
        }
        if (e.equals("purpleWall") && !p.equals("purplePlayer")) {
            Play.gameOver = true;
        }
    } 
    
    public void orbCollision(Body playerBody, Body entityBody) {
        String p = (String) playerBody.getUserData();
        String e = (String) entityBody.getUserData();
        if (e.equals("blueOrb") && !p.equals("bluePlayer")) {
            Play.player.getTexture().dispose();
            Play.player.setTexture(new Texture("bluePlayer.png"));
            Play.player.getBody().setUserData("bluePlayer");
            for (Entity ent : Play.entities) {
                if (ent.getBody().equals(entityBody)) { 
                    Play.entitiesToRemove.add(ent);
                    break;
                }
            }
        }
        if (e.equals("yellowOrb") && !p.equals("yellowPlayer")) {
            Play.player.getTexture().dispose();
            Play.player.setTexture(new Texture("yellowPlayer.png"));
            Play.player.getBody().setUserData("yellowPlayer");
            for (Entity ent : Play.entities) {
                if (ent.getBody().equals(entityBody)) { 
                    Play.entitiesToRemove.add(ent);
                    break;
                }
            }
        }
        if (e.equals("greenOrb") && !p.equals("greenPlayer")) {
            Play.player.getTexture().dispose();
            Play.player.setTexture(new Texture("greenPlayer.png"));
            Play.player.getBody().setUserData("greenPlayer");
            for (Entity ent : Play.entities) {
                if (ent.getBody().equals(entityBody)) { 
                    Play.entitiesToRemove.add(ent);
                    break;
                }
            }
        }
        if (e.equals("redOrb") && !p.equals("redPlayer")) {
            Play.player.getTexture().dispose();
            Play.player.setTexture(new Texture("redPlayer.png"));
            Play.player.getBody().setUserData("redPlayer");
            for (Entity ent : Play.entities) {
                if (ent.getBody().equals(entityBody)) { 
                    Play.entitiesToRemove.add(ent);
                    break;
                }
            }
        }
        if (e.equals("purpleOrb") && !p.equals("purplePlayer")) {
            Play.player.getTexture().dispose();
            Play.player.setTexture(new Texture("purplePlayer.png"));
            Play.player.getBody().setUserData("purplePlayer");
            for (Entity ent : Play.entities) {
                if (ent.getBody().equals(entityBody)) { 
                    Play.entitiesToRemove.add(ent);
                    break;
                }
            }
        }
    }
}
