/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import com.mohanathas.spiffer.util.Point;
import java.util.List;
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
        assertEquals("#..\n.#.\n", g.serialize(null, null));
    }

    @Test
    public void testSerializeForStartAndGoalPoints() {
        final Graph g = new Graph(3, 2);
        final Point startPoint = new Point(2, 1);
        final Point goalPoint = new Point(1, 0);
        assertEquals(".G.\n..S\n", g.serialize(startPoint, goalPoint));
    }

    @Test
    public void testDeserialize() {
        final Graph g = new Graph(3, 2);
        g.deserialize("#..\n.#.\n", null, null);
        assertTrue(g.isWall(new Point(0, 0)));
        assertTrue(!g.isWall(new Point(1, 0)));
        assertTrue(g.isWall(new Point(1, 1)));
    }

    @Test
    public void testDeserializeForStartAndGoalPoints() {
        final Graph g = new Graph(3, 2);
        final Point startPoint = new Point(0, 0);
        final Point goalPoint = new Point(0, 0);
        g.deserialize("#G.\n.#S\n", null, goalPoint);
        g.deserialize("#G.\n.#S\n", startPoint, null);
        assertEquals(new Point(2, 1), startPoint);
        assertEquals(new Point(1, 0), goalPoint);
    }

    @Test
    public void testMalformedDeserialize() {
        final Graph g = new Graph(2, 2);
        g.deserialize("#.\n.####\n####\n", null, null);
        assertTrue(!g.isWall(new Point(2, 2)));
    }

    @Test
    public void testResize() {
        final Graph g = new Graph(0, 0);
        assertEquals(0, g.getWidth());
        assertEquals(0, g.getHeight());
        g.resize(5, 7);
        assertEquals(5, g.getWidth());
        assertEquals(7, g.getHeight());
        g.resize(5, 8);
        assertNotNull(g.getNode(4, 7));
        g.resize(6, 8);
        assertNotNull(g.getNode(5, 7));
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

        g.setWall(new Point(1, 1), true);
        assertFalse(g.isWall(new Point(1, 1)));
        assertFalse(g.isVisited(new Point(1, 1)));
    }

    @Test
    public void testFindNodeNeighbors() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 1, 0},
            {0, 1, 1},
            {1, 0, 1}});
        final List<Node> neigbors = g.findNodeNeighbors(g.getNode(1, 1));
        assertEquals(4, neigbors.size());
        assertTrue(neigbors.contains(g.getNode(0, 0)));
        assertTrue(neigbors.contains(g.getNode(1, 0)));
        assertTrue(neigbors.contains(g.getNode(2, 1)));
        assertTrue(neigbors.contains(g.getNode(2, 2)));
    }

    @Test
    public void testCreateFromIntArray() {
        assertNull(Graph.createFromIntArray(null));
        assertNull(Graph.createFromIntArray(new int[][] {}));
        assertNull(Graph.createFromIntArray(new int[][] {{}}));
        assertNull(Graph.createFromIntArray(new int[][] {{1}, {1, 2}}));
    }
}
