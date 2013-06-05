// -----------------------------------------------------------------------------
// Tweener.java
//
// Mistral Works Game Studio
// Copyright (c) Mistral Works Corporation. All rights reserved.
// -----------------------------------------------------------------------------
package com.mistralworks.helper;

/**
 *
 * @author Andri Ihsannudin
 */
public final class Tweener {

    private Tweener() {
    }

    public static int expInterpolation(final int base, final int target, final int exp) {
        if (base == target) {
            return 0;
        } else if (Math.abs(base - target) < exp) {
            if (base < target) {
                return +1;
            } else if (base > target) {
                return -1;
            }
        }
        return (target - base) / exp;
    }
}
