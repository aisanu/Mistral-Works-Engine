// -----------------------------------------------------------------------------
// TextureParser.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.helper;

import com.mistralworks.engine.SpriteFrame;
import com.mistralworks.engine.graphics.TextureAtlas;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.lcdui.Image;
import org.kxml.Xml;
import org.kxml.parser.ParseEvent;
import org.kxml.parser.XmlParser;

/**
 * Read data from Texture Packer's Generic XML Data File
 *
 * @author Andri Ihsannudin
 */
public final class TextureParser {

    private TextureParser() {
    }

    public static final TextureAtlas parseTexture(final String textureName) {
        try {
            TextureAtlas ret = null;

            Image image = Image.createImage(textureName);
            SpriteFrame defaultFrame = new SpriteFrame(0, 0, image.getWidth(), image.getHeight());
            ret = new TextureAtlas(image);
            ret.addFrame("default", defaultFrame);

            return ret;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final TextureAtlas parseTexture(final Class root, final String fileDirectory, final String fileName) {
        TextureAtlas atlas = null;
        try {
            System.out.println("Loading: " + fileDirectory + fileName);
            InputStream is = root.getResourceAsStream(fileDirectory + fileName);
            XmlParser parser = new XmlParser(new InputStreamReader(is));
            ParseEvent pe = parser.read(Xml.START_TAG, null, "TextureAtlas");
            System.out.println("Here <-");

            // get texture image
            String textureName = pe.getAttribute("imagePath").getValue();
            Image tes = Image.createImage(is.getClass().getResourceAsStream(fileDirectory + textureName));
            atlas = new TextureAtlas(tes);

            // get the frame
            System.out.println(pe.getName());
            while (pe.getType() != Xml.END_DOCUMENT) {
                pe = parser.read();
                if (pe.getType() == Xml.START_TAG) {
                    if (pe.getName().equals("sprite")) {
                        String name = pe.getAttribute("n").getValue();
                        System.out.println("Sprite Frame: " + name);
                        int x = Integer.parseInt(pe.getAttribute("x").getValue());
                        int y = Integer.parseInt(pe.getAttribute("y").getValue());
                        int w = Integer.parseInt(pe.getAttribute("w").getValue());
                        int h = Integer.parseInt(pe.getAttribute("h").getValue());
                        int ox = 0;
                        int oy = 0;
                        if (pe.getAttribute("oX") != null) {
                            ox = Integer.parseInt(pe.getAttribute("oX").getValue());
                        }
                        if (pe.getAttribute("oY") != null) {
                            oy = Integer.parseInt(pe.getAttribute("oY").getValue());
                        }
                        SpriteFrame frame = new SpriteFrame(x, y, w, h);
                        frame.setOrigin(ox, oy);
                        atlas.addFrame(name, frame);
                    } else if (pe.getName().equalsIgnoreCase("SubTexture")) {
                        String name = pe.getAttribute("name").getValue();
                        System.out.println("Sprite Frame: " + name);
                        int x = Integer.parseInt(pe.getAttribute("x").getValue());
                        int y = Integer.parseInt(pe.getAttribute("y").getValue());
                        int w = Integer.parseInt(pe.getAttribute("width").getValue());
                        int h = Integer.parseInt(pe.getAttribute("height").getValue());
                        int ox = 0;
                        int oy = 0;
                        if (pe.getAttribute("oX") != null) {
                            SpriteFrame frame = new SpriteFrame(x, y, w, h);
                            frame.setOrigin(ox, oy);
                            atlas.addFrame(name, frame);
                        }
                    }
                }
            }
            System.out.println("Here");

            return atlas;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
//    public void parse(InputStream in) throws IOException {
//        Reader reader = new InputStreamReader(in);
//        XmlParser parser = new XmlParser(reader);
//        ParseEvent pe = null;
//        parser.read(Xml.START_TAG, null, "login");
//        boolean trucking = true;
//        while (trucking) {
//            pe = parser.read();
//            if (pe.getType() == Xml.START_TAG) {
//                String name = pe.getName();
//                if (name.equals("status")) {
//                    while ((pe.getType() != Xml.END_TAG)
//                            || (pe.getName().equals(name) == false)) {
//                        pe = parser.read();
//                        if (pe.getType() == Xml.START_TAG
//                                && pe.getName().equals("message")) {
//                            pe = parser.read();
//                            message = pe.getText();
//                        }
//                    }
//                    mRSSListener.itemParsed(message);
//                } else {
//                    while ((pe.getType() != Xml.END_TAG)
//                            || (pe.getName().equals(name) == false)) {
//                        pe = parser.read();
//                    }
//                }
//            }
//            if (pe.getType() == Xml.END_TAG
//                    && pe.getName().equals("login")) {
//                trucking = false;
//            }
//        }
//    }
}
