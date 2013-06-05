/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mistralworks.content;

import java.util.Hashtable;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Andri Ihsannudin
 */
public final class ContentManager {

    Hashtable content;

    public ContentManager() {
        content = new Hashtable();
    }

    public final Image loadImage(String path) {
        Image ret = null;

        if (content.containsKey(path)) {
            ret = (Image) content.get(path);
        } else {
            try {
                ret = Image.createImage(path);
                content.put(path, ret);
            } catch (Exception e) {
                System.out.println("Error loading " + path);
            }
        }

        return ret;
    }
    
    public final void dispose() {
        content.clear();
        content = null;
    }
}
