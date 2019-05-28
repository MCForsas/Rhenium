package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.core.Renderable;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.exceptions.IncompatibleViewportException;

import java.util.*;

/**
 * @author MCForsas @since 3/16/2019
 * Renders all the added entities
 */
public class RenderHandler {
    private Vector<Camera> cameras = new Vector<Camera>();
    private Camera currentCamera;

    private Vector<Viewport> viewports = new Vector<Viewport>();
    private Viewport currentViewport;

    private Vector<Renderable> renderables = new Vector<Renderable>();//All rendered items

    //Camera positions
    private float xTo = 0, yTo = 0;
    public static final float CAMERA_Z = 0;
    private boolean isCameraBounded = false; //Weather camera is allowed to leave the level

    //Maximum allowed deviation from regular aspect ratio
    private float maxAspectDeviation = 0;

    //Defaults
    private static final float DINAMIC_CAMERA_MOVESPEED = 1f;
    private static final float DINAMIC_CAMERA_MAXRADIUS = 10f;



    public void setup(Camera camera, Viewport viewport){
        addCemera(camera);
        setCurrentCamera(camera);

        addViewport(viewport);
        setCurrentViewport(viewport);
    }

    /**
     * Setup default renderer defaults: Ortographic camera, ExtendViewPort and position camera in
     * the center
     */
    public void setupDefault(){
        setup(
                new CameraHandler(DINAMIC_CAMERA_MOVESPEED, DINAMIC_CAMERA_MAXRADIUS),
                new ExtendViewport(
                        GameLauncher.getWorldWidth(), GameLauncher.getWorldHeight(),
                        currentCamera)
        );
        addViewport(currentViewport);

    }

    public void setup(Camera camera, ExtendViewport viewport, float maxAspectDeviation){
        setup(camera, viewport);
        setMaxAspectDeviation(maxAspectDeviation);

        setViewportMaxDimensions(
                currentViewport.getWorldWidth() * (1 + maxAspectDeviation),
                currentViewport.getWorldHeight() * (1 + maxAspectDeviation)
        );
    }



    public void render(SpriteBatch sb, float deltaTime) {
        Gdx.gl.glClearColor(.02f, 0f, .04f, 1); //Set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (currentCamera instanceof CameraHandler) {
            ((CameraHandler) currentCamera).updatePosition(xTo, yTo, deltaTime);
        }else{
            currentCamera.position.set(xTo, yTo, CAMERA_Z);
        }

        if (isCameraBounded){
            boundCamera(currentCamera);
        }

        currentCamera.update();
        currentViewport.apply();

        sb.setProjectionMatrix(currentCamera.combined);

        sb.begin();
        for(int i = 0; i < renderables.size(); i++){
            renderables.get(i).render(sb, deltaTime);
        }
        sb.end();
    }

    /**
     * Sorts entities based on depth value
     */
    public void refreshRenderOrder(){
        HashMap<Renderable, Float> tempList = new HashMap<Renderable, Float>();
        ValueComparator bvc = new ValueComparator(tempList);
        TreeMap<Renderable, Float> sortedMap = new TreeMap<Renderable, Float>(bvc);

        for(int i = 0; i < renderables.size(); i++){
            Renderable r = renderables.get(i);
            tempList.put(r, r.getDepth());
        }

        sortedMap.putAll(tempList);
        renderables.clear();

        Vector<Renderable> sortedRenderables = new Vector<Renderable>(sortedMap.keySet());
        for(int i = 0; i < sortedRenderables.size(); i++){
            renderables.add(sortedRenderables.get(i));
        }

        renderables = sortedRenderables;
    }

    public void resize(int width, int height){
        currentViewport.update(width, height);
    }
    //region <Camera>

    private void boundCamera(Camera camera){

        float worldWidth = GameLauncher.getWorldWidth();
        float worldHeight = GameLauncher.getWorldHeight();

        try {
            Level level = GameLauncher.getLevelHandler().getCurrentLevel();
            worldWidth = level.getWidth();
            worldHeight = level.getHeigth();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

            camera.position.x = Utils.clamp(
                    camera.position.x,
                    (camera.viewportWidth/2),
                    (worldWidth - camera.viewportWidth/2)
            );

            camera.position.y = Utils.clamp(
                    camera.position.y,
                    camera.viewportHeight/2,
                    (worldHeight - camera.viewportHeight/2)
            );

            //To avoid shaky screen position camera in the center if viewport dimensions are bigger than worlds.
            if(currentViewport.getWorldWidth() >= worldWidth || currentViewport.getWorldHeight() >= worldHeight){
                camera.position.x = worldWidth/2;
                camera.position.y = worldHeight/2;
            }
    }
    //endregion

    public Camera getCamera() {
        return currentCamera;
    }

    public void setCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }

    public void addCemera(Camera camera){
        cameras.add(camera);
    }

    public void removeCamera(Camera camera) {
        cameras.remove(camera);
    }

    public void setCameraPosition(float x, float y){
        xTo = x;
        yTo = y;
    }

    public void translateCamera(float deltaX, float deltaY){
        xTo += deltaX;
        yTo += deltaY;
    }

    //region <Viewport>
    public void addViewport(Viewport viewport){
        viewports.add(viewport);
    }

    public void removeViewport(Viewport viewport){
        viewports.remove(viewport);
    }

    /**
     * Max dimensions before viewport starts letterboxing
     * @param viewport to apply to
     * @param width size in px
     * @param height size in px
     */
    private void setViewportMaxDimensions(Viewport viewport, float width, float height) throws IncompatibleViewportException{
        if(viewport.getClass() == ExtendViewport.class){
            ((ExtendViewport) viewport).setMaxWorldWidth(width);
            ((ExtendViewport) viewport).setMaxWorldHeight(height);
        }else{
            throw new IncompatibleViewportException("Tried to set max world dimension's for viewport which is not an ExtendViewport");
        }
    }

    public void setViewportMaxDimensions(float width, float height) {
        try {
            setViewportMaxDimensions(currentViewport, width, height);
        }catch (IncompatibleViewportException e){
            e.printStackTrace();
        }

    }
    //endregion

    //region <Getters and setters>
    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
        refreshRenderOrder();
    }

    public void removeRenderable(Renderable renderable){
        renderables.remove(renderable);
    }

    public boolean isCameraBounded() {
        return isCameraBounded;
    }

    public void setCameraBounded(boolean cameraBounded) {
        isCameraBounded = cameraBounded;
    }

    public Viewport getCurrentViewport() {
        return currentViewport;
    }

    public void setCurrentViewport(Viewport viewport) {
        this.currentViewport = viewport;
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public void setCurrentCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }

    public float getMaxAspectDeviation() {
        return maxAspectDeviation;
    }

    public void setMaxAspectDeviation(float maxAspectDeviation) {
        this.maxAspectDeviation = maxAspectDeviation;
    }

    //endregion

    /**
     * Used for Sorting renderables array
     */
    private class ValueComparator implements Comparator<Renderable> {
        Map<Renderable, Float> base;

        public ValueComparator(Map<Renderable, Float> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(Renderable a, Renderable b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
