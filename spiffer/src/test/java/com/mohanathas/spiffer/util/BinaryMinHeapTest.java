/*
 * Copyright (C) 2014 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Tests the BinaryMinHeap class.
 */
public class BinaryMinHeapTest {
    private BinaryMinHeap<Integer> mHeap;

    public BinaryMinHeapTest() {
    }

    @Before
    public void setUp() {
        mHeap = new BinaryMinHeap<>();
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeekOnEmptyHeap() {
        mHeap.peek();
    }

    @Test
    public void testPopOrder() {
        mHeap.add(3);
        mHeap.add(8);
        mHeap.add(2);
        mHeap.add(6);
        mHeap.add(4);
        mHeap.add(5);
        mHeap.add(1);
        mHeap.add(9);
        mHeap.add(7);
        mHeap.add(0);
        assertEquals(new Integer(0), mHeap.pop());
        assertEquals(new Integer(1), mHeap.pop());
        assertEquals(new Integer(2), mHeap.pop());
        assertEquals(new Integer(3), mHeap.pop());
    }

    @Test
    public void testToString() {
        mHeap.add(1);
        mHeap.add(2);
        assertTrue(mHeap.toString().startsWith("[1, 2"));
    }
}
