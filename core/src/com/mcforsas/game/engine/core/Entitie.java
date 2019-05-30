package com.mcforsas.game.engine.core;

import com.mcforsas.game.GameLauncher;

/**
 * @author MCForsas @since 3/22/2019
 * Holds main entitie lifecycle methods
 */
public abstract class Entitie implements InputListener {

    /**
     * All assets and values should be set here for this to function correctly.
     */
    public void start(){

    }

    /**
     * Game logic
     */
    public void update(){

    }

    /**
     * End all game logic. Remove all dependencies.
     */
    public void end(){
        try {
            GameLauncher.getInputHandler().removeInputListener(this);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * Called on application close;
     */
    public void dispose(){

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

    @Override
    public void touchDragged(float x, float y) {

    }
}
