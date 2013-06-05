// <editor-fold desc="File Description">
// -----------------------------------------------------------------------------
// ContentManager.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
// </editor-fold>
package com.mistralworks.engine;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 * Content helper class. Simplify loading resources for the game.
 *
 * @author Andri Ihsannudin
 */
public class ContentManager {

    /**
     * Make it's impossible to instantiate this class.
     */
    private ContentManager() {
    }

    public static Image loadImage(String path) {
        Image image = null;
        try {
            image = Image.createImage(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;

    }
}
