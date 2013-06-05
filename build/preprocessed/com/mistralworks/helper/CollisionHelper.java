// -----------------------------------------------------------------------------
// CollisionHelper.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.helper;

/**
 *
 * @author Andri Ihsannudin
 */
public class CollisionHelper {

    public static boolean collided(int x, int y, int x0, int y0, int w0, int h0) {
        boolean ret = false;
        
        ret = (x >= x0 && x <= x0 + w0) && (y >= y0 && y <= y0 + h0); 
        
        return ret;
    }
}
