/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import com.mohanathas.spiffer.util.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Graph class.
 */
public class GraphTest {
    public GraphTest() {
    }

    @Test
    public void testSerialize() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {0, 1, 1},
            {1, 0, 1}});
        assertEquals("#..\n.#.\n", g.serialize());
    }

    @Test
    public void testDeserialize() {
        final Graph g = new Graph(3, 2);
        g.deserialize("#..\n.#.\n");
        assertTrue(g.isWall(new Point(0, 0)));
        assertTrue(g.isWall(new Point(0, 0)));
        assertTrue(g.isWall(new Point(1, 1)));
    }

    @Test
    public void testSize() {
        final Graph g = new Graph(0, 0);
        assertEquals(0, g.getWidth());
        assertEquals(0, g.getHeight());
        g.resize(5, 7);
        assertEquals(5, g.getWidth());
        assertEquals(7, g.getHeight());
    }

    @Test
    public void testSetWall() {
        final Graph g = new Graph(2, 2);
        final Point point = new Point(1, 1);
        assertFalse(g.isWall(point));
        g.setWall(point, true);
        assertTrue(g.isWall(point));
    }

    @Test
    public void testClearWalls() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {0, 0},
            {0, 0}});
        g.clearWalls();
        assertFalse(g.isWall(new Point(0, 0)));
        assertFalse(g.isWall(new Point(1, 1)));
    }

    @Test
    public void testGetNodeWithinBounds() {
        final Graph g = new Graph(4, 2);
        assertNotNull(g.getNode(0, 0));
        assertNotNull(g.getNode(1, 0));
        assertNotNull(g.getNode(0, 1));
        assertNotNull(g.getNode(2, 1));
    }

    @Test
    public void testGetNodeOutOfBounds() {
        final Graph g = new Graph(1, 1);
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
