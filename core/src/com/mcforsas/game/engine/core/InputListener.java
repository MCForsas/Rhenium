package com.mcforsas.game.engine.core;

/**
 * @author MCForsas @since 3/16/2019
 * Used as listener interface
 */
public interface InputListener {
    /**
     * Called when user touches the screen.
     */
    void touchDown(final float x, final float y);

    /**
     * Called when user releases finger from the screen.
     */
    void touchUp(final float x, final float y);

    /**
     * Called when user presses key on keyboard.
     */
    void keyDown(final int keycode);

    /**
     * Called when user releases key on keyboard.
     */
    void keyUp(final int keycode);

    void touchDragged(final float x, final float y);

    void mouseMoved(final float x, final float y);
}
