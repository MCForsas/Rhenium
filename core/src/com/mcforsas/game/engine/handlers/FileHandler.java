package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mcforsas.game.engine.core.GameData;

/**
 * @author MCForsas @since 3/25/2019
 * Reads and writes from files. Handles preferences and gameData JSON.
 */
public class FileHandler {
    public static final String FILE_PATH = "files/";
    public final String PREFERENCES_NAME = "rheniumPreferences";

    private FileHandle fileHandle;
    private Preferences preferences;
    private Json json;
    private boolean isEncoded;

    //region <File handling>
    public static FileHandle openFile(String fileName){
        return Gdx.files.local(FILE_PATH + fileName);
    }

    public static String readFileString(String fileName){
        return Gdx.files.local(FILE_PATH + fileName).readString();
    }
    //endregion

    //region <Game data>
    /**
     * Opens file. If encode, encodes/decodes file data
     * @param isEncoded wheather information is encoded or not.
     */
    public FileHandler(String fileName, boolean isEncoded){
        this.isEncoded = isEncoded;

        this.fileHandle = Gdx.files.local(FILE_PATH + fileName);

        this.json = new Json(JsonWriter.OutputType.json);
        this.preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    /**
     * Saves gameData object to json file.
     * @param gameData
     */
    public void saveGameData(GameData gameData){
        String writeString = json.prettyPrint(gameData);
        if(isEncoded){
            writeString = Base64Coder.encodeString(writeString);
        }
        try {

            fileHandle.writeString(writeString, false);
        }catch (Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     * Loads game data from file or null if none was saved
     * @return gameData loaded data
     */
    public GameData loadGameData(){
        GameData data = null;
        String readString = "";

        try{
            readString = fileHandle.readString();
        }catch (GdxRuntimeException e){
            e.printStackTrace();
            data = new GameData();
            fileHandle.write(true);
            return data;
        }

        if(isEncoded){
            try {
                readString = Base64Coder.decodeString(readString);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

        }

        try {
            data = json.fromJson(GameData.class, readString);
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }
    //endregion

    //region <Preferences>

    /**
     * Puts preference to the preferences file
     * @param key
     * @param value
     * @throws if passed object is of incompatible type with preferences
     */
    public void putPreferences(String key, Object value) throws IllegalArgumentException{
        if(value instanceof String){
            putPreferencesString(key, (String) value);
        }else if(value instanceof Boolean){
            putPreferencesBoolean(key, (Boolean) value);
        }else if(value instanceof Integer){
            putPreferencesInt(key, (Integer) value);
        }else if(value instanceof Float){
            putPreferencesFloat(key, (Float) value);
        }else if(value instanceof Long){
            putPreferencesLong(key, (Long) value);
        }else {
            throw new IllegalArgumentException("Unknown type of: " + value.getClass());
        }
    }

    /**
     * Gets preferences from preference file. If none are found, default data is returned
     * @param key
     * @param type of data
     * @param defaultValue default data
     * @return
     */
    public Object getPreferences(String key, Class type, Object defaultValue){
        Object data = null;

        if(!preferences.contains(key)){
            return defaultValue;
        }

        if(type == String.class){
            data =  getPreferencesString(key);
        }else if(type == Boolean.class){
            data = getPreferencesBoolean(key);
        }else if(type == Integer.class){
            data = getPreferencesInt(key);
        }else if(type == Float.class){
            data = getPreferencesFloat(key);
        }else if(type == Long.class){
            data = getPreferencesLong(key);
        }

        return (data != null ? data : defaultValue);
    }

    public void putPreferencesString(String key, String value) {
        preferences.putString(key, value);
    }

    public String getPreferencesString(String key) {
        return preferences.getString(key);
    }

    public void putPreferencesInt(String key, int value) {
        preferences.putInteger(key, value);
    }

    public int getPreferencesInt(String key) {
        return preferences.getInteger(key);
    }

    public void putPreferencesBoolean(String key, boolean value) {
        preferences.putBoolean(key, value);
    }

    public boolean getPreferencesBoolean(String key) {
        return preferences.getBoolean(key);
    }

    public void putPreferencesFloat(String key, Float value) {
        preferences.putFloat(key, value);
    }

    public Float getPreferencesFloat(String key) {
        return preferences.getFloat(key);
    }

    public void putPreferencesLong(String key, Long value) {
        preferences.putLong(key, value);
    }

    public Long getPreferencesLong(String key) {
        return preferences.getLong(key);
    }

    public void savePreferences(){
        preferences.flush();
    }
    //endregion

    public void dispose(){
        savePreferences();
    }
}
