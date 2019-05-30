package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;

import java.util.HashMap;

/**
 * Created by mcforsas on 19.5.28
 * Renders digits. Monospaced font
 */
public class GODigitRenderer extends GameObject {
    private int number; //Number to render;
    private int numberLength;

    private float height = 0f; //If it's 0, width will be used
    private float width = 0f;
    private float spacing = 0;

    private HashMap<String, Texture> textureHashMap = new HashMap<String, Texture>();

    public static final String TEXTURE_PREFIX = "sprDig";

    public GODigitRenderer(int number, float x, float y) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.numberLength = String.valueOf(number).length();

        for(int i = 0; i < 10; i++){
            textureHashMap.put(String.valueOf(i), GameLauncher.getAssetHandler().getTexture(TEXTURE_PREFIX + i));
        }

        setDepth(-10);

    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        if(width == 0){
            width = height/2f;
        }
        if(height == 0){
            height = width*2f;
        }

        for(int i = 0; i < numberLength; i++){
            spriteBatch.draw(
                    textureHashMap.get(String.valueOf(String.valueOf(number).charAt(i))),
                    x + (width+spacing)*i,
                    y,
                    width,
                    height
            );
        }
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.numberLength = String.valueOf(number).length();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    /**
     * Sets the width for whole string
     * @param width
     */
    public void setStringWidth(float width){
        this.width = width/numberLength;
        this.height = this.width*2;
    }

    public float getStringWidth(){
        return this.width * (numberLength + spacing);
    }
}
