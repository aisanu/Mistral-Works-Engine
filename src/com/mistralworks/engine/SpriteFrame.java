// -----------------------------------------------------------------------------
// SpriteFrame.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------

package com.mistralworks.engine;

/**
 *
 * @author Andri Ihsannudin
 */
public final class SpriteFrame {

    public final int x, y;
    public final int w, h;
    public int originX, originY;

    public SpriteFrame(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public final void setOrigin(final int _x, final int _y) {
        this.originX = _x;
        this.originY = _y;
    }
}
