package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;
/**
 * Created by mcforsas on 19.5.28
 * Renders text when font provided. Centers it if needed, calculates positions
 */
public class GOTextRenderer extends GameObject {

    @Override
    public void start() {
        Utils.warn("started");
        super.start();
    }

    public void end() {
        //DEBUG:
        Utils.warn("ended");
        super.end();
    }
/*
    @Override
    public void start() {
        this.layout = new GlyphLayout();
        super.start();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        if(centerText) {
            centerText();
        }else{
            drawX = x;
            drawY = y;
        }

//        font.setColor(Color.WHITE);
//        font.draw(spriteBatch, text, drawX, drawY);
    }

    private void centerText(){
        float scale = 1f/this.text.length();
        scale = Utils.clamp(scale, minScale, maxScale);

        font.getData().setScale(scale);

        layout.setText(font, text);

        drawX = x - layout.width/2f;
        drawY = y + layout.height/2f;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        centerText();
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
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

    public float getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public boolean isCenterText() {
        return centerText;
    }

    public void setCenterText(boolean centerText) {
        this.centerText = centerText;
    }

   */
}
