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
 * Tests the AStarPathFinder/DijkstraPathFinder classes.
 */
public class AStarPathFinderTest extends PathFinderTestBase {
    public AStarPathFinderTest() {
        super(new AStarPathFinder(Heuristic.Euclidean));
    }

    @Test
    public void testProcessedCount() {
        final Graph g = new Graph(5, 5);
        final Point start = new Point(0, 0);
        final Point end = new Point(4, 4);

        assertNotNull(g.findPath(new DijkstraPathFinder(), start, end));
        assertProcessedCountEquals(25, g);

        assertNotNull(g.findPath(new AStarPathFinder(Heuristic.Euclidean), start, end));
        assertProcessedCountEquals(5, g);
    }
}
