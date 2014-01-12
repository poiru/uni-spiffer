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

    public void testPeekOnEmptyHeap() {
        assertNull(mHeap.peek());
    }

    private void assertPopOrder(int from, int to) {
        for (int i = from; i <= to; ++i) {
            assertEquals(new Integer(i), mHeap.poll());
        }
    }

    @Test
    public void testAscedingAddOrder() {
        for (int i = 0; i <= 100; ++i) {
            mHeap.add(i);
        }
        assertPopOrder(0, 100);
    }

    @Test
    public void testDescedingAddOrder() {
        for (int i = 100; i >= 0; --i) {
            mHeap.add(i);
        }
        assertPopOrder(0, 100);
    }

    @Test
    public void testBothAddOrder() {
        for (int i = 0; i <= 100; ++i) {
            mHeap.add(i % 2 == 0 ? i : 100 - i);
        }
        assertPopOrder(0, 100);
    }

    @Test
    public void testToString() {
        mHeap.add(1);
        mHeap.add(2);
        assertTrue(mHeap.toString().startsWith("[1, 2"));
    }
}
