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
        final Node node = new Node(1, 2);
        assertEquals(1, node.getX());
        assertEquals(2, node.getY());
        assertEquals(0, node.getDistance());
    }

    @Test
    public void testToString() {
        final Node node = new Node(1, 2);
        assertEquals("(1, 2)", node.toString());
    }
}
