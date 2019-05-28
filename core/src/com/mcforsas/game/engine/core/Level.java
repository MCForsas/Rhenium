package com.mcforsas.game.engine.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.handlers.FileHandler;

import java.util.Vector;

/**
 * @author by MCForsas @since 3/16/2019
 * Level object, holds gameObjects, can be used to draw backgrounds, etc.
 */
public abstract class Level extends Renderable{

    protected Vector<GameObject> gameObjects = new Vector<GameObject>();
    protected boolean isStarted = false;
    protected float width = GameLauncher.getWorldWidth(), heigth = GameLauncher.getWorldHeight();


    public void start(){
        super.start();
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).start();
        }
        isStarted = true;
        isRendered = true;
    }

    public void update(float deltaTime){
        for(int i = 0; i < gameObjects.size(); i++){
            if(!gameObjects.get(i).isPaused) {
                gameObjects.get(i).update(deltaTime);
            }
        }
    }

    public void dispose() {
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).dispose();
        }
    }


    public void end(){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).end();
        }

        gameObjects.clear();
        super.end();
        isStarted = false;
        isRendered = false;
    }

    public void save(FileHandler fileHandler, GameData gameData){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).save(fileHandler, gameData);
        }
    }


    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
    }

    /**
     * Adds gameObject and sets it's level to self
     */
    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
        gameObject.setLevel(this);
        if(isStarted){
            gameObject.start();
        }
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    @Override
    public void touchDown(float x, float y) {

    }

    @Override
    public void touchUp(float x, float y) {

    }

    @Override
    public void keyDown(int keycode) {

    }

    @Override
    public void keyUp(int keycode) {

    }

    @Override
    public void mouseMoved(float x, float y) {

    }

    public boolean isStarted() {
        return isStarted;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeigth() {
        return heigth;
    }

    public void setHeigth(float heigth) {
        this.heigth = heigth;
    }
}
