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
 * Tests the Node class.
 */
public class NodeTest {
    public NodeTest() {
    }

    @Test
    public void testConstructor() {
        Node node = new Node(1, 2, 3.0);
        assertEquals(1, node.getX());
        assertEquals(2, node.getY());
        assertEquals(3.0, node.getWeight(), 0.001);
    }

    @Test
    public void testAddNeighbours() {
        Node node1 = new Node(1, 1, 1.0);
        Node node2 = new Node(2, 2, 3.0);
        Node node3 = new Node(3, 2, 3.0);

        assertEquals(0, node1.getNeighbours().size());
        node1.addNeighbour(node2);
        node1.addNeighbour(node3);
        assertEquals(2, node1.getNeighbours().size());

        assertEquals(node2, node1.getNeighbours().get(0));
        assertEquals(node3, node1.getNeighbours().get(1));
    }
}
