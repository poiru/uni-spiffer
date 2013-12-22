/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import com.mohanathas.spiffer.util.Point;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class DijkstraPathFinderTest {

    public DijkstraPathFinderTest() {
    }

    private void assertPathSizeEquals(int expected, Graph g, int x1, int y1, int x2, int y2) {
        final List<Point> path = g.findPath(new DijkstraPathFinder(), new Point(x1, y1), new Point(x2, y2));
        if (expected == 0) {
            assertNull(path);
        } else {
            assertNotNull(path);
            assertEquals(expected, path.size());
        }
    }

    @Test
    public void testDirections() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}});
        assertPathSizeEquals(1, g, 1, 1, 0, 1);
        assertPathSizeEquals(1, g, 1, 1, 1, 0);
        assertPathSizeEquals(1, g, 1, 1, 2, 1);
        assertPathSizeEquals(1, g, 1, 1, 1, 2);
    }

    @Test
    public void testValidPaths() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}});
        assertPathSizeEquals(3, g, 0, 0, 3, 0);
        assertPathSizeEquals(5, g, 0, 0, 3, 2);
    }

    @Test
    public void testPath() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 0, 1},
            {1, 1, 1}});
        final List<Point> path = g.findPath(new DijkstraPathFinder(), new Point(0, 0), new Point(2, 0));
        assertEquals(path.get(0), new Point(0, 1));
        assertEquals(path.get(1), new Point(1, 1));
        assertEquals(path.get(2), new Point(2, 1));
    }

    @Test
    public void testValidPathsWithWalls() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 0, 1, 1, 1},
            {1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1}});
        assertPathSizeEquals(12, g, 0, 0, 2, 0);
        assertPathSizeEquals(7, g, 0, 0, 3, 2);
        assertPathSizeEquals(9, g, 0, 0, 4, 3);
    }

    @Test
    public void testInvalidPaths() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 0, 1},
            {1, 0, 0},
            {1, 1, 1}});
        assertPathSizeEquals(0, g, 0, 0, 2, 0);
        assertPathSizeEquals(0, g, 1, 2, 2, 0);
    }
}
