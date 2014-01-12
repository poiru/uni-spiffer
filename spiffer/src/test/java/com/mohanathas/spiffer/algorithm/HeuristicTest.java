/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the various Heuristic distances.
 */
public class HeuristicTest {
    @Test
    public void testChebyshevHeuristic() {
        final Heuristic h = Heuristic.Chebyshev;
        assertEquals(0.0, h.distance(0, 0), 0.001);
        assertEquals(2.0, h.distance(2, 0), 0.001);
        assertEquals(2.0, h.distance(0, 2), 0.001);
        assertEquals(2.0, h.distance(2, 2), 0.001);
        assertEquals(20.0, h.distance(2, 20), 0.001);
    }

    @Test
    public void testEuclideanHeuristic() {
        final Heuristic h = Heuristic.Euclidean;
        assertEquals(0.0, h.distance(0, 0), 0.001);
        assertEquals(2.0, h.distance(2, 0), 0.001);
        assertEquals(2.0, h.distance(0, 2), 0.001);
        assertEquals(2.828, h.distance(2, 2), 0.001);
        assertEquals(20.099, h.distance(2, 20), 0.001);
    }

    @Test
    public void testManhattanHeuristic() {
        final Heuristic h = Heuristic.Manhattan;
        assertEquals(0.0, h.distance(0, 0), 0.001);
        assertEquals(2.0, h.distance(2, 0), 0.001);
        assertEquals(2.0, h.distance(0, 2), 0.001);
        assertEquals(4.0, h.distance(2, 2), 0.001);
        assertEquals(22.0, h.distance(2, 20), 0.001);
    }

    @Test
    public void testZeroHeuristic() {
        final Heuristic h = Heuristic.Zero;
        assertEquals(0.0, h.distance(2, 20), 0.001);
    }
}
