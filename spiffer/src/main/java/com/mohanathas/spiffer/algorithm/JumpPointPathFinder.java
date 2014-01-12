/*
 * Copyright (C) 2014 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import com.mohanathas.spiffer.util.BinaryMinHeap;
import com.mohanathas.spiffer.util.DynamicArray;
import java.util.List;

/**
 * PathFinder implementation of the Jump Point Search algorithm, which behaves similarly to A*
 * except that it avoids traversing "unnecessary" neighbors.
 *
 * Based on the PathFinding.js implementation by Nathan Witmer <nathan@zerowidth.com>.
 */
public class JumpPointPathFinder extends AStarPathFinder {
    public JumpPointPathFinder(Heuristic heuristic) {
        super(heuristic);
    }

    @Override
    void handleNeighbors(Graph graph, BinaryMinHeap<Node> heap, Node node, Node goalNode) {
        for (final Node neighbor : findPrunedNeighbors(graph, node)) {
            final Node jumpNode = findJumpNode(graph, neighbor, node, goalNode);
            if (jumpNode != null) {
                relax(heap, node, jumpNode, goalNode);
            }
        }
    }

    private static Node findJumpNode(Graph graph, Node node, Node parentNode, Node goalNode) {
        if (node == null || parentNode == null || !node.isWalkable()) {
            return null;
        }

        if (node.equals(goalNode)) {
            return node;
        }

        final int x = node.getX();
        final int y = node.getY();
        final int dx = x - parentNode.getX();
        final int dy = y - parentNode.getY();

        if (dx != 0 && dy != 0) {
            // Check for diagonal forced neighbors.
            if ((graph.isWalkable(x - dx, y + dy) && !graph.isWalkable(x - dx, y)) ||
                (graph.isWalkable(x + dx, y - dy) && !graph.isWalkable(x, y - dy))) {
                return node;
            }

            // Additional check for horizontal and vertical jump points with diagonal movements.
            final Node xJumpNode = findJumpNode(graph, graph.getNode(x + dx, y), node, goalNode);
            final Node yJumpNode = findJumpNode(graph, graph.getNode(x, y + dy), node, goalNode);
            if (xJumpNode != null || yJumpNode != null) {
                return node;
            }
        } else if (dx == 0) {
            // Check for horizontal forced neighbors.
            if ((graph.isWalkable(x + 1, y + dy) && !graph.isWalkable(x + 1, y)) ||
                (graph.isWalkable(x - 1, y + dy) && !graph.isWalkable(x - 1, y))) {
                return node;
            }
        } else if (dy == 0) {
            // Check for vertical forced neighbors.
            if ((graph.isWalkable(x + dx, y + 1) && !graph.isWalkable(x, y + 1)) ||
                (graph.isWalkable(x + dx, y - 1) && !graph.isWalkable(x, y - 1))) {
                return node;
            }
        }

        // For a diagonal jump, at least one shared neighbors between the node and the jump node
        // must be walkable.
        if (graph.isWalkable(x + dx, y) || graph.isWalkable(x, y + dy)) {
            return findJumpNode(graph, graph.getNode(x + dx, y + dy), node, goalNode);
        }
        return null;
    }

    private static List<Node> findPrunedNeighbors(Graph graph, Node node) {
        final Node parentNode = node.getParent();
        if (parentNode == null) {
            return graph.findNodeNeighbors(node);
        }

        final List<Node> neighbors = new DynamicArray<>(5);
        final int x = node.getX();
        final int y = node.getY();
        final int dx = (x - parentNode.getX()) / Math.max(Math.abs(x - parentNode.getX()), 1);
        final int dy = (y - parentNode.getY()) / Math.max(Math.abs(y - parentNode.getY()), 1);
        if (dx != 0 && dy != 0) {
            // Check for diagonal forced neighbors.
            if (graph.isWalkable(x, y + dy)) {
                neighbors.add(graph.getNode(x, y + dy));
            }
            if (graph.isWalkable(x + dx, y)) {
                neighbors.add(graph.getNode(x + dx, y));
            }
            if (graph.isWalkable(x, y + dy) || graph.isWalkable(x + dx, y)) {
                neighbors.add(graph.getNode(x + dx, y + dy));
            }
            if (!graph.isWalkable(x - dx, y) && graph.isWalkable(x, y + dy)) {
                neighbors.add(graph.getNode(x - dx, y + dy));
            }
            if (!graph.isWalkable(x, y - dy) && graph.isWalkable(x + dx, y)) {
                neighbors.add(graph.getNode(x + dx, y - dy));
            }
        } else if (dx == 0 && graph.isWalkable(x, y + dy)) {
            // Check for horizontal forced neighbors.
            neighbors.add(graph.getNode(x, y + dy));
            if (!graph.isWalkable(x + 1, y)) {
                neighbors.add(graph.getNode(x + 1, y + dy));
            }
            if (!graph.isWalkable(x - 1, y)) {
                neighbors.add(graph.getNode(x - 1, y + dy));
            }
        } else if (dy == 0 && graph.isWalkable(x + dx, y)) {
            // Check for vertical forced neighbors.
            neighbors.add(graph.getNode(x + dx, y));
            if (!graph.isWalkable(x, y + 1)) {
                neighbors.add(graph.getNode(x + dx, y + 1));
            }
            if (!graph.isWalkable(x, y - 1)) {
                neighbors.add(graph.getNode(x + dx, y - 1));
            }
        }

        return neighbors;
    }
}
