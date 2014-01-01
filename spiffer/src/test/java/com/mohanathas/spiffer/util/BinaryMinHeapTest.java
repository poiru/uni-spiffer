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
        mHeap.add(2);
        assertEquals(new Integer(2), mHeap.peek());
        mHeap.add(3);
        assertEquals(new Integer(2), mHeap.peek());
        mHeap.add(0);
        assertEquals(new Integer(0), mHeap.peek());
        mHeap.add(1);

        assertEquals(new Integer(0), mHeap.pop());
        assertEquals(new Integer(1), mHeap.pop());
        assertEquals(new Integer(2), mHeap.pop());
        assertEquals(new Integer(3), mHeap.pop());
    }
}
