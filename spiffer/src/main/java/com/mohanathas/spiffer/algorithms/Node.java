/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in a graph.
 */
public class Node {
    private int mX = 0;
    private int mY = 0;
    private double mWeight = 0.0;
    private final ArrayList<Node> mNeighbours = new ArrayList<>();

    public Node(int x, int y, double weight) {
        mX = x;
        mY = y;
        mWeight = weight;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public double getWeight() {
        return mWeight;
    }

    public List<Node> getNeighbours() {
        return mNeighbours;
    }

    public void addNeighbour(Node neighbour) {
        mNeighbours.add(neighbour);
    }
}
