package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mcforsas.game.engine.core.Utils;

/**
 * @author MCForsas @since 3/30/2019
 * Manages camera. Adds some more functions to it.
 */
public class CameraHandler extends OrthographicCamera {
    private float xTo, yTo;
    private float moveSpeed;
    private float maxRadius;
    private boolean isDinamic;

    public CameraHandler(){
        isDinamic = false;
    }

    public CameraHandler(float moveSpeed, float maxRadius){
        super();
        this.isDinamic = true;
        this.moveSpeed = moveSpeed;
        this.maxRadius = maxRadius;
    }

    @Override
    public void update() {
        super.update();
    }

    public void updatePosition(float xTo, float yTo, float deltaTime){
        this.xTo = xTo;
        this.yTo = yTo;

        float ms = moveSpeed * Math.abs((xTo - position.x) + (yTo - position.y)/2);

        if(isDinamic){
            position.x += Utils.lerp(position.x, xTo, ms) * deltaTime;
            position.y += Utils.lerp(position.y, yTo, ms) * deltaTime;

            position.x = Utils.clampRange(position.x, xTo, maxRadius);
            position.y = Utils.clampRange(position.y, yTo, maxRadius);
        }
    }

    public float getxTo() {
        return xTo;
    }

    public void setxTo(float xTo) {
        this.xTo = xTo;
    }

    public float getyTo() {
        return yTo;
    }

    public void setyTo(float yTo) {
        this.yTo = yTo;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(float maxRadius) {
        this.maxRadius = maxRadius;
    }

    public boolean isDinamic() {
        return isDinamic;
    }

    public void setDinamic(boolean dinamic) {
        isDinamic = dinamic;
    }
}
