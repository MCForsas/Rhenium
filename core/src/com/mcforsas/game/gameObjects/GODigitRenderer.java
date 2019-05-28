package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;

import java.util.HashMap;

/**
 * Created by mcforsas on 19.5.28
 * Renders digits. Monospaced font
 */
public class GODigitRenderer extends GameObject {
    private int number; //Number to render;
    private int numberLength = 0;
    private float charScale = 0f;

    private float height = 1f; //If it's 0, width will be used
    private float width = 0;

    private final int TEXTURE_HEIGHT = 32; //Texture height in px
    private final int TEXTURE_WIDTH = 16; //Texture width in px

    private HashMap<Integer, Sprite> spriteHashMap;

    public static final String TEXTURE_PREFIX = "sprDig";

    public GODigitRenderer(int number) {
        this.number = number;
        this.numberLength = String.valueOf(number).length();

        for(int i = 0; i < 10; i++){
            spriteHashMap.put(i, new Sprite(GameLauncher.getAssetHandler().getTexture(TEXTURE_PREFIX + i)));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        for(int i = 0; i < numberLength; i++){
            Sprite spr = spriteHashMap.get((int) String.valueOf(number).charAt(i));
            spr.draw(spriteBatch);
        }
    }

    private void refreshTextures(){
        for(int i = 0; i < numberLength; i++){
            Sprite spr = spriteHashMap.get((int) String.valueOf(number).charAt(i));
            spr.setScale(charScale);
            spr.setPosition(x + charScale*i, y);
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        if(height > 0){
            charScale = height/TEXTURE_HEIGHT;
        }
        refreshTextures();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        if(width > 0){
            charScale = width/TEXTURE_WIDTH;
        }
        refreshTextures();
    }
}
