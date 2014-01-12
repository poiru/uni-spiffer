/*
 * Copyright (C) 2014 Birunthan Mohanathas
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
 * Tests the JumpPointPathFinder class.
 */
public class JumpPointPathFinderTest extends PathFinderTestBase {
    public JumpPointPathFinderTest() {
        super(new JumpPointPathFinder(Heuristic.Chebyshev));
    }

    @Test
    public void testProcessedCount() {
        final Graph g = Graph.createFromIntArray(new int[][] {
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1}});
        assertNotNull(g.findPath(mPathFinder, new Point(0, 0), new Point(10, 4)));
        assertProcessedCountEquals(15, g);
        assertNotNull(g.findPath(mPathFinder, new Point(0, 2), new Point(10, 2)));
        assertProcessedCountEquals(25, g);
    }
}
