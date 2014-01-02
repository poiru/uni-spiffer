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
 * Tests the AStarPathFinder/DijkstraPathFinder classes.
 */
public class AStarPathFinderTest {
    public AStarPathFinderTest() {
    }

    private void assertPathLengthEquals(float expected, Graph g, int x1, int y1, int x2, int y2) {
        final Point start = new Point(x1, y1);
        final List<Point> path = g.findPath(new DijkstraPathFinder(), start, new Point(x2, y2));
        if (expected == 0.0f) {
            assertNull(path);
        } else {
            assertNotNull(path);
            assertEquals(expected, Graph.calculatePathLength(start, path), 0.01f);
        }
    }

    private void assertVisitedCountEquals(int expected, Graph g) {
        int visited = 0;
        for (int y = 0; y < g.getWidth(); ++y) {
            for (int x = 0; x < g.getWidth(); ++x) {
                if (g.isVisited(new Point(x, y))) {
                    ++visited;
                }
            }
        }
        assertEquals(expected, visited);
    }

    @Test
    public void testDirections() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}});
        assertPathLengthEquals(1.0f, g, 1, 1, 0, 1);
        assertPathLengthEquals(1.0f, g, 1, 1, 1, 0);
        assertPathLengthEquals(1.0f, g, 1, 1, 2, 1);
        assertPathLengthEquals(1.0f, g, 1, 1, 1, 2);
    }

    @Test
    public void testValidPaths() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}});
        assertPathLengthEquals(3.0f, g, 0, 0, 3, 0);
        assertPathLengthEquals(3.83f, g, 0, 0, 3, 2);
    }

    @Test
    public void testValidPathsWithWalls() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 0, 1, 1, 1},
            {1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1}});
        assertPathLengthEquals(9.66f, g, 0, 0, 2, 0);
        assertPathLengthEquals(5.83f, g, 0, 0, 3, 2);
        assertPathLengthEquals(7.24f, g, 0, 0, 4, 3);
    }

    @Test
    public void testInvalidPaths() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 0, 1},
            {1, 0, 0},
            {1, 1, 1}});
        assertPathLengthEquals(0.0f, g, 0, 0, 2, 0);
        assertPathLengthEquals(0.0f, g, 1, 2, 2, 0);
    }

    @Test
    public void testVisitedCount() {
        final Graph g = new Graph(5, 5);
        final Point start = new Point(0, 0);
        final Point end = new Point(4, 4);

        assertNotNull(g.findPath(new DijkstraPathFinder(), start, end));
        assertVisitedCountEquals(24, g);

        assertNotNull(g.findPath(new AStarPathFinder(Heuristic.Manhattan), start, end));
        assertVisitedCountEquals(4, g);
    }
}
