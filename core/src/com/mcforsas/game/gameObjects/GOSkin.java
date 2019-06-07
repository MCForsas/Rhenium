package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameData;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.FileHandler;

import java.awt.image.SinglePixelPackedSampleModel;

/**
 * @author mcforsas on
 * An object which is used to choose a car skin or unlock a new one
 */

public class GOSkin extends GameObject {

    private GOSkinShop shop;
    private int index; //Skin to unlock index

    public static final String SKIN_PREFIX = "sprSkin";
    public static final float skinDimensions = 24f;
    private boolean isUnlocked;
    private boolean isSelected;

    private Texture lockedTexture;
    private Texture unlockedTexture;

    private Sprite selected;

    private boolean maySelect = false; //If the user has tapped on the item but not released the cursor yet

    public GOSkin(GOSkinShop shop, int index, boolean isUnlocked, float x, float y) {
        this.shop = shop;
        this.index = index;
        this.isUnlocked = isUnlocked;
        this.x = x;
        this.y = y;

        setUnlocked(isUnlocked);

    }

    @Override
    public void start() {
        //Load a sprite by index
        this.sprite = new Sprite(
                GameLauncher.getAssetHandler().getTexture(SKIN_PREFIX + index)
        );

        unlockedTexture = sprite.getTexture();
        lockedTexture = GameLauncher.getAssetHandler().getTexture("sprShopLocked");

        selected = new Sprite(AssetHandler.getTexture("sprShopSelect"));
        selected.setSize(skinDimensions,skinDimensions);
        selected.setOriginCenter();
        selected.setOriginBasedPosition(x,y);

        sprite.setSize(skinDimensions,skinDimensions);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x,y);

        Engine.getInputHandler().addInputListener(this);
        setDepth(-10);
        super.start();
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
        if(isSelected){
            selected.draw(spriteBatch);
        }
    }

    @Override
    public void update(float deltaTime) {
        sprite.setOriginBasedPosition(x,y);
        selected.setOriginBasedPosition(x,y);
        super.update(deltaTime);
    }

    @Override
    public void touchDown(float x, float y) {
        if(Utils.isOnSprite(sprite,x,y)){
            maySelect = true;
        }
        super.touchDown(x, y);
    }

    @Override
    public void touchUp(float x, float y) {

        //If the user has touched and released the finger on the same sprite, buy the skin
        if(Utils.isOnSprite(sprite,x,y) && maySelect){
            shop.onClick(this);
        }

        maySelect = false;
        super.touchUp(x, y);
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
        if(isUnlocked == true){
            sprite.setTexture(unlockedTexture);
        }else{
            sprite.setTexture(lockedTexture);
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
