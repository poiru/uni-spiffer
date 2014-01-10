/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Point class.
 */
public class PointTest {
    public PointTest() {
    }

    @Test
    public void testSet() {
        final Point point = new Point(0, 0);
        point.set(1, 2);
        assertEquals(new Point(1, 2), point);
    }

    @Test
    public void testDistanceTo() {
        assertEquals(2.0f, new Point(0, 0).distanceTo(new Point(0, 2)), 0.01f);
        assertEquals((float)Math.sqrt(2.0f), new Point(0, 0).distanceTo(new Point(-1, -1)), 0.01f);
    }

    @Test
    public void testEquals() {
        final Point point = new Point(1, 2);
        assertTrue(new Point(1, 2).equals(new Point(1, 2)));
        assertTrue(point.equals(point));
        assertFalse(point.equals(new Point(1, 0)));
        assertFalse(point.equals(new Point(0, 2)));
        assertFalse(point.equals(new Integer(1)));
        assertFalse(point.equals(null));
    }
}
