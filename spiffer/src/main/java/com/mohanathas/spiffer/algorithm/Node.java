/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import com.mohanathas.spiffer.util.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a node in a graph.
 */
class Node implements Comparable<Node> {
    private final Point mPoint;
    private boolean mWalkable = true;

    /**
     * Distance to the start and end nodes from the current node.
     */
    private float mStartDistance = Float.MAX_VALUE;
    private float mGoalDistance = 0.0f;

    private Node mParent = null;
    private boolean mVisited = false;

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

    public Node getParent() {
        return mParent;
    }

    public void setParent(Node parent) {
        mParent = parent;
    }

    public boolean isWalkable() {
        return mWalkable;
    }

    public void setWalkable(boolean walkable) {
        mWalkable = walkable;
    }

    public boolean isVisited() {
        return mVisited;
    }

    public void markVisited() {
        mVisited = true;
    }

    /**
     * Resets this fields of this instance to their initial state.
     */
    public void reset() {
        mStartDistance = Float.MAX_VALUE;
        mGoalDistance = 0.0f;
        mVisited = false;
        mParent = null;
    }

    /**
     * @return A list of parents excluding the furthest parent in reverse order.
     */
    public List<Point> getParentPoints() {
        final List<Point> list = new ArrayList<>();
        Node node = this;
        while (node != null) {
            list.add(node.mPoint);
            node = node.getParent();
        }

        // Remove the start node.
        list.remove(list.size() - 1);

        Collections.reverse(list);
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
