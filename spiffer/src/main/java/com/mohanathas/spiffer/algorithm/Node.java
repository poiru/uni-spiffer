/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import com.mohanathas.spiffer.util.DynamicArray;
import com.mohanathas.spiffer.util.Point;
import java.util.List;

/**
 * Represents a node in a graph.
 */
class Node implements Comparable<Node> {
    private final Point mPoint;
    private boolean mWalkable = true;
    private Node mParent = null;

    /** Distance to the start node from the current node. */
    private float mStartDistance = Float.MAX_VALUE;

    /** Distance to the goal node from the current node. */
    private float mGoalDistance = 0.0f;

    private boolean mProcessed = false;
    private boolean mQueued = false;

    public Node(int x, int y) {
        mPoint = new Point(x, y);
    }

    public int getX() {
        return mPoint.getX();
    }

    public int getY() {
        return mPoint.getY();
    }

    public Point getPoint() {
        return mPoint;
    }

    public boolean isWalkable() {
        return mWalkable;
    }

    public void setWalkable(boolean walkable) {
        mWalkable = walkable;
    }

    public Node getParent() {
        return mParent;
    }

    public void setParent(Node parent) {
        mParent = parent;
    }

    public float getStartDistance() {
        return mStartDistance;
    }

    public void setStartDistance(float distance) {
        mStartDistance = distance;
    }

    public float getGoalDistance() {
        return mGoalDistance;
    }

    public void setGoalDistance(float distance) {
        mGoalDistance = distance;
    }

    public boolean isQueued() {
        return mQueued;
    }

    public void setQueued() {
        mQueued = true;
    }

    public boolean isProcessed() {
        return mProcessed;
    }

    public void setProcessed() {
        mProcessed = true;
    }

    /**
     * Resets this fields of this instance to their initial state.
     */
    public void reset() {
        mParent = null;
        mStartDistance = Float.MAX_VALUE;
        mGoalDistance = 0.0f;
        mQueued = false;
        mProcessed = false;
    }

    /**
     * @return A list of parents excluding the furthest parent in reverse order.
     */
    public List<Point> getParentPoints() {
        final DynamicArray<Point> list = new DynamicArray<>();
        Node node = this;
        while (node != null) {
            list.add(node.mPoint);
            node = node.getParent();
        }

        // Remove the start node.
        list.remove(list.size() - 1);
        list.reverse();
        return list;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }

    @Override
    public int compareTo(Node that) {
        return Double.compare(
            mStartDistance + mGoalDistance, that.mStartDistance + that.mGoalDistance);
    }
}
