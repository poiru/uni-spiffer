/*
 * Copyright (C) 2014 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Implements a complete binary tree such that any given element is less than or equal to its
 * child elements (similar in operation to java.util.PriorityQueue<T>).
 *
 * @param <T> Type of element held in this heap.
 */
public class BinaryMinHeap<T extends Comparable<T>> implements Queue<T> {
    private T[] mElements = (T[])new Comparable[8];
    private int mSize = 0;

    public BinaryMinHeap() {
    }

    @Override
    public String toString() {
        return Arrays.toString(mElements);
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return mSize == 0;
    }

    /**
     * Inserts the specified element into this heap.
     *
     * @param element Element to be appended to this list.
     * @return <tt>true</tt>.
     */
    @Override
    public boolean add(T element) {
        ensureCapacity();

        ++mSize;
        int i = mSize - 1;
        while (i > 0 && mElements[parent(i)].compareTo(element) > 0) {
            mElements[i] = mElements[parent(i)];
            i = parent(i);
        }
        mElements[i] = element;
        return true;
    }

    /**
     * Reorders an existing element in this heap.
     *
     * @param t Previously added element that is to be reordered.
     */
    public void reorder(T t) {
        int i;
        for (i = 0; i < mSize; ++i) {
            if (mElements[i] == t) {
                break;
            }
        }

        if (i >= mSize) {
            throw new NoSuchElementException();
        }

        while (i > 0 && mElements[parent(i)].compareTo(t) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Retrieves, but does not remove, the topmost (smallest) element of this heap.
     *
     * @return Topmost heap element, or <tt>null</tt> if this heap is empty.
     */
    @Override
    public T peek() {
        return isEmpty() ? null : mElements[0];
    }

    /**
     * Retrieves and removes the topmost (smallest) element of this heap.
     *
     * @return Topmost heap element, or <tt>null</tt> if this heap is empty.
     */
    @Override
    public T poll() {
        final T t = peek();
        if (t != null) {
            mElements[0] = mElements[mSize - 1];
            mSize -= 1;
            heapify(0);
        }
        return t;
    }

    /**
     * Arranges the specified element and its subtrees to satisfy the heap property.
     *
     * @param i Index of element to arrange.
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
        return i == 0 ? 1 : 2 * i;
    }

    private int rightChild(int i) {
        return i == 0 ? 2 : 2 * i + 1;
    }

    private void ensureCapacity() {
        if (mSize < mElements.length) return;

        mElements = Arrays.copyOf(mElements, mElements.length * 2);
    }

    @Override
    public boolean offer(T e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T element() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
