// -----------------------------------------------------------------------------
// Input.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------

package com.mistralworks.engine;

/**
  * Harusnya dijadiin struct ._.
  * @author Andri Ihsannudin
 */
public class Input {
        public int keyState;
        public int touchX;
        public int touchY;
        public boolean touchPressed;
        public boolean keyPressed;
        
        public int raw_pressed_key;
        public int raw_down_key;
        public int raw_released_key;
}
