// -----------------------------------------------------------------------------
// AdvancedAnimatedSprite.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.engine;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Andri Ihsannudin
 */
public class AdvancedAnimatedSprite {

    public static final int FPS = 19;
    private long timer;
    protected int x;
    protected int y;
    protected Image image;
    int activeSequence;
    int activeFrame;
    SpriteFrame[] frames;
    int[][] sequences;
    int width, height;
    protected int drawmode;

    public AdvancedAnimatedSprite(Image image, SpriteFrame[] frames, int[][] sequences) {
        this.image = image;
        this.frames = frames;
        this.sequences = sequences;
        timer = 1000 / FPS;

        width = frames[0].w;
        height = frames[0].h;

        drawmode = 0;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void update(long elapsedTime) {
        if (loop) {
            activeFrame = (activeFrame + 1) % sequences[activeSequence].length;
        } else if (activeFrame < sequences[activeSequence].length - 1) {
            activeFrame++;
        } 
    }
    boolean loop = false;

    public void setSequence(int sequenceIndex, boolean loop) {
        activeSequence = sequenceIndex;
        activeFrame = 0;
        this.loop = loop;
    }

    public int getSequence() {
        return activeSequence;
    }

    // naga = 135
    int frameMaxW = 135;
    public void setTransform(int transform, int frameMaxW) {
        this.drawmode = transform;
        this.frameMaxW = frameMaxW;
    }

    protected SpriteFrame currentFrame;
    public void paint(Graphics g) {
        int currentFrameIndex = sequences[activeSequence][activeFrame];
        currentFrame = frames[currentFrameIndex];
        int offsetX = currentFrame.originX, offsetY = currentFrame.originY;
        if((drawmode & Sprite.TRANS_MIRROR) != 0) {
            offsetX = (frameMaxW - currentFrame.w);
//            offsetY -= currentFrame.originY;
        } 

        g.drawRegion(image, currentFrame.x, currentFrame.y, currentFrame.w, currentFrame.h, drawmode, x + offsetX, y + offsetY, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public SpriteFrame getActiveSpriteFrame() {
        int currentFrameIndex = sequences[activeSequence][activeFrame];
        return this.frames[currentFrameIndex];
    }
}
