/*
 * Copyright (C) 2014 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Tests the List class.
 */
public class DynamicArrayTest {
    private DynamicArray<Integer> mList;

    public DynamicArrayTest() {
    }

    @Before
    public void setUp() {
        mList = new DynamicArray<>();
        for (int i = 1; i <= 20; ++i) {
            mList.add(i);
        }
    }

    @Test
    public void testGet() {
        assertEquals(new Integer(1), mList.get(0));
        assertEquals(new Integer(10), mList.get(9));
        assertEquals(new Integer(20), mList.get(19));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(mList.isEmpty());
        assertTrue(new DynamicArray<Integer>().isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(20, mList.size());
    }

    @Test
    public void testRemove() {
        assertEquals(new Integer(1), mList.remove(0));
        assertEquals(new Integer(2), mList.remove(0));
    }

    @Test
    public void testIterator() {
        Iterator<Integer> it = mList.iterator();
        for (int i = 1; i <= 20; ++i) {
            assertTrue(it.hasNext());
            assertEquals(new Integer(i), it.next());
        }
        assertFalse(it.hasNext());
    }
}
