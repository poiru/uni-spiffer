/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the GraphPoint class.
 */
public class GraphPointTest {
    public GraphPointTest() {
    }

    @Test
    public void testEquals() {
        final GraphPoint point = new GraphPoint(1, 2);
        assertTrue(new GraphPoint(1, 2).equals(new GraphPoint(1, 2)));
        assertTrue(point.equals(point));
        assertFalse(point.equals(new GraphPoint(1, 0)));
        assertFalse(point.equals(new GraphPoint(0, 2)));
        assertFalse(point.equals(new Integer(1)));
        assertFalse(point.equals(null));
    }
}
