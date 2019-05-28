package com.mcforsas.game.engine.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;

/**
 * @author MCForsas @since 3/16/2019
 * Renderable framework. Will be used for rendering. Has a sprite by default.
 */
public abstract class Renderable extends Entitie {
    protected Sprite sprite;

    protected float x = 0, y = 0, z = 0;
    protected float depth = 0;
    protected boolean isRendered = true;

    public boolean WARN_NO_SPRITE = false; //Weather to print stack trace if no sprite is set

    public Renderable(){

    }

    @Override
    public void start() {
        super.start();
        Engine.getRenderHandler().addRenderable(this);
        isRendered = true;
    }

    public void render(SpriteBatch spriteBatch, float deltaTime){
        try {
            sprite.draw(spriteBatch);
        }catch (NullPointerException e){
            if(WARN_NO_SPRITE)
                e.printStackTrace();
        }
    }

    @Override
    public void end() {
        super.end();
        isRendered = false;
        GameLauncher.getRenderHandler().removeRenderable(this);
    }

    //region <Getters and setters>

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        if(depth != this.depth) { //Do not update depth if it's the same, so refreshing render order wont be called
            this.depth = depth;
            try {
                GameLauncher.getRenderHandler().refreshRenderOrder();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public boolean isRendered() {
        return isRendered;
    }

    public void setRendered(boolean rendered) {
        isRendered = rendered;
    }
    //endregion
}
