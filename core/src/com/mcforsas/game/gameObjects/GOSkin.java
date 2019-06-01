package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.FileHandler;

/**
 * @author mcforsas on
 * @desc Replace this text by description, of what this code is for
 */

public class GOSkin extends GameObject {
    private GOSkinShop shop;
    private int index;
    private boolean isUnlocked;
    public static final String SKIN_PREFIX = "sprSkin";
    private final float skinDimensions = 3f;
    //private ShaderProgram shaderProgram;
    private boolean isSelectable; //If the obect is in the shop
    private Texture lockedTexture;
    private Texture unlockedTexture;

    public GOSkin(GOSkinShop shop, int index, boolean isUnlocked, float x, float y) {
        this.shop = shop;
        this.index = index;
        this.isUnlocked = isUnlocked;
        this.x = x;
        this.y = y;

        //Load a sprite by index
        this.sprite = new Sprite(
          GameLauncher.getAssetHandler().getTexture(SKIN_PREFIX + index)
        );
        unlockedTexture = sprite.getTexture();
        lockedTexture = GameLauncher.getAssetHandler().getTexture("sprBadlogic");

        sprite.setSize(skinDimensions,skinDimensions);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x,y);

//        if(!isUnlocked){
//            String shaderVertex = FileHandler.readFileString("shaders/shader_allBlack_vertex.glsl");
//            String shaderFragment =  FileHandler.readFileString("shaders/shader_allBlack_fragment.glsl");
//        }

        Engine.getInputHandler().addInputListener(this);
        setDepth(-10);
        setUnlocked(isUnlocked);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
        spriteBatch.setShader(null);
    }

    @Override
    public void touchUp(float x, float y) {

        if(Utils.isOnSprite(sprite,x,y)){
            shop.onClick(this);
        }

        super.touchUp(x, y);
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        isSelectable = selectable;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
        if(isUnlocked){
            sprite.setTexture(unlockedTexture);
        }else{
            sprite.setTexture(lockedTexture);
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
