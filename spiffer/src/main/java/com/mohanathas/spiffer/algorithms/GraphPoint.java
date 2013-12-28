/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

/**
 * Represents a location in (x, y) coordinate space.
 */
public class GraphPoint {
    int mX;
    int mY;

    public GraphPoint(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof GraphPoint)) return false;
        GraphPoint other = (GraphPoint)obj;
        return mX == other.mX && mY == other.mY;
    }
}
