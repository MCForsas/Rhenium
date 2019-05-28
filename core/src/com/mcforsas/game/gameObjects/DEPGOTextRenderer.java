package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;
/**
 * Created by mcforsas on 19.5.28
 * Renders text when font provided. Centers it if needed, calculates positions
 */
public class DEPGOTextRenderer extends GameObject {
    private String text = "";
    private BitmapFont font;
    private float x, y, drawX, drawY;
    private GlyphLayout layout;
    private float maxScale = 100;
    private float minScale = 0.0001f;
    private boolean centerText = true;

    public DEPGOTextRenderer(BitmapFont font, float x, float y) {
        this.x = x;
        this.y = y;
        this.font = font;

        layout = new GlyphLayout(font,"1337");
    }

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
        drawX = x;
        drawY = y;

        layout.setText(font, text);

        font.setColor(Color.WHITE);
        font.draw(spriteBatch, layout, drawX, drawY);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        float scale = 1f/(float) this.text.length();
        scale = Utils.clamp(scale, minScale, maxScale);

        font.getData().setScale(scale);
    }

    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
        float scale = 1f/(float) this.text.length();
        scale = Utils.clamp(scale, minScale, maxScale);

        font.getData().setScale(scale);
    }

    public boolean isCenterText() {
        return centerText;
    }

    public void setCenterText(boolean centerText) {
        this.centerText = centerText;
    }
}
