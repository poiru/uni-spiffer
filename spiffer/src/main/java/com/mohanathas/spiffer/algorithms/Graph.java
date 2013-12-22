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

/**
 * Container of Nodes for every combination of X and Y within the bounds of the
 * graph.
 */
public class Graph {
    private final Node[][] mGrid;

    public Graph(int width, int height) {
        mGrid = new Node[height][width];
    }

    /**
     * Gets the Node on the given position.
     *
     * @return A Node object if the position is valid or |null| otherwise.
     */
    Node getNode(int x, int y) {
        if (y >= 0 && y < mGrid.length && x >= 0 && x < mGrid[y].length) {
            return mGrid[y][x];
        }
        return null;
    }

    Node getNode(Point point) {
        return getNode(point.getX(), point.getY());
    }

    /**
     * Finds a list of Points connecting the nodes at |startPos| and |endPos|.
     *
     * @param finder A PathFinder instance used for the search.
     * @param startPos Start position.
     * @param endPos End position.
     * @return List of Points if a path was found or |null| otherwise.
     */
    public List<Point> findPath(PathFinder finder, Point startPos, Point endPos) {
        // We need to clear e.g. the visited flag of the nodes.
        reset();

        List<Point> path = finder.findPath(this, getNode(startPos), getNode(endPos));
        return path;
    }

    /**
     * Creates or resets the nodes in the grid.
     */
    void reset() {
        for (int y = 0; y < mGrid.length; ++y) {
            for (int x = 0; x < mGrid[y].length; ++x) {
                if (mGrid[y][x] == null) {
                    mGrid[y][x] = new Node(x, y);
                } else {
                    mGrid[y][x].reset();
                }
            }
        }
    }

    /**
     * Helper method to create a Graph from a two dimensional array of ints. A
     * integer value of 0 is treated as a wall and all others as a normal node.
     * 
     * @param ints Two dimensional int array representing the nodes.
     * @return A Graph based on the |ints| array.
     */
    public static Graph createFromIntArray(int[][] ints) {
        if (ints == null || ints.length == 0 || ints[0].length == 0) return null;

        Graph graph = new Graph(ints[0].length, ints.length);
        for (int y = 0; y < ints.length; ++y) {
            if (ints[y].length != ints[0].length) return null;
            for (int x = 0; x < ints[y].length; ++x) {
                graph.mGrid[y][x] = new Node(x, y);
                if (ints[y][x] == 0) {
                    graph.mGrid[y][x].setWall(true);
                }
            }
        }
        return graph;
    }
}
