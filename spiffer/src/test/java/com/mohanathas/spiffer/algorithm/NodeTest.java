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
 * Tests the Node class.
 */
public class NodeTest {
    public NodeTest() {
    }

    @Test
    public void testConstructor() {
        final Node node = new Node(1, 2);
        assertEquals(1, node.getX());
        assertEquals(2, node.getY());
        assertEquals(new Point(1, 2), node.getPoint());
        assertEquals(Float.MAX_VALUE, node.getStartDistance(), 0.001f);
        assertEquals(0.0f, node.getGoalDistance(), 0.001f);
    }

    @Test
    public void testCompareTo() {
        final Node node1 = new Node(0, 0);
        final Node node2 = new Node(0, 0);
        node1.setStartDistance(1.0f);
        node1.setGoalDistance(2.0f);
        node2.setStartDistance(2.0f);
        node2.setGoalDistance(1.0f);
        assertEquals(0, node1.compareTo(node2));

        node2.setGoalDistance(3.0f);
        assertEquals(-1, node1.compareTo(node2));
    }

    @Test
    public void testToString() {
        final Node node = new Node(1, 2);
        assertEquals("(1, 2)", node.toString());
    }
}
