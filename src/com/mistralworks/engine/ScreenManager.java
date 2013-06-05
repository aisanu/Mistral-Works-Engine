// -----------------------------------------------------------------------------
// ScreenManager.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.engine;

import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Andri Ihsannudin
 */
public final class ScreenManager extends GameCanvas implements Runnable {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static final int DELAY = 1000 / 25;
    public static final int REFRESH_RATE = 1000;
    private final Vector screens;
    private Thread gameLooper;
    private final boolean rotated;
    private final int transformMode;
    private Image bufferImage;
    private Input input;
    private final Vector updatedScreen = new Vector(2);

    public ScreenManager(boolean landscape) {
        super(false);
        
        setFullScreenMode(true);

        // if the game is landscape but the device is potrait
        if (landscape && getWidth() < getHeight()) {
            rotated = true;
            transformMode = Sprite.TRANS_ROT90;
        } else if (!landscape && getWidth() > getHeight()) {
            // if the game is potrait and the device is landscape
            rotated = true;
            transformMode = Sprite.TRANS_ROT90;
        } else {
            rotated = false;
            transformMode = 0;
        }

        SCREEN_WIDTH = getRenderWidth();
        SCREEN_HEIGHT = getRenderHeight();

        // The basic screen size is 2, the screen itself and the pause menu
        screens = new Vector(2);
        input = new Input();

    }
    boolean running = true;

    protected void hideNotify() {
        //this.gameLooper.cancel();
        running = false;
    }

    public final int getRenderWidth() {
        return rotated ? getHeight() : getWidth();
    }

    public final int getRenderHeight() {
        return rotated ? getWidth() : getHeight();
    }

    /*
     * Used for preloading all the assets
     */
    public final void loadContent() {
        for (int i = 0; i < screens.size(); i++) {
            GameScreen gameScreen = (GameScreen) screens.elementAt(i);
            gameScreen.loadContent();
        }
    }
    TimerTask gameLoopTask;

    public final Graphics getBufferGraphics() {
        return bufferG;
    }
    Graphics baseGraphics;
    Graphics bufferG;

    public void showNotify() {
        if (gameLooper == null) {
            gameLooper = new Thread(this);
            gameLooper.setPriority(Thread.MAX_PRIORITY);

            bufferImage =
                    Image.createImage(getRenderWidth(), getRenderHeight());

            bufferG = bufferImage.getGraphics();
            baseGraphics = getGraphics();
            gameLooper.start();
        }
        running = true;
//        else {
//            gameLooper = new Timer();
//            gameLooper.schedule(gameLoopTask, 0, 1000 / 24);
//        }
    }

    public void unloadContent() {
        System.gc();
    }

    /*
     * UPDATE and RENDER
     */
    public void update(long elapsedGameTime) {
        if (!running) {
            return;
        }
        // Using stack system, so last screen is the only screen that
        // read the key input
        int totalScreen = screens.size() - 1;
        updatedScreen.removeAllElements();
        GameScreen gameScreen;
        for (int i = 0; i <= totalScreen; i++) {
            updatedScreen.addElement(screens.elementAt(i));
        }

        totalScreen = updatedScreen.size() - 1;
//        System.out.println("Screen to be updated: " + totalScreen);

        if (totalScreen >= 0) {
            gameScreen = (GameScreen) updatedScreen.elementAt(totalScreen);
            gameScreen.handleInput(input);
        }
        for (int i = totalScreen; i >= 0; i--) {
            gameScreen = (GameScreen) updatedScreen.elementAt(i);
            if ((gameScreen.getScreenStatus() & GameScreen.SCREEN_ACTIVE) != 0) {
                gameScreen.update(elapsedGameTime);
            }
//            System.out.println(gameScreen);
        }
//        System.out.println("Updating : " + screens.size() + " screen with " +1000/elapsedGameTime);
//        screenRefreshTimer += elapsedGameTime;
//        if(screenRefreshTimer > REFRESH_RATE) {
//            DeviceControl.setLights(0, 100);
//        }
    }

    private void initTouchCoordinate(int x, int y) {
        if (rotated) {
            input.touchY = getWidth() - x;
            input.touchX = y;
        } else {
            input.touchX = x;
            input.touchY = y;
        }
    }

    protected void pointerDragged(int x, int y) {
        initTouchCoordinate(x, y);
    }

    protected void pointerPressed(int x, int y) {
        input.touchPressed = true;
        initTouchCoordinate(x, y);
    }

    protected void pointerReleased(int x, int y) {
        input.touchPressed = false;
        initTouchCoordinate(x, y);
    }
    int totalLoop;

    private void render(Graphics g) {
        totalLoop = updatedScreen.size();
        for (int i = 0; i < totalLoop; i++) {
            GameScreen gameScreen = (GameScreen) updatedScreen.elementAt(i);
            if ((gameScreen.getScreenStatus()
                    & GameScreen.SCREEN_RENDERED) != 0) {
//                System.out.println(gameScreen + " " + g);
                gameScreen.paint(g);
            }
        }
    }

    public final void paint(Graphics g) {
        g.drawRegion(
                bufferImage, 0, 0, bufferImage.getWidth(),
                bufferImage.getHeight(), transformMode, 0, 0,
                Graphics.TOP | Graphics.LEFT);
    }

    public void loadContentOnBackground(final GameScreen newScreen, final GameScreen oldScreen) {
        Thread backgroundTimer = new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                oldScreen.unloadContent();
                newScreen.loadContent();
                newScreen.isFinishedLoading = true;
            }
        });
        backgroundTimer.setPriority(Thread.MIN_PRIORITY);
        backgroundTimer.start();
    }

    public synchronized void addScreen(GameScreen newScreen) {
//        if(newScreen == null) System.out.println(newScreen);
        
        screens.addElement(newScreen);
        
        if (newScreen.isFinishedLoading == false) {
            newScreen.loadContent();
            newScreen.isFinishedLoading = true;
        }
//        System.out.println("Tambah: " + newScreen);
        newScreen.onScreenHasFocus();

//        System.out.println("ADD: " + newScreen);
//        for (int i = 0; i < screens.size(); i++) {
//            System.out.println(" -> " + screens.elementAt(i));
//        }
    }

    public synchronized void removeScreen(GameScreen oldScreen) {
        screens.removeElement(oldScreen);
        if (screens.size() > 0) {
            GameScreen lastScreen = (GameScreen) screens.elementAt(screens.size() - 1);
            lastScreen.onScreenHasFocus();
        }
    }
    private long lastUpdate = System.currentTimeMillis();

    public void destroyApp() {
        appIsRunning = false;
    }
    boolean appIsRunning = true;

    public void run() {
        long timeNeeded;
        int last_state = 0;
        while (appIsRunning) {
            long currentUpdate = System.currentTimeMillis();
            input.keyState = getKeyStates();
            input.keyPressed = last_state == input.keyState;
            if (rotated) {
                if ((input.keyState & DOWN_PRESSED) != 0) {
                    input.keyState |= RIGHT_PRESSED;
                    input.keyState -= DOWN_PRESSED;
                } else if ((input.keyState & UP_PRESSED) != 0) {
                    input.keyState |= LEFT_PRESSED;
                    input.keyState -= UP_PRESSED;
                } else if ((input.keyState & RIGHT_PRESSED) != 0) {
                    input.keyState |= UP_PRESSED;
                    input.keyState -= RIGHT_PRESSED;
                } else if ((input.keyState & LEFT_PRESSED) != 0) {
                    input.keyState |= DOWN_PRESSED;
                    input.keyState -= LEFT_PRESSED;
                }
            }
            try {
                update(currentUpdate - lastUpdate);
                //                    long UpdateTime = System.currentTimeMillis() - currentUpdate;
                //                    long renderTime = System.currentTimeMillis();
                render(bufferG);
                paint(baseGraphics);
                //                    renderTime = System.currentTimeMillis() - renderTime;
                flushGraphics();
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
            //                    if(renderTime > UpdateTime) {
            //                        System.out.println("Render bottleneck: " + renderTime);
            //                    }
            lastUpdate = currentUpdate;
            timeNeeded = System.currentTimeMillis() - currentUpdate;
            last_state = input.keyState;
            if (timeNeeded < DELAY) {
                try {
                    Thread.sleep(DELAY - (timeNeeded));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //            System.out.println(DELAY + " " + (currentUpdate - lastUpdate));
        }

    }

    protected void keyPressed(int code) {
    }

    protected void keyRepeated(int code) {
    }

    protected void keyReleased(int code) {
    }
}
