/*
 * Copyright (C) 2014 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Implements a complete binary tree such that any given element is less than or equal to its
 * child elements.
 *
 * @param <T> Type of element held in this heap.
 */
public class BinaryMinHeap<T extends Comparable<T>> {
    private T[] mElements = (T[])new Comparable[8];
    private int mSize = 0;

    public BinaryMinHeap() {
    }

    @Override
    public String toString() {
        return Arrays.toString(mElements);
    }

    /**
     * Checks if heap is empty.
     *
     * @return True if heap is empty.
     */
    public boolean isEmpty() {
        return mSize == 0;
    }

    /**
     * Adds an element into heap.
     *
     * @param t Element to add.
     */
    public void add(T t) {
        ensureCapacity();

        ++mSize;
        int i = mSize - 1;
        while (i > 0 && mElements[parent(i)].compareTo(t) > 0) {
            mElements[i] = mElements[parent(i)];
            i = parent(i);
        }
        mElements[i] = t;
    }

    /**
     * Returns to topmost heap element without removing it.
     *
     * @return Topmost heap element.
     */
    public T peek() {
        if (!isEmpty()) {
            return mElements[0];
        }

        throw new NoSuchElementException();
    }

    /**
     * Returns the topmost heap element and removes is.
     *
     * @return Topmost heap element.
     */
    public T pop() {
        final T max = peek();
        mElements[0] = mElements[mSize - 1];
        mSize -= 1;
        heapify(0);
        return max;
    }

    /**
     * Arranges element i and its subtrees to satisfy the heap property.
     *
     * @param i Element to arrange.
     */
    private void heapify(int i) {
        final int left = leftChild(i);
        final int right = rightChild(i);
        if (right < mSize) {
            final int smaller = (mElements[left].compareTo(mElements[right]) < 0) ? left : right;
            if (mElements[i].compareTo(mElements[smaller]) > 0) {
                swap(i, smaller);
                heapify(smaller);
            }
        } else if (left == mSize - 1 && mElements[i].compareTo(mElements[left]) > 0) {
            swap(i, left);
        }
    }

    private void swap(int i, int j) {
        final T temp = mElements[i];
        mElements[i] = mElements[j];
        mElements[j] = temp;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return 2 * Math.max(i, 1) - 1;
    }

    private int rightChild(int i) {
        return 2 * Math.max(i, 1);
    }

    private void ensureCapacity() {
        if (mSize < mElements.length) return;

        mElements = Arrays.copyOf(mElements, mElements.length * 2);
    }
}
