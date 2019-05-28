package com.mcforsas.game.engine.core;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author MCForsas @since 3/25/2019
 * Saves any game variables that should be saved by key -> pair.
 */
public class GameData{
    private static final long serialVersionUID = 1;

    private HashMap<String, Object> gameData; //All of the game data;

    public GameData(){
        if(gameData == null)
            gameData = new HashMap<String, Object>();
    }

    /**
     * Adds data to the list
     * @param key
     * @param object
     */
    public void addData(String key, Object object){
        gameData.put(key, object);
    }

    /**
     * Returns data which was got
     * @param key
     */
    public Object getData(String key){
        return gameData.get(key);
    }

    /**
     * Returns data which was got. If it's null, default passed value will be returned.
     * @param key
     * @param defaultObject
     */
    public Object getDataDefault(String key, Object defaultObject){
        if(gameData.get(key) == null){
            return defaultObject;
        }
        return gameData.get(key);
    }
}
