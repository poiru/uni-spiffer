/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

/**
 * Container of Nodes for every combination of X and Y within the bounds of the
 * grid.
 */
public class NodeGrid {
    private final Node[][] mGrid;

    public NodeGrid(int width, int height) {
        mGrid = new Node[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                mGrid[y][x] = new Node(x, y, 0.0);
            }
        }
    }

    /**
     * Gets the Node on the given position.
     *
     * @return A Node object if the position is valid or |null| otherwise.
     */
    public Node getNode(int x, int y) {
        if (y >= 0 && y < mGrid.length && x >= 0 && x < mGrid[y].length) {
            return mGrid[y][x];
        }
        return null;
    }
}
