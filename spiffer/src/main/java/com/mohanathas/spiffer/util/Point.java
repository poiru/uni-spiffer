/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

/**
 * Represents a location in (x, y) coordinate space.
 */
public class Point {
    private final int mX;
    private final int mY;

    public Point(int x, int y) {
        mX = x;
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    /**
     * Calculates the distance between this point and the specified point.
     *
     * @param other Point to compare to.
     * @return Distance between the points.
     */
    public float distanceTo(Point other) {
        final int dx = mX - other.getX();
        final int dy = mY - other.getY();
        return (float)Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Point)) return false;
        Point other = (Point)obj;
        return mX == other.mX && mY == other.mY;
    }
}
