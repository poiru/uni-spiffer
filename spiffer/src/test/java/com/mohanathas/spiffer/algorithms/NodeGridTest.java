/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the NodeGrid class.
 */
public class NodeGridTest {
    public NodeGridTest() {
    }

    @Test
    public void testGetNodeWithinBounds() {
        NodeGrid grid = new NodeGrid(4, 2);
        assertNotNull(grid.getNode(0, 0));
        assertNotNull(grid.getNode(1, 0));
        assertNotNull(grid.getNode(0, 1));
        assertNotNull(grid.getNode(2, 1));
    }

    @Test
    public void testGetNodeOutOfBounds() {
        NodeGrid grid = new NodeGrid(1, 1);
        assertNull(grid.getNode(-1, 0));
        assertNull(grid.getNode(0, -1));
        assertNull(grid.getNode(1, 0));
        assertNull(grid.getNode(0, 1));
    }
}
