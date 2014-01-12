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
import java.util.List;
import java.util.ListIterator;

/**
 * Implements a dynamic array (similar in operation to java.util.ArrayList<T>).
 *
 * @param <T> Type of element held in this array.
 */
public class DynamicArray<T> implements List<T> {
    private T[] mElements;
    private int mSize = 0;

    public DynamicArray(int initialCapacity) {
        mElements = (T[])new Object[initialCapacity];
    }

    public DynamicArray() {
        this(8);
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     *
     * @param o Element whose presence in this list is to be tested.
     * @return <tt>true</tt> if this list contains the specified element.
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < mSize; ++i) {
            if (mElements[i].equals(o)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element Element to be appended to this list.
     * @return <tt>true</tt>.
     */
    @Override
    public boolean add(T element) {
        ensureCapacity();
        mElements[mSize] = element;
        ++mSize;
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index Index of the element to return.
     * @return The element at the specified position in this list.
     */
    @Override
    public T get(int index) {
        return mElements[index];
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index The index of the element to be removed.
     * @return The element previously at the specified position.
     */
    @Override
    public T remove(int index) {
        final T t = mElements[index];
        --mSize;
        for (int i = index; i < mSize; ++i) {
            mElements[i] = mElements[i + 1];
        }
        mElements[mSize] = null;
        return t;
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index Index of the element to replace.
     * @param element Element to be stored at the specified position.
     * @return The element previously at the specified position.
     */
    @Override
    public T set(int index, T element) {
        final T prevElement = mElements[index];
        mElements[index] = element;
        return prevElement;
    }

    /**
     * Reverses the elements of this list.
     */
    public void reverse() {
        for (int i = 0; i < mSize / 2; ++i){
            final T temp = mElements[i];
            mElements[i] = mElements[mSize - i - 1];
            mElements[mSize - i - 1] = temp;
        }
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
     * Returns the number of elements in this list.
     *
     * @return The number of elements in this list.
     */
    @Override
    public int size() {
        return mSize;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return An iterator over the elements in this list in proper sequence.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int mPosition = 0;

            @Override
            public boolean hasNext() {
                return mPosition < mSize;
            }

            @Override
            public T next() {
                final T t = (T)mElements[mPosition];
                ++mPosition;
                return t;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void ensureCapacity() {
        if (mSize < mElements.length) return;

         mElements = Arrays.copyOf(mElements, mElements.length * 2);
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
    public boolean addAll(int index, Collection<? extends T> c) {
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

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
