package com.mcforsas.game.engine.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;


/**
 * @author MCForsas @since 3/16/2019
 * Packs a bunch of useful functions.
 */
public final class Utils {

    private Utils(){  }

    public static final String WARNING_TAG = "warning";

    /**
     * Clamps a value between min and max
     * @param val
     * @param min
     * @param max
     * @return float clamped value
     */
    public static float clamp(float val, float min, float max){
        if(val > max){
            return max;
        }
        if(val < min){
            return min;
        }
        return val;
    }

    /**
     * Clamps value to be in range of a point
     * @param val
     * @param point
     * @param range
     * @return val
     */
    public static float clampRange(float val, float point, float range){
        return clamp(val, point - range, point + range);
    }

    //region <Random based methods>

    /**
     * Chooses random object from given ones
     * @param  objects to choose from
     * @return Object
     */
    public static Object choose(Object... objects) {
        Random r = new Random();
        return objects[r.nextInt(objects.length)];
    }

    /**
     * Returns random int between 0 and max
     * @return int
     */
    public static final int irandom(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    /**
     * Returns random int between min and max
     * @return int
     */
    public static int irandomRange(int min, int max) {
        Random r = new Random();
        return (r.nextInt((Math.abs(max - min)) + 1) + min);

    }

    /**
     * Returns true at given chance
     * @param percentage number between 0 - 100
     * @return boolean chance true chance% of the time
     */
    public static boolean chance(int percentage) {
        Random r = new Random();
        return percentage > r.nextInt(100);
    }

    /**
     * Returns first object chance% of the time and second object  100% - chance% of the time
     * @param object1
     * @param object2
     * @param percentage number between 0 - 100
     * @return Object chosen object
     */
    public static Object pick(Object object1, Object object2, int percentage) {
        return chance(percentage) ? object2 : object1;
    }
    //endregion

    public static boolean flipBoolean(boolean bool){
        return !bool;
    }

    /**
     * returns number between 0 and 1, which is eased in.
     * @param progress
     * @return eased out
     */
    public static float easeIn(float progress){
        float y = (float) Math.pow(Math.sin(5*progress/Math.PI),2);
        return y;
    }

    /**
     * returns number between 0 and 1, which is eased in.
     * @param progress
     * @return eased in
     */
    public static float easeOut(float progress){
        progress++;
        float y = (float) Math.pow(Math.sin(5*progress/Math.PI),2);
        return 1 - y;
    }

    /**
     * returns number between 0 and 1, which is eased in.
     * @param progress
     * @return
     */
    public static float parametricEaseIn(float progress){
        float sqt = (float) Math.pow(progress,2);
        return sqt / (2.0f * (sqt - progress) + 1.0f);
    }

    /**
     * Approaches one value to other, by given completion amount (0 - 1);
     * @param completion completion rating from 0 to 1
     * @param from start value
     * @param to end value
     */
    public static float approach(float completion, float from, float to){
        return from + (to - from) * completion;
    }

    /**
     * Lerps value
     * @param val
     * @param toVal
     * @param lerp
     * @return
     */
    public static float lerp(float val, float toVal, float lerp){
        return (toVal - val) * lerp;
    }

    /**
     * Returns min possible value from val. Floor is max
     * @param max
     * @param val
     * @return
     */
    public static float min(float max, float val){
        return (max < val) ? max : val;
    }

    /**
     * Returns max possible value from val. Floor is min
     * @param min
     * @param val
     * @return
     */
    public static float max(float min, float val){
        return (min > val) ? min : val;
    }

    /**
     * Wrap
     */

    public static float wrap(float val, float min, float max){
        if (val % 1 == 0){
            while (val > max || val < min) {
                if (val > max)
                    val += min - max - 1;
                else if (val < min)
                    val += max - min + 1;
            }
            return(val);
        }else{
            float vOld = val + 1;
            while (val != vOld){
                vOld = val;
                if (val < min)
                    val = max - (min - val);
                else if (val > max)
                    val = min + (val - max);
            }
            return(val);
        }
    }

    /**
     * Checks if value falls in range between two other values
     * @param value value to check
     * @param min min value
     * @param max max value
     * @return inRange is in range?
     */
    public static boolean isInRange(float value, float min, float max){
        return (value >= min && value <= max);
    }

    /**
     * Returns if int is in array
     * @param array
     * @param searched
     * @return boolean if is in array
     */
    public static boolean isInArray(int[] array, int searched) {
        for (int o : array) {
            if (o == searched)
                return true;
        }
        return false;
    }


    /**
     * Prints formated string as warning
     * @param format string
     * @param objects to  format
     */
    public static void warnf(String format, Object ... objects){
        Gdx.app.log(WARNING_TAG, String.format(format, objects));
    }

    /**
     * Prints formated string as warning
     * @param message
     */
    public static void warn(Object message){
        Gdx.app.log(WARNING_TAG, message.toString());
    }

    /**
     * Checks if given coordinates are overlaping sprite
     * @param sprite
     * @param x
     * @param y
     * @return
     */
    public static boolean isOnSprite(Sprite sprite, float x, float y){
        return sprite.getBoundingRectangle().contains(x,y);
    }

    /**
     * Checks if sprites' rectangles collide
     * @param sprite1
     * @param sprite2
     * @return
     */
    public static boolean isSpriteCollisionRectangle(Sprite sprite1, Sprite sprite2){
        return (sprite1.getBoundingRectangle().overlaps(sprite2.getBoundingRectangle()));
    }

    public static float distanceBetweenPoints(float x1, float y1, float x2, float y2){
        return (float) Math.abs(Math.hypot((x1 - x2), (y1 - y2)));
    }
}
