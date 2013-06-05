// -----------------------------------------------------------------------------
// IGameScreen.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.engine;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Andri Ihsannudin
 */
public abstract class GameScreen {
    public boolean disposing = false;

    /**
     * Mark the screen is updateable
     */
    public static final int SCREEN_ACTIVE = 1;
    /**
     * Mark the screen is rendered
     */
    public static final int SCREEN_RENDERED = 2;
    private int screenStatus;
    ScreenManager manager;
    public boolean isFinishedLoading = false;

    public GameScreen(ScreenManager manager) {
        this.manager = manager;
        screenStatus = SCREEN_ACTIVE | SCREEN_RENDERED;
    }

    // <editor-fold desc="Content">
    public abstract void loadContent();

    public abstract void unloadContent();
    // </editor-fold>
    
    public void onLoadComplete() {
        this.isFinishedLoading = true;
    }
    
    public void onScreenHasFocus() {
        
    }

    // <editor-fold desc="Update and Draw">  
    /**
     * Update the GameScreen
     *
     * @param elapsedTime Total elapsed time in milliseconds since last update.
     */
    public abstract void update(long elapsedTime);

    /**
     * Draw the entire GameScreen
     *
     * @param g Graphics used to render the screen.
     */
    public abstract void paint(Graphics g);

    /**
     * Handle the input of the GameScreen.
     *
     * @param keyState Current key input
     */
    public abstract void handleInput(Input input);
    
    public void pauseGame() {
        setScreenStatus(SCREEN_RENDERED);
    }
    
    public void onKeyPressed(int keyCode) {
        
    }
    
    public void onKeyReleased(int keyCode) {
        
    }
    
    public void onKeyDown(int keyCode) {
        
    }

    // </editor-fold>

    // <editor-fold desc="Get and Set">
    /**
     * Get the current status of the screen use SCREEN_ACTIVE, SCREEN_HIDDEN
     *
     * @return
     */
    public int getScreenStatus() {
        return screenStatus;
    }

    public void setScreenStatus(int screenStatus) {
        this.screenStatus = screenStatus;
    }

    public ScreenManager getManager() {
        return manager;
    }
    // </editor-fold>
}
