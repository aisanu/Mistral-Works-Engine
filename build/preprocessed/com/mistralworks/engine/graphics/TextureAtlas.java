// -----------------------------------------------------------------------------
// TextureAtlas.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.engine.graphics;

import com.mistralworks.engine.SpriteFrame;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Andri Ihsannudin
 */
public final class TextureAtlas {
    final Image image;
    final Hashtable frames;

    public TextureAtlas(final Image img) {
        this.image = img;
        this.frames = new Hashtable();
    }
    
    public final void addFrame(final String frameName, final SpriteFrame frame) {
        frames.put(frameName, frame);
        System.out.println(frameName + " " + frame);
    }
    
    public final SpriteFrame getFrame(final String name) {
        return (SpriteFrame)frames.get(name);
    }
    
    public final int totalFrames() {
        return frames.size();
    }
    
    public final SpriteFrame[] getFrames() {
        SpriteFrame[] ret = new SpriteFrame[frames.size()];
        Enumeration keys = frames.elements();
        
        for(int i = 0; i < ret.length; i++) {
            ret[i] = (SpriteFrame) keys.nextElement();
        }
        
        return ret;
    }
    
    public final void dispose() {
        frames.clear();
    }
    
    public final Image getImage() {
        return image;
    }
    
    public static final SpriteFrame[] getSpriteFrames(final TextureAtlas atlas, final String[] frameNames) {
        SpriteFrame[] frames = new SpriteFrame[frameNames.length];
        for(int i = 0; i < frameNames.length; i++) {
            frames[i] = atlas.getFrame(frameNames[i]);
            System.out.println(frameNames[i] + ": " + atlas.getFrame(frameNames[i]));
        }
        
        return frames;
    }
}
