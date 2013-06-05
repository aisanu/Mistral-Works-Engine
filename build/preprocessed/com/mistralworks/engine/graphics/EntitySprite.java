// -----------------------------------------------------------------------------
// EntitySprite.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------

package com.mistralworks.engine.graphics;

import com.mistralworks.engine.SpriteFrame;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Andri Ihsannudin
 */
public final class EntitySprite {
    SpriteFrame[] frameSequence;
    int activeFrame;
    final TextureAtlas atlas;
    int x, y;
    
    EntitySprite[] childs;
    int _childNumber = 0;

    public EntitySprite(final TextureAtlas atlas, final SpriteFrame[] frames) {
        this.atlas = atlas;
        this.frameSequence = frames;
        activeFrame = 0;
        
        childs = new EntitySprite[2];
    }
    
    public void addChild(EntitySprite child) {
        if(_childNumber >= childs.length) {
            EntitySprite temp[] = new EntitySprite[childs.length * 2];
            for(int i = 0; i < childs.length; i++) {
                temp[i] = childs[i];
            }
            childs = temp;
        }
        childs[_childNumber] = child;
        _childNumber++;
    }
    
    public final void setFrames(String frame) {
        frameSequence = new SpriteFrame[1];
        frameSequence[0] = atlas.getFrame(frame);
        activeFrame = 0;
    }
    
    public final void setFrameSequence(final String[] sequence) {
        this.frameSequence = TextureAtlas.getSpriteFrames(atlas, sequence);
        activeFrame = 0;
        
    }
    
    public final void nextFrame() {
        activeFrame = (activeFrame + 1) % frameSequence.length;
    }
    
    public final void setPosition(final int _x, final int _y) {
        this.x = _x;
        this.y = _y;
    }
    
    public final void paint(final Graphics g) {
        g.drawRegion(
                atlas.image, 
                frameSequence[activeFrame].x, frameSequence[activeFrame].y, 
                frameSequence[activeFrame].w, frameSequence[activeFrame].h, 0, 
                x + frameSequence[activeFrame].originX, y + frameSequence[activeFrame].originY, 20
                );
        for(int i = 0 ; i < _childNumber; i++) {
            childs[i].paint(g, x, y);
        }
    }

    public final void paint(final Graphics g, final int offsetX, final int offsetY) {
        g.drawRegion(
                atlas.image, 
                frameSequence[activeFrame].x, frameSequence[activeFrame].y, 
                frameSequence[activeFrame].w, frameSequence[activeFrame].h, 0, 
                x + offsetX + frameSequence[activeFrame].originX, y + offsetY + frameSequence[activeFrame].originY, 20
                );
        for(int i = 0 ; i < _childNumber; i++) {
            childs[i].paint(g, x + offsetX, y + offsetY);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

}
