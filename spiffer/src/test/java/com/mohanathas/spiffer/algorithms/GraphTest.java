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
 * Tests the Graph class.
 */
public class GraphTest {
    public GraphTest() {
    }

    @Test
    public void testGetNodeWithinBounds() {
        Graph g = new Graph(4, 2);
        g.reset();
        assertNotNull(g.getNode(0, 0));
        assertNotNull(g.getNode(1, 0));
        assertNotNull(g.getNode(0, 1));
        assertNotNull(g.getNode(2, 1));
    }

    @Test
    public void testGetNodeOutOfBounds() {
        Graph g = new Graph(1, 1);
        g.reset();
        assertNull(g.getNode(-1, 0));
        assertNull(g.getNode(0, -1));
        assertNull(g.getNode(1, 0));
        assertNull(g.getNode(0, 1));
    }

    @Test
    public void testCreateFromIntArray() {
        assertNull(Graph.createFromIntArray(null));
        assertNull(Graph.createFromIntArray(new int[][] {}));
        assertNull(Graph.createFromIntArray(new int[][] {{}}));
        assertNull(Graph.createFromIntArray(new int[][] {{1}, {1, 2}}));
    }
}
