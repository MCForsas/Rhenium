package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mcforsas.game.engine.exceptions.UnknownAssetTypeException;

import java.util.HashMap;

/**
 * @author mcforsas @since 3/16/2019
 * Handles assets by loading them into memory and keeping them accessible by key.
 */

public class AssetHandler {

    //Data structures
    private static HashMap<String, Texture> textures;
    private static HashMap<String, Sound> sounds;
    private static HashMap<String, Music> music;
    private static HashMap<String, FileHandle> data;
    private static HashMap<String, BitmapFont> fonts;

    //queue
    private static HashMap<String, Class> loadingQueue;
    private static HashMap<String, String> loadingQueuePaths;


    //region <Directories>
    private static final String TEXTURE_DIR = "textures/";
    private static final String SOUND_DIR = "audio/sound/";
    private static final String MUSIC_DIR = "audio/music/";
    private static final String FONTS_DIR = "fonts/";
    private static final String FILES_DIR = "files/";
    //endregion

    private static final boolean ANTI_ALIASING = true;

    public AssetHandler() {
        textures = new HashMap<String, Texture>();
        sounds = new HashMap<String, Sound>();
        music = new HashMap<String, Music>();
        data = new HashMap<String, FileHandle>();
        fonts = new HashMap<String, BitmapFont>();

        loadingQueue = new HashMap<String, Class>();
        loadingQueuePaths = new HashMap<String, String>();
    }

    //region <Load data>

    public void loadTexture(String name, String path) {
        Texture tx = new Texture(Gdx.files.internal(TEXTURE_DIR + path), ANTI_ALIASING);

        if (ANTI_ALIASING) {
            tx.setFilter(
                    Texture.TextureFilter.MipMapLinearLinear,
                    Texture.TextureFilter.MipMapLinearLinear
            );
        }

        textures.put(name, tx);
    }

    public void loadSound(String name, String path) {
        Sound snd = Gdx.audio.newSound(Gdx.files.internal(SOUND_DIR + path));
        sounds.put(name, snd);
    }

    public void loadMusic(String name, String path) {
        Music mus = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_DIR + path));
        music.put(name, mus);
    }

    public void loadFont(String name, String path) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(FONTS_DIR + path));
        fonts.put(name, font);
    }

    public void loadFile(String name, String path) {
        FileHandle f = Gdx.files.internal(FILES_DIR + path);
        data.put(name, f);
    }

    /**
     * Loads any supported asset
     * @param type of asset to load
     * @param name to use when referencing in code
     * @param filePath to load from. Note: asset directories are applied automatically
     * @throws UnknownAssetTypeException if asset type is not supported.
     */
    public void loadAsset(Class type, String name, String filePath) throws UnknownAssetTypeException {
        if (type == Texture.class) {
            loadTexture(name, filePath);
        }else if (type == Sound.class) {
            loadSound(name, filePath);
        }else if (type == Music.class) {
            loadMusic(name, filePath);
        }else if (type == FileHandle.class) {
            loadFile(name, filePath);
        }else if (type == BitmapFont.class) {
            loadFont(name, filePath);
        }else {
            throw new UnknownAssetTypeException("Tried to load an asset which class is not the one listed in asset types");
        }
    }
    //endregion

    //region <Data getters>
    public static Texture getTexture(String name) {
        return textures.get(name);
    }

    public static Sound getSound(String name) {
        return sounds.get(name);
    }

    public static Music getMusic(String name) {
        return music.get(name);
    }

    public static BitmapFont getFont(String name) {
        return fonts.get(name);
    }

    public static FileHandle getFile(String name) {
        return data.get(name);
    }

    /**
     * Get's asset by name. Returns null if no such asset was found
     * @param name
     * @return Object found asset or null.
     */
    public static Object getAsset(String name) {

        if (getTexture(name) != null) {
            return getTexture(name);
        }

        if (getSound(name) != null) {
            return getTexture(name);
        }

        if (getMusic(name) != null) {
            return getTexture(name);
        }

        if (getFont(name) != null) {
            return getTexture(name);
        }

        if (getFile(name) != null) {
            return getTexture(name);
        }

        return null;
    }
    //endregion

    //region <Queue>
    public void addToQueue(Class type, String name, String path) {
        loadingQueue.put(name, type);
        loadingQueuePaths.put(name, path);
    }

    public void removeFromQueu(String name) {
        if (loadingQueue.containsKey(name)) {
            loadingQueue.remove(name);
            loadingQueuePaths.remove(name);
        }
    }

    /*
     * Starts loading assets which are waiting for loading in queue.
     */
    public void startLoadingQueue() {
        for (String name : loadingQueue.keySet()) {
            try {
                loadAsset(loadingQueue.get(name), name, loadingQueuePaths.get(name));
            } catch (UnknownAssetTypeException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    /*
     * Disposes all the assets which are loaded in order to save memory
     */
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }

        for (Sound sound : sounds.values()) {
            sound.dispose();
        }

        for (Music music : music.values()) {
            music.dispose();
        }

        for (BitmapFont bitmapFont : fonts.values()) {
            bitmapFont.dispose();
        }
    }
}
