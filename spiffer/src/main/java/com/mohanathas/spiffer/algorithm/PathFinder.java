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

/**
 * Abstracts the path finding algorithm used by the Graph class.
 */
public abstract interface PathFinder {
    /**
     * Finds a list of Points connecting the Nodes at |startPos| and |endPos|.
     *
     * @param graph Graph to search for the path.
     * @param startNode Node to start the search from.
     * @param goalNode Node that is to be reached from |startNode|.
     * @return List of Points if a path was found or |null| otherwise.
     */
    List<Point> findPath(Graph graph, Node startNode, Node goalNode);
}
