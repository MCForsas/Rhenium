package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.InputListener;

import java.util.HashSet;
import java.util.Vector;

/**
 * @author MCForsas @since 3/16/2019
 * Handles input. When key is pressed, calls input listeners
 */
public class InputHandler implements InputProcessor {
    private Vector<InputListener> listeners = new Vector<InputListener>(); //Listeners

    private static float touchX = 0;

    //region <Call listeners and overrride methods>
    @Override
    public boolean keyDown(int keycode) {
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).keyDown(keycode);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).keyUp(keycode);
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //TODO: fix this
        touchX = screenX;

        Vector3 worldCoordinates =
                GameLauncher.getRenderHandler().
                        getCamera().
                        unproject(new Vector3(screenX, screenY, 0));
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).touchDown(worldCoordinates.x,worldCoordinates.y);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //TODO:
        touchX = screenX;

        Vector3 worldCoordinates =
                GameLauncher.getRenderHandler().
                        getCamera().
                        unproject(new Vector3(screenX, screenY, 0));
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).touchUp(worldCoordinates.x, worldCoordinates.y);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 worldCoordinates =
                GameLauncher.getRenderHandler().
                        getCamera().
                        unproject(new Vector3(screenX, screenY, 0));
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).touchDragged(worldCoordinates.x, worldCoordinates.y);
        }
        return true;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).mouseMoved(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    //endregion

    public void addInputListener(InputListener listener){
        listeners.add(listener);
    }

    public void removeInputListener(InputListener listener){
        listeners.remove(listener);
    }

    public boolean isKeyDown(final int keycode){
        return Gdx.input.isKeyPressed(keycode);
    }

    public boolean isButtonDown(final int keycode){return Gdx.input.isButtonPressed(keycode);}

    public static float getTouchX() {
        return touchX;
    }
}