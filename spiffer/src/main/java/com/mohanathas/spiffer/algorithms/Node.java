/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import com.mohanathas.spiffer.util.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a node in a graph.
 */
class Node implements Comparable<Node> {
    private final Point mPoint;
    private boolean mWall = false;

    /**
     * Distance to the start and end nodes from the current node.
     */
    private double mStartDistance = Double.MAX_VALUE;
    private double mGoalDistance = 0.0;

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

    public double getStartDistance() {
        return mStartDistance;
    }

    public void setStartDistance(double distance) {
        mStartDistance = distance;
    }

    public double getGoalDistance() {
        return mGoalDistance;
    }

    public void setGoalDistance(double distance) {
        mGoalDistance = distance;
    }

    public Node getParent() {
        return mParent;
    }

    public void setParent(Node parent) {
        mParent = parent;
    }

    public boolean isWall() {
        return mWall;
    }

    public void setWall(boolean wall) {
        mWall = wall;
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
        mStartDistance = Double.MAX_VALUE;
        mGoalDistance = 0.0;
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
