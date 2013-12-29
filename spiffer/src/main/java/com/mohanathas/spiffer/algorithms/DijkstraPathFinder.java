/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

/**
 * PathFinder implementation of Dijkstra's algorithm. This simply uses the A* algorithm
 * implementation with a constant heuristic distance of 0.
 */
public class DijkstraPathFinder extends AStarPathFinder {
    public DijkstraPathFinder() {
        super(Heuristic.Zero);
    }
}
