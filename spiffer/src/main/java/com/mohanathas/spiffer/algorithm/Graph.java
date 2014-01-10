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
import java.util.List;

/**
 * Container of Nodes for every combination of X and Y within the bounds of the
 * graph.
 */
public final class Graph {
    private Node[][] mGrid = new Node[1][1];
    private boolean mDirty = false;

    public Graph(int width, int height) {
        resize(width, height);
    }

    public int getHeight() {
        return mGrid.length;
    }

    public int getWidth() {
        return mGrid.length > 0 ? mGrid[0].length : 0;
    }

    /**
     * Gets the Node on the given position.
     *
     * @return A Node object if the position is valid or |null| otherwise.
     */
    Node getNode(int x, int y) {
        if (y >= 0 && y < mGrid.length && x >= 0 && x < mGrid[y].length) {
            return mGrid[y][x];
        }
        return null;
    }

    Node getNode(Point point) {
        return getNode(point.getX(), point.getY());
    }

    /**
     * Checks if a point has been visited (by a call to |findPath|).
     *
     * @param point Point to check.
     * @return True if the point has been visited.
     */
    public boolean isVisited(Point point) {
        final Node node = getNode(point);
        return node != null ? node.isVisited() : false;
    }

    /**
     * Checks if a point is a wall.
     *
     * @param point Point to check.
     * @return True if the point is a wall.
     */
    public boolean isWall(Point point) {
        final Node node = getNode(point);
        return node != null ? node.isWall() : false;
    }

    /**
     * Sets or unsets a point as a wall.
     *
     * @param point Point to set.
     * @param wall New wall state of the point.
     */
    public void setWall(Point point, boolean wall) {
        final Node node = getNode(point);
        if (node != null) {
            node.setWall(wall);
        }
    }

    /**
     * Finds a list of Points connecting the nodes at |startPos| and |endPos|.
     *
     * @param finder A PathFinder instance used for the search.
     * @param startPoint Start position.
     * @param endPoint End point.
     * @return List of Points if a path was found or |null| otherwise.
     */
    public List<Point> findPath(PathFinder finder, Point startPoint, Point endPoint) {
        if (mDirty) {
            // We need to clear e.g. the visited flag of the nodes.
            reset();
        }

        final List<Point> path = finder.findPath(this, getNode(startPoint), getNode(endPoint));
        mDirty = true;
        return path;
    }

    /**
     * Calculates the total length of a solution path.
     *
     * @param startPoint Start point.
     * @param points List of points to take from startPoint in order to reach goal.
     * @return Total distance of path.
     */
    public static float calculatePathLength(Point startPoint, List<Point> points) {
        float length = 0.0f;
        Point prevPoint = startPoint;
        for (Point point : points) {
            length += point.distanceTo(prevPoint);
            prevPoint = point;
        }

        return length;
    }

    /**
     * Finds the non-wall neighbors of the specified node.
     *
     * @param node Node to get the neighbors of.
     * @return List of neighbor nodes.
     */
    List<Node> findNodeNeighbors(Node node) {
        final List<Node> neighbors = new ArrayList<>();
        for (int y = -1; y <= 1; ++y) {
            for (int x = -1; x <= 1; ++x) {
                if (y == 0 && x == 0) {
                    continue;
                }

                final Node neighbor = getNode(node.getX() + x, node.getY() + y);
                if (neighbor == null || neighbor.isWall()) {
                    continue;
                }

                // Skip diagonal neighbors if both shared neighbors between the node and the
                // neighbor are walls.
                final boolean diagonal = x != 0 && y != 0;
                if (diagonal &&
                    getNode(node.getX(), node.getY() + y).isWall() &&
                    getNode(node.getX() + x, node.getY()).isWall()) {
                    continue;
                }

                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    /**
     * Resets the nodes in the grid if needed.
     */
    public void reset() {
        if (mDirty) {
            for (int y = 0; y < mGrid.length; ++y) {
                for (int x = 0; x < mGrid[y].length; ++x) {
                    mGrid[y][x].reset();
                }
            }

            mDirty = false;
        }
    }

    /**
     * Marks all nodes as normal nodes.
     */
    public void clearWalls() {
        for (int y = 0; y < mGrid.length; ++y) {
            for (int x = 0; x < mGrid[y].length; ++x) {
                mGrid[y][x].setWall(false);
            }
        }
    }

    /**
     * Resize the graph while keeping the Nodes within the new bounds intact.
     *
     * @param width New width.
     * @param height New height.
     */
    public void resize(int width, int height) {
        if (width == getWidth() && height == getHeight()) return;

        final Node[][] newGrid = new Node[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                newGrid[y][x] = getNode(x, y);
                if (newGrid[y][x] == null) {
                    newGrid[y][x] = new Node(x, y);
                }
            }
        }
        mGrid = newGrid;
    }

    /**
     * Serializes the graph state to a string.
     *
     * @param startPoint Optional start point to include in the serialized string.
     * @param goalPoint Optional end point to include in the serialized string.
     * @return Serialized representation of the graph.
     */
    public String serialize(Point startPoint, Point goalPoint) {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < mGrid.length; ++y) {
            for (int x = 0; x < mGrid[y].length; ++x) {
                char ch = '.';
                if (startPoint != null && x == startPoint.getX() && y == startPoint.getY()) {
                    ch = 'S';
                } else if (goalPoint != null && x == goalPoint.getX() && y == goalPoint.getY()) {
                    ch = 'G';
                } else if (mGrid[y][x].isWall()) {
                    ch = '#';
                }
                sb.append(ch);
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Updates the state of the graph to match the state of the given serialized data.
     *
     * @param data Serialized data from a previous call to serialize().
     * @param startPoint Optional point whose position is set to match the start point in the data.
     * @param goalPoint Optional point whose position is set to match the goal point in the data.
     */
    public void deserialize(String data, Point startPoint, Point goalPoint) {
        int x = 0;
        int y = 0;
        for (char ch : data.toCharArray()) {
            if (ch == '\n') {
                x = 0;
                ++y;
                continue;
            }

            if (y < mGrid.length && x < mGrid[0].length) {
                if (ch == 'S' && startPoint != null) {
                    startPoint.set(x, y);
                } else if (ch == 'G' && goalPoint != null) {
                    goalPoint.set(x, y);
                } else if (ch == '#' || ch == '.') {
                    mGrid[y][x].setWall(ch == '#');
                }
            }

            ++x;
        }
    }

    /**
     * Helper method to create a Graph from a two dimensional array of ints. A
     * integer value of 0 is treated as a wall and all others as a normal node.
     *
     * @param ints Two dimensional int array representing the nodes.
     * @return A Graph based on the |ints| array.
     */
    public static Graph createFromIntArray(int[][] ints) {
        if (ints == null || ints.length == 0 || ints[0].length == 0) return null;

        final Graph graph = new Graph(ints[0].length, ints.length);
        for (int y = 0; y < ints.length; ++y) {
            if (ints[y].length != ints[0].length) return null;
            for (int x = 0; x < ints[y].length; ++x) {
                graph.mGrid[y][x] = new Node(x, y);
                if (ints[y][x] == 0) {
                    graph.mGrid[y][x].setWall(true);
                }
            }
        }
        return graph;
    }
}
