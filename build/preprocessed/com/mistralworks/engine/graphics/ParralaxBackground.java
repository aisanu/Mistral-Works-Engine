// -----------------------------------------------------------------------------
// ParralaxBackground.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------

package com.mistralworks.engine.graphics;

import com.mistralworks.engine.ScreenManager;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Andri Ihsannudin
 */
public class ParralaxBackground {
    Vector background;
    Vector offsets;
    
    public ParralaxBackground() {
        background = new Vector();
        offsets = new Vector();
    }
    
    public void addBackground(Image image) {
        background.addElement(image);
        offsets.addElement(new int[2]);
    }
    
    public void moveBackground(int index, int x, int y) {
        if(index >= background.size()) return;
        Image imej = (Image)background.elementAt(index);
        int[] pointer = (int[])offsets.elementAt(index);
        pointer[0] += x;
        pointer[1] += y;
        if(pointer[0] <= -imej.getWidth()) {
            pointer[0] += imej.getWidth();
        }
        
        if(pointer[0] >= 0) {
            pointer[0] -= imej.getWidth();
        }
    }
    
    public void paint(Graphics g) {
        paint(0, 0, g);
    }
    
    // _x = camera
    // _y = camera
    public void paint(int _x, int _y, Graphics g) {
        int[] position;
        int kiri_x;
        int kiri_y;
        for(int i = 0; i < background.size(); i++) {
            Image imej = (Image)background.elementAt(i);
            position = (int[]) offsets.elementAt(i);
            kiri_x = position[0] + _x;
            kiri_y = position[1] + _y;
           
            
//            g.drawImage(imej, kiri_x, kiri_y, 0);

            while(kiri_x <= ScreenManager.SCREEN_WIDTH) {
                g.drawImage(imej, kiri_x, kiri_y, 0);
                kiri_x += imej.getWidth();
            }
            
//            if(kiri_x > 0) {
//                kiri_x -= imej.getWidth();
//            }
//            if(kiri_x <= 0) {
//                int width = imej.getWidth() + kiri_x;
//                if(width > g.getClipWidth()) width = g.getClipWidth();
//                g.drawRegion(imej, -kiri_x, 0, width, imej.getHeight(), 0, 0, kiri_y, 0);
//                if(width < g.getClipWidth()) {
//                    g.drawRegion(imej, 0, 0, g.getClipWidth() - width, imej.getHeight(), 0, width, kiri_y, 0);// -, width, width, width);
//                }
//                g.drawRegion(imej, -kiri_x, 0, width, imej.getHeight(), 0, 0, kiri_y, 0);
//                if(width < g.getClipWidth()) {
//                    g.drawRegion(imej, 0, 0, g.getClipWidth() - width, 
//                            imej.getHeight(), 0, width, 0, 0);
//                }
//            } 
            
//            g.drawImage(imej, kiri_x, kiri_y, 0);
//            g.drawImage(imej, kiri_x + imej.getWidth(), kiri_y, 0);
        }
    }
}
